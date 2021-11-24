package com.nttdata.controller;

import com.nttdata.model.Product;
import com.nttdata.service.ProductService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**Dentro de la clase ProductController se realizan los metodos http.*/
@RestController
@RequestMapping("/product")
public class ProductController {
  
  @Autowired
  private ProductService service;
  
  /** Muestra los registros de la tabla * @return registro de la tabla seleccionada. */
  @GetMapping
  public Mono<ResponseEntity<Flux<Product>>> findAll() { 
    Flux<Product> products = service.findAll();
    
    return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(products));  
  }
  
  /** Muestra los registros de la tabla a traves de un id. * 
   * * @return registro de la tabla seleccionada por id. */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Product>> findById(@PathVariable("id") String id) {
    return service.findById(id)
        .map(objectFound -> ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(objectFound))
        .defaultIfEmpty(ResponseEntity
            .noContent()
            .build());
  }
  
  /** Crea los registros de la tabla.* @return crea registros de la tabla. */
  @PostMapping
  public Mono<ResponseEntity<Product>> 
      create(@RequestBody Product product, final ServerHttpRequest request) {
    return service.create(product)
        .map(createdObject -> {
          return ResponseEntity
              .created(URI.create(request.getURI().toString().concat(createdObject.getId())))
              .contentType(MediaType.APPLICATION_JSON)
              .body(createdObject);
        });
  }
  
  /** Actualiza los registros de la tabla por id.* @return actualiza registros de la tabla por id.*/
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Product>> 
      update(@RequestBody Product product, @PathVariable("id") String id) {
    Mono<Product> productModification = Mono.just(product);
    Mono<Product> productDatabase = service.findById(id);
    
    return productDatabase
        .zipWith(productModification, (a, b) -> {
          a.setId(id);
          a.setProductName(product.getProductName());
          a.setProductType(product.getProductType());
          a.getCondition().setCustomerTypeTarget(product.getCondition().getCustomerTypeTarget());
          a.getCondition().setHasMaintenanceFee(product.getCondition().isHasMaintenanceFee());
          a.getCondition().setMaintenanceFee(product.getCondition().getMaintenanceFee());
          a.getCondition().setHasMonthlyTransactionLimit(product.getCondition()
              .isHasMonthlyTransactionLimit());
          a.getCondition().setMonthlyTransactionLimit(product.getCondition()
              .getMonthlyTransactionLimit());
          a.getCondition().setHasDailyWithdrawalTransactionLimit(product.getCondition()
              .isHasDailyWithdrawalTransactionLimit());
          a.getCondition().setDailyWithdrawalTransactionLimit(product.getCondition()
              .getDailyWithdrawalTransactionLimit());
          a.getCondition().setHasDailyDepositTransactionLimit(product.getCondition()
              .isHasDailyDepositTransactionLimit());
          a.getCondition().setDailyDepositTransactionLimit(product.getCondition()
              .getDailyDepositTransactionLimit());
          a.getCondition().setProductPerPersonLimit(product.getCondition()
              .getProductPerPersonLimit());
          a.getCondition().setProductPerBusinessLimit(product.getCondition()
              .getProductPerBusinessLimit());
          return a;
        })
        .flatMap(service::update)
        .map(objectUpdated -> {
          return ResponseEntity
              .ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(objectUpdated);
        })
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }
  
  

}
