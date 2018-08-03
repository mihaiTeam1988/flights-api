package com.and.flights.service.impl;

import com.and.flights.exception.ConflictException;
import com.and.flights.model.data.ProductDao;
import com.and.flights.model.domain.Product;
import com.and.flights.repository.ProductRepository;
import com.and.flights.service.ProductService;
import com.and.flights.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public Collection<Product> getProducts() {

        Collection<Product> products = new ArrayList<>();

        productRepository.findAll()
                .forEach(p -> products.add(mapper.transformProductDaoToProduct(p)));

        LOGGER.info(String.format("Retrieved [%s] products", products.size()));

        return products;
    }

    @Override
    public Long createProduct(Product product) {

        ProductDao productDao = mapper.transformProductToProductDao(product);

        try {
            ProductDao savedProduct = productRepository.save(productDao);

            LOGGER.info(String.format("Created product", savedProduct));

            return savedProduct.getId();
        } catch (Exception ex) {
            LOGGER.error("Error encountered while saving the product", ex);
            throw new ConflictException("Error encountered while saving the product");
        }
    }
}
