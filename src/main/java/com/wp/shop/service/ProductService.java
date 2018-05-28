package com.wp.shop.service;

import com.wp.shop.model.domain.Product;

import java.util.Collection;

/**
 * Interface for the Product service
 */
public interface ProductService {

    Collection<Product> getProducts();

    Long createProduct(Product product);
}
