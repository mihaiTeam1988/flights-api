package com.and.flights.util;

import com.and.flights.model.data.ProductDao;
import com.and.flights.model.transfer.ProductDtoRead;
import com.and.flights.model.data.OfferDao;
import com.and.flights.model.domain.Offer;
import com.and.flights.model.transfer.OfferDtoRead;
import com.and.flights.model.domain.Product;
import com.and.flights.model.transfer.OfferDtoWrite;
import com.and.flights.model.transfer.ProductDtoWrite;

import java.time.Instant;
import java.util.Date;

/**
 * Helper class to provide test data
 */
public final class TestData {

    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final Integer PRICE = 100;
    private static final String CURRENCY = "GBP";
    private static final Long OFFER_ID = new Long(1001);
    private static final Long PRODUCT_ID = new Long(1002);
    private static final Date START_DATE = new Date(Instant.now().plusMillis(500000).toEpochMilli());
    private static final Date END_DATE = new Date(Instant.now().plusMillis(1000000).toEpochMilli());

    public static Offer getOffer(Status status) {
        Offer offer = new Offer();
        offer.setId(OFFER_ID);
        offer.setDescription(DESCRIPTION);
        offer.setPrice(PRICE);
        offer.setCurrency(CURRENCY);
        offer.setProductId(PRODUCT_ID);
        offer.setStartDate(START_DATE);
        offer.setEndDate(END_DATE);
        offer.setStatus(status);

        return offer;
    }

    public static OfferDtoRead getOfferDtoRead(Status status) {
        OfferDtoRead offer = new OfferDtoRead();
        offer.setId(OFFER_ID);
        offer.setDescription(DESCRIPTION);
        offer.setPrice(PRICE);
        offer.setCurrency(CURRENCY);
        offer.setProductId(PRODUCT_ID);
        offer.setStartDate(START_DATE);
        offer.setEndDate(END_DATE);
        offer.setStatus(status);

        return offer;
    }

    public static OfferDtoWrite getOfferDtoWrite() {
        OfferDtoWrite offer = new OfferDtoWrite();
        offer.setDescription(DESCRIPTION);
        offer.setPrice(PRICE);
        offer.setCurrency(CURRENCY);
        offer.setProductId(PRODUCT_ID);
        offer.setStartDate(START_DATE);
        offer.setEndDate(END_DATE);

        return offer;
    }

    public static OfferDao getOfferDao(Status status) {
        OfferDao offer = new OfferDao();
        offer.setId(OFFER_ID);
        offer.setDescription(DESCRIPTION);
        offer.setPrice(PRICE);
        offer.setCurrency(CURRENCY);
        offer.setProduct(getProductDao());
        offer.setStartDate(START_DATE);
        offer.setEndDate(END_DATE);
        offer.setStatus(status);

        return offer;
    }

    public static Product getProduct() {
        Product product = new Product();
        product.setCurrency(CURRENCY);
        product.setPrice(PRICE);
        product.setName(NAME);
        product.setId(PRODUCT_ID);

        return product;
    }

    public static ProductDtoRead getProductDtoRead() {
        ProductDtoRead productDto = new ProductDtoRead();
        productDto.setCurrency(CURRENCY);
        productDto.setPrice(PRICE);
        productDto.setName(NAME);
        productDto.setId(PRODUCT_ID);

        return productDto;
    }

    public static ProductDtoWrite getProductDtoWrite() {
        ProductDtoWrite productDto = new ProductDtoWrite();
        productDto.setCurrency(CURRENCY);
        productDto.setPrice(PRICE);
        productDto.setName(NAME);

        return productDto;
    }

    public static ProductDao getProductDao() {
        ProductDao productDao = new ProductDao();
        productDao.setCurrency(CURRENCY);
        productDao.setPrice(PRICE);
        productDao.setName(NAME);
        productDao.setId(PRODUCT_ID);

        return productDao;
    }
}
