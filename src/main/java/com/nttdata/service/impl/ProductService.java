package com.nttdata.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.model.Product;
import com.nttdata.repository.IProductRepository;
import com.nttdata.repository.IRepository;
import com.nttdata.service.IProductService;

@Service
public class ProductService extends CRUDServiceImpl<Product, String> implements IProductService {

	@Autowired
	private IProductRepository repository;

	@Override
	protected IRepository<Product, String> getRepository() {
		return repository;
	}

}