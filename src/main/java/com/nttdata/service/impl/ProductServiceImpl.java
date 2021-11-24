package com.nttdata.service.impl;

import com.nttdata.model.Product;
import com.nttdata.repository.ProductRepository;
import com.nttdata.repository.Repository;
import com.nttdata.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**Se crea la clase ProductService extendido con CrudServiceImpl implementado ProductService.*/
@Service
public class ProductServiceImpl extends CrudServiceImpl<Product, String> implements ProductService {

  @Autowired
  private ProductRepository repository;

  @Override
  protected Repository<Product, String> getRepository() {
    return repository;
  }

}