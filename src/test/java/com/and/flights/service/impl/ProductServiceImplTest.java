package com.and.flights.service.impl;

import com.and.flights.exception.ConflictException;
import com.and.flights.model.data.ProductDao;
import com.and.flights.repository.ProductRepository;
import com.and.flights.util.TestData;
import com.and.flights.model.domain.Product;
import com.and.flights.util.Mapper;
import org.hibernate.JDBCException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ProductServiceImpl productService = new ProductServiceImpl();

    private static final Product PRODUCT = TestData.getProduct();
    private static final ProductDao PRODUCT_DAO = TestData.getProductDao();

    @Before
    public void before() {
        when(mapper.transformProductDaoToProduct(PRODUCT_DAO)).thenReturn(PRODUCT);
        when(mapper.transformProductToProductDao(PRODUCT)).thenReturn(PRODUCT_DAO);
    }

    @Test
    public void shouldGetProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(PRODUCT_DAO));

        Collection<Product> products = productService.getProducts();

        assertFalse(products.isEmpty());
        assertEquals(PRODUCT_DAO.getId(), products.iterator().next().getId());
        verify(productRepository, times(1)).findAll();
        verify(mapper, times(1)).transformProductDaoToProduct(PRODUCT_DAO);
    }

    @Test
    public void shouldNotFindAnyProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        Collection<Product> products = productService.getProducts();

        assertTrue(products.isEmpty());
        verify(productRepository, times(1)).findAll();
        verifyZeroInteractions(mapper);
    }

    @Test
    public void shouldCreateProduct() {
        ProductDao savedProduct = TestData.getProductDao();
        when(productRepository.save(PRODUCT_DAO)).thenReturn(savedProduct);

        Long productId = productService.createProduct(PRODUCT);

        assertEquals(savedProduct.getId(), productId);
        verify(productRepository, times(1)).save(PRODUCT_DAO);
        verify(mapper, times(1)).transformProductToProductDao(PRODUCT);
    }

    @Test
    public void shouldThrowExceptionWhenSavingProduct() {
        when(productRepository.save(PRODUCT_DAO)).thenThrow(new JDBCException("conflict", new SQLException()));

        try {
            productService.createProduct(PRODUCT);
            fail();
        } catch (ConflictException ex) {
            assertEquals("Error encountered while saving the product", ex.getMessage());
        } catch (Exception ex) {
            fail();
        }

        verify(productRepository, times(1)).save(PRODUCT_DAO);
        verify(mapper, times(1)).transformProductToProductDao(PRODUCT);
    }
}
