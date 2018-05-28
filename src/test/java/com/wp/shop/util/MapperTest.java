package com.wp.shop.util;

import com.wp.shop.model.data.OfferDao;
import com.wp.shop.model.data.ProductDao;
import com.wp.shop.model.domain.Offer;
import com.wp.shop.model.domain.Product;
import com.wp.shop.model.transfer.OfferDto;
import com.wp.shop.model.transfer.ProductDto;
import org.junit.Test;

import static org.junit.Assert.*;


public class MapperTest {

    private static final Offer OFFER = TestData.getOffer(Status.ACTIVE);
    private static final OfferDao OFFER_DAO = TestData.getOfferDao(Status.ACTIVE);
    private static final OfferDto OFFER_DTO = TestData.getOfferDto(Status.ACTIVE);

    private static final Product PRODUCT = TestData.getProduct();
    private static final ProductDao PRODUCT_DAO = TestData.getProductDao();
    private static final ProductDto PRODUCT_DTO = TestData.getProductDto();

    private Mapper mapper = new Mapper();

    @Test
    public void shouldTransformOfferToOfferDto() {
        OfferDto offerDto = mapper.transformOfferToOfferDto(OFFER);
        assertEquals(OFFER_DTO, offerDto);
    }

    @Test
    public void shouldTransformOfferDtoToOffer() {
        Offer offer = mapper.transformOfferDtoToOffer(OFFER_DTO);
        assertEquals(OFFER, offer);
    }

    @Test
    public void shouldTransformOfferDaoToOffer() {
        Offer offer = mapper.transformOfferDaoToOffer(OFFER_DAO);
        assertEquals(OFFER, offer);
    }

    @Test
    public void shouldTransformOfferToOfferDao() {
        OfferDao offerDao = mapper.transformOfferToOfferDao(OFFER, PRODUCT_DAO);
        assertEquals(OFFER_DAO, offerDao);
    }

    @Test
    public void shouldTransformProductToProductDto() {
        ProductDto productDto = mapper.transformProductToProductDto(PRODUCT);
        assertEquals(PRODUCT_DTO, productDto);
    }

    @Test
    public void shouldTransformProductDtoToProduct() {
        Product product = mapper.transformProductDtoToProduct(PRODUCT_DTO);
        assertEquals(PRODUCT, product);
    }

    @Test
    public void shouldTransformProductDaoToProduct() {
        Product product = mapper.transformProductDaoToProduct(PRODUCT_DAO);
        assertEquals(PRODUCT, product);
    }

    @Test
    public void shouldTransformProductToProductDao() {
        ProductDao productDao = mapper.transformProductToProductDao(PRODUCT);
        assertEquals(PRODUCT_DAO, productDao);
    }
}
