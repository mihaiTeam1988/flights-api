package com.wp.shop.controller;

import com.wp.shop.exception.handler.HttpExceptionResponse;
import com.wp.shop.model.domain.Product;
import com.wp.shop.model.transfer.ProductDto;
import com.wp.shop.service.ProductService;
import com.wp.shop.util.Mapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private Mapper mapper;

    @ApiOperation("Get all products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = HttpExceptionResponse.class)})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ProductDto> getProducts() {

        LOGGER.info("Fetching all products");

        return productService.getProducts().stream()
                .map(o -> mapper.transformProductToProductDto(o))
                .collect(Collectors.toList());
    }

    @ApiOperation("Create product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Long.class),
            @ApiResponse(code = 400, message = "Bad Request", response = HttpExceptionResponse.class),
            @ApiResponse(code = 409, message = "Product already exists", response = HttpExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = HttpExceptionResponse.class)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long createProduct(@Valid @RequestBody ProductDto product) {

        LOGGER.info(String.format("Creating product [%s]", product));

        Product prod = mapper.transformProductDtoToProduct(product);
        return productService.createProduct(prod);
    }
}
