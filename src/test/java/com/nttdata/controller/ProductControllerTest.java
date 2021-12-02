package com.nttdata.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.model.Product;
import com.nttdata.repository.ProductRepository;
import com.nttdata.service.impl.ProductServiceImpl;

import reactor.core.publisher.Flux;

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
    products.getCondition().setCustomerTypeTarget(type);
    products.getCondition().setHasMaintenanceFee(false);
    products.getCondition().setMaintenanceFee(0.0);
    products.getCondition().setHasMonthlyTransactionLimit(true);
    products.getCondition().setMonthlyTransactionLimit(3);
    products.getCondition().setHasDailyWithdrawalTransactionLimit(false);
    products.getCondition().setDailyWithdrawalTransactionLimit(0);
    products.getCondition().setHasDailyDepositTransactionLimit(false);
    products.getCondition().setDailyDepositTransactionLimit(0);
    products.getCondition().setProductPerPersonLimit(1);
    products.getCondition().setProductPerBusinessLimit(0);
    List<Product> list = new ArrayList<>();
    list.add(products);
    Flux<Product> fluxproduct = Flux.fromIterable(list);
    
    Mockito.when(repository.findAll()).thenReturn(fluxproduct);
    product.get().uri("/product").accept(MediaType.APPLICATION_JSON)
    .exchange().expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
    .expectBodyList(Product.class).hasSize(1);
    
        
  }

//  @Test
//  void testFindById() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  void testCreate() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  void testUpdate() {
//    fail("Not yet implemented");
//  }

}
