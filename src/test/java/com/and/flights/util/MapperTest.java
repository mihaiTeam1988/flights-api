package com.and.flights.util;

import com.and.flights.model.data.OfferDao;
import com.and.flights.model.data.ProductDao;
import com.and.flights.model.domain.Offer;
import com.and.flights.model.domain.Product;
import com.and.flights.model.transfer.OfferDtoRead;
import com.and.flights.model.transfer.OfferDtoWrite;
import com.and.flights.model.transfer.ProductDtoRead;
import com.and.flights.model.transfer.ProductDtoWrite;
import org.junit.Test;

import static org.junit.Assert.*;


public class MapperTest {

    private static final Offer OFFER = TestData.getOffer(Status.ACTIVE);
    private static final OfferDao OFFER_DAO = TestData.getOfferDao(Status.ACTIVE);
    private static final OfferDtoRead OFFER_DTO_READ = TestData.getOfferDtoRead(Status.ACTIVE);
    private static final OfferDtoWrite OFFER_DTO_WRITE = TestData.getOfferDtoWrite();

    private static final Product PRODUCT = TestData.getProduct();
    private static final ProductDao PRODUCT_DAO = TestData.getProductDao();
    private static final ProductDtoRead PRODUCT_DTO_READ = TestData.getProductDtoRead();
    private static final ProductDtoWrite PRODUCT_DTO_WRITE = TestData.getProductDtoWrite();

    private Mapper mapper = new Mapper();

    @Test
    public void shouldTransformOfferToOfferDto() {
        OfferDtoRead offerDto = mapper.transformOfferToOfferDtoRead(OFFER);
        assertEquals(OFFER_DTO_READ, offerDto);
    }

    @Test
    public void shouldTransformOfferDtoWriteToOffer() {
        Offer offer = mapper.transformOfferDtoWriteToOffer(OFFER_DTO_WRITE);
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
        ProductDtoRead productDto = mapper.transformProductToProductDtoRead(PRODUCT);
        assertEquals(PRODUCT_DTO_READ, productDto);
    }

    @Test
    public void shouldTransformProductDtoWriteToProduct() {
        Product product = mapper.transformProductDtoWriteToProduct(PRODUCT_DTO_WRITE);
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
