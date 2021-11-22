package com.nttdata.controller;

import java.net.URI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.model.Product;
import com.nttdata.service.IProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private IProductService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Product>>> findAll(){ 

		Flux<Product> products = service.findAll();
		
		return Mono.just(ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(products));	
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Product>> findById(@PathVariable("id") String id){
		return service.findById(id)
				.map(objectFound -> ResponseEntity
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(objectFound))
				.defaultIfEmpty(ResponseEntity
						.noContent()
						.build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<Product>> create(@RequestBody Product product, final ServerHttpRequest request){
		return service.create(product)
				.map(createdObject->{
					return ResponseEntity
							.created(URI.create(request.getURI().toString().concat(createdObject.getId())))
							.contentType(MediaType.APPLICATION_JSON)
							.body(createdObject);
				});
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Product>> update(@RequestBody Product product, @PathVariable("id") String id){
		Mono<Product> ProductModification = Mono.just(product);
		Mono<Product> ProductDatabase = service.findById(id);
		
		return ProductDatabase
				.zipWith(ProductModification, (a,b)->{
					a.setId(id);
					a.setProductName(product.getProductName());
					a.setProductType(product.getProductType());
					a.getCondition().setCustomerTypeTarget(product.getCondition().getCustomerTypeTarget());
					a.getCondition().setHasMaintenanceFee(product.getCondition().isHasMaintenanceFee());
					a.getCondition().setMaintenanceFee(product.getCondition().getMaintenanceFee());
					a.getCondition().setHasMonthlyTransactionLimit(product.getCondition().isHasMonthlyTransactionLimit());
					a.getCondition().setMonthlyTransactionLimit(product.getCondition().getMonthlyTransactionLimit());
					a.getCondition().setHasDailyWithdrawalTransactionLimit(product.getCondition().isHasDailyWithdrawalTransactionLimit());
					a.getCondition().setDailyWithdrawalTransactionLimit(product.getCondition().getDailyWithdrawalTransactionLimit());
					a.getCondition().setHasDailyDepositTransactionLimit(product.getCondition().isHasDailyDepositTransactionLimit());
					a.getCondition().setDailyDepositTransactionLimit(product.getCondition().getDailyDepositTransactionLimit());
					a.getCondition().setProductPerPersonLimit(product.getCondition().getProductPerPersonLimit());
					a.getCondition().setProductPerBusinessLimit(product.getCondition().getProductPerBusinessLimit());
					return a;
				})
				.flatMap(service::update)
				.map(objectUpdated->{
					return ResponseEntity
							.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(objectUpdated);
				})
				.defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id){
		return service.findById(id)				
				.flatMap(p -> {
					return service.delete(p.getId())
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));					
				})
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
