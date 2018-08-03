package com.and.flights.service;

import com.and.flights.model.domain.Product;

import java.util.Collection;

/**
 * Interface for the Product service
 */
public interface ProductService {

    Collection<Product> getProducts();

    Long createProduct(Product product);
}
