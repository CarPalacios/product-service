package com.nttdata.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.model.Condition;
import com.nttdata.model.Product;
import com.nttdata.repository.ProductRepository;
import com.nttdata.service.impl.ProductServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Import(ProductServiceImpl.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProductController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class ProductControllerTest {
  
  @Autowired
  private WebTestClient product;
  
  @MockBean
  private ProductRepository repository;

  @Test
  void testFindAll() {
 
    Product products = new Product();
    
    List<String> type = new ArrayList<>();    
    type.add("PERSONAL");
    
    products.setId("1");
    products.setProductName("AHORRO");
    products.setProductType("PASIVO");
    
    Condition condition = new Condition();
    
    condition.setCustomerTypeTarget(type);
    condition.setHasMaintenanceFee(false);
    condition.setMaintenanceFee(0.0);
    condition.setHasMonthlyTransactionLimit(true);
    condition.setMonthlyTransactionLimit(3);
    condition.setHasDailyWithdrawalTransactionLimit(false);
    condition.setDailyWithdrawalTransactionLimit(0);
    condition.setHasDailyDepositTransactionLimit(false);
    condition.setDailyDepositTransactionLimit(0);
    condition.setProductPerPersonLimit(1);
    condition.setProductPerBusinessLimit(0);
    
    products.setCondition(condition);
    
    List<Product> list = new ArrayList<>();
    list.add(products);
    Flux<Product> fluxproduct = Flux.fromIterable(list);
    
    Mockito.when(repository.findAll()).thenReturn(fluxproduct);
    
    product.get().uri("/product").accept(MediaType.APPLICATION_JSON)
    .exchange().expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
    .expectBodyList(Product.class).hasSize(1);
    
        
  }

  @Test
  void testFindById() {
    
    Product products = new Product();
    
    List<String> type = new ArrayList<>();    
    type.add("PERSONAL");
    
    products.setId("1");
    products.setProductName("AHORRO");
    products.setProductType("PASIVO");
    
    Condition condition = new Condition();
    
    condition.setCustomerTypeTarget(type);
    condition.setHasMaintenanceFee(false);
    condition.setMaintenanceFee(0.0);
    condition.setHasMonthlyTransactionLimit(true);
    condition.setMonthlyTransactionLimit(3);
    condition.setHasDailyWithdrawalTransactionLimit(false);
    condition.setDailyWithdrawalTransactionLimit(0);
    condition.setHasDailyDepositTransactionLimit(false);
    condition.setDailyDepositTransactionLimit(0);
    condition.setProductPerPersonLimit(1);
    condition.setProductPerBusinessLimit(0);
    
    products.setCondition(condition);
    
    Mockito.when(repository.findById("1")).thenReturn(Mono.just(products));
    product.get().uri("/product/1")
    .accept(MediaType.APPLICATION_JSON).exchange()
    .expectStatus().isOk()
    .expectHeader().contentType(MediaType.APPLICATION_JSON)
    .expectBody()
    .jsonPath("$.id").isNotEmpty()
    .jsonPath("$.productName").isEqualTo("AHORRO")
    .jsonPath("$.productType").isEqualTo("PASIVO");
  }

  @Test
  void testCreate() {
    
    Product products = new Product();
    
    List<String> type = new ArrayList<>();    
    type.add("PERSONAL");
    
    products.setId("2");
    products.setProductName("CREDITO PERSONAL");
    products.setProductType("ACTIVO");
    
    Condition condition = new Condition();
    
    condition.setCustomerTypeTarget(type);
    condition.setHasMaintenanceFee(false);
    condition.setMaintenanceFee(0.0);
    condition.setHasMonthlyTransactionLimit(false);
    condition.setMonthlyTransactionLimit(0);
    condition.setHasDailyWithdrawalTransactionLimit(false);
    condition.setDailyWithdrawalTransactionLimit(0);
    condition.setHasDailyDepositTransactionLimit(false);
    condition.setDailyDepositTransactionLimit(0);
    condition.setProductPerPersonLimit(1);
    condition.setProductPerBusinessLimit(0);
    
    products.setCondition(condition);
    
    Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(Mono.just(products));
    product.post().uri("/product")
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON)
    .body(Mono.just(products), Product.class)
    .exchange()
    .expectStatus().isCreated()
    .expectHeader().contentType(MediaType.APPLICATION_JSON)
    .expectBody()
    .jsonPath("$.id").isNotEmpty()
    .jsonPath("$.productName").isEqualTo("CREDITO PERSONAL")
    .jsonPath("$.productType").isEqualTo("ACTIVO");
    
  }

  @Test
  void testUpdate() {
    
    Product products = new Product();
    
    List<String> type = new ArrayList<>();    
    type.add("PERSONAL");
    
    products.setId("2");
    products.setProductName("PLAZO FIJO");
    products.setProductType("PASIVO");
    
    Condition condition = new Condition();
    
    condition.setCustomerTypeTarget(type);
    condition.setHasMaintenanceFee(false);
    condition.setMaintenanceFee(0.0);
    condition.setHasMonthlyTransactionLimit(false);
    condition.setMonthlyTransactionLimit(0);
    condition.setHasDailyWithdrawalTransactionLimit(false);
    condition.setDailyWithdrawalTransactionLimit(0);
    condition.setHasDailyDepositTransactionLimit(false);
    condition.setDailyDepositTransactionLimit(0);
    condition.setProductPerPersonLimit(1);
    condition.setProductPerBusinessLimit(0);
    
    products.setCondition(condition);
    
    Mockito.when(repository.findById("2")).thenReturn(Mono.just(products));
    Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(Mono.just(products));
    product.put().uri("/product/2")
    .accept(MediaType.APPLICATION_JSON)
    .body(Mono.just(products), Product.class)
    .exchange()
    .expectStatus().isOk()
    .expectHeader().contentType(MediaType.APPLICATION_JSON)
    .expectBody()
    .jsonPath("$.id").isNotEmpty()
    .jsonPath("$.productName").isEqualTo("PLAZO FIJO")
    .jsonPath("$.productType").isEqualTo("PASIVO");
  }

}
