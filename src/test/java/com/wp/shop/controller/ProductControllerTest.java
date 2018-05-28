package com.wp.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.shop.util.TestData;
import com.wp.shop.model.domain.Product;
import com.wp.shop.model.transfer.ProductDto;
import com.wp.shop.service.ProductService;
import com.wp.shop.util.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Product product;

    private ProductDto productDto;

    @Mock
    private ProductService productService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ProductController controller = new ProductController();

    @Before
    public void before() {
        product = TestData.getProduct();
        productDto = TestData.getProductDto();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(mapper.transformProductToProductDto(product)).thenReturn(productDto);
        when(mapper.transformProductDtoToProduct(productDto)).thenReturn(product);
    }

    @Test
    public void shouldGetAllProducts() throws Exception {

        when(productService.getProducts()).thenReturn(Arrays.asList(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(product.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(product.getName())))
                .andExpect(jsonPath("$[0].price", is(product.getPrice())))
                .andExpect(jsonPath("$[0].currency", is(product.getCurrency())));

        verify(productService, times(1)).getProducts();
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldCreateProduct() throws Exception {

        when(productService.createProduct(product)).thenReturn(product.getId());
        when(mapper.transformProductDtoToProduct(any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(product.getId().toString()));

        verify(productService, times(1)).createProduct(product);
        verifyNoMoreInteractions(productService);
    }
}
