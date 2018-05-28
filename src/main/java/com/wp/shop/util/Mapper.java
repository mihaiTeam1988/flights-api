package com.wp.shop.util;

import com.wp.shop.model.data.OfferDao;
import com.wp.shop.model.data.ProductDao;
import com.wp.shop.model.domain.Offer;
import com.wp.shop.model.transfer.OfferDto;
import com.wp.shop.model.domain.Product;
import com.wp.shop.model.transfer.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    /*
     * Offer mappers
     */
    public OfferDto transformOfferToOfferDto(Offer offer) {
        OfferDto dto = new OfferDto();

        dto.setId(offer.getId());
        dto.setProductId(offer.getProductId());
        dto.setPrice(offer.getPrice());
        dto.setCurrency(offer.getCurrency());
        dto.setDescription(offer.getDescription());
        dto.setStartDate(offer.getStartDate());
        dto.setEndDate(offer.getEndDate());
        dto.setStatus(offer.getStatus());

        return dto;
    }

    public Offer transformOfferDtoToOffer(OfferDto offerDto) {
        Offer offer = new Offer();

        offer.setProductId(offerDto.getProductId());
        offer.setPrice(offerDto.getPrice());
        offer.setCurrency(offerDto.getCurrency());
        offer.setDescription(offerDto.getDescription());
        offer.setStartDate(offerDto.getStartDate());
        offer.setEndDate(offerDto.getEndDate());
        offer.setStatus(offerDto.getStatus());

        return offer;
    }

    public Offer transformOfferDaoToOffer(OfferDao offerDao) {
        Offer offer = new Offer();

        offer.setId(offerDao.getId());
        offer.setProductId(offerDao.getProduct().getId());
        offer.setPrice(offerDao.getPrice());
        offer.setCurrency(offerDao.getCurrency());
        offer.setDescription(offerDao.getDescription());
        offer.setStartDate(offerDao.getStartDate());
        offer.setEndDate(offerDao.getEndDate());
        offer.setStatus(offerDao.getStatus());

        return offer;
    }

    public OfferDao transformOfferToOfferDao(Offer offer, ProductDao product) {
        OfferDao offerDao = new OfferDao();

        offerDao.setId(offer.getId());
        offerDao.setProduct(product);
        offerDao.setPrice(offer.getPrice());
        offerDao.setCurrency(offer.getCurrency());
        offerDao.setDescription(offer.getDescription());
        offerDao.setStartDate(offer.getStartDate());
        offerDao.setEndDate(offer.getEndDate());
        offerDao.setStatus(offer.getStatus());

        return offerDao;
    }

    /*
     * Product mappers
     */
    public ProductDto transformProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCurrency(product.getCurrency());

        return productDto;
    }

    public Product transformProductDtoToProduct(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCurrency(productDto.getCurrency());

        return product;
    }

    public Product transformProductDaoToProduct(ProductDao productDao) {
        Product product = new Product();

        product.setId(productDao.getId());
        product.setName(productDao.getName());
        product.setPrice(productDao.getPrice());
        product.setCurrency(productDao.getCurrency());

        return product;
    }

    public ProductDao transformProductToProductDao(Product product) {
        ProductDao productDao = new ProductDao();

        productDao.setId(product.getId());
        productDao.setName(product.getName());
        productDao.setPrice(product.getPrice());
        productDao.setCurrency(product.getCurrency());

        return productDao;
    }
}
