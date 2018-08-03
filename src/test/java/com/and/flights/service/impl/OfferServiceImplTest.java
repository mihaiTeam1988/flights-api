package com.and.flights.service.impl;

import com.and.flights.exception.ConflictException;
import com.and.flights.model.data.ProductDao;
import com.and.flights.repository.ProductRepository;
import com.and.flights.util.TestData;
import com.and.flights.exception.BadRequestException;
import com.and.flights.exception.NotFoundException;
import com.and.flights.model.data.OfferDao;
import com.and.flights.model.domain.Offer;
import com.and.flights.repository.OfferRepository;
import com.and.flights.util.Mapper;
import com.and.flights.util.Status;
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
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private OfferServiceImpl offerService = new OfferServiceImpl();

    private static final OfferDao OFFER_DAO = TestData.getOfferDao(Status.ACTIVE);
    private static final Offer OFFER = TestData.getOffer(Status.ACTIVE);

    @Before
    public void before() {
        when(mapper.transformOfferDaoToOffer(OFFER_DAO)).thenReturn(OFFER);
    }

    @Test
    public void shouldGetOffer() {

        Long offerId = OFFER_DAO.getId();

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(OFFER_DAO));

        Offer result = offerService.getOffer(OFFER_DAO.getId());

        assertEquals(offerId, result.getId());
        verify(offerRepository, times(1)).findById(offerId);
        verify(mapper, times(1)).transformOfferDaoToOffer(OFFER_DAO);
    }

    @Test
    public void shouldNotFindOffer() {

        Long offerId = OFFER_DAO.getId();

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        try {
            offerService.getOffer(OFFER_DAO.getId());
            fail();
        } catch (NotFoundException e) {
            assertEquals(String.format("Offer with id [%s] does not exist", OFFER_DAO.getId().toString()), e.getMessage());
        } catch (Exception e) {
            fail();
        }

        verify(offerRepository, times(1)).findById(offerId);
        verifyZeroInteractions(mapper);
    }

    @Test
    public void shouldGetAllOffers() {

        Long offerId = OFFER_DAO.getId();

        when(offerRepository.findAll()).thenReturn(Arrays.asList((OFFER_DAO)));

        Collection<Offer> offers = offerService.getOffers(Optional.empty());

        assertEquals(1, offers.size());
        assertEquals(offerId, offers.iterator().next().getId());
        verify(offerRepository, times(1)).findAll();
        verify(mapper, times(1)).transformOfferDaoToOffer(OFFER_DAO);
    }

    @Test
    public void shouldGetAllActiveOffers() {

        Long offerId = OFFER_DAO.getId();

        when(offerRepository.findByStatus(Status.ACTIVE)).thenReturn(Arrays.asList((OFFER_DAO)));

        Collection<Offer> offers = offerService.getOffers(Optional.of(Status.ACTIVE));

        assertEquals(1, offers.size());

        Offer retrievedOffer = offers.iterator().next();

        assertEquals(offerId, retrievedOffer.getId());
        assertTrue(Status.ACTIVE.equals(retrievedOffer.getStatus()));
        verify(offerRepository, times(1)).findByStatus(Status.ACTIVE);
        verify(mapper, times(1)).transformOfferDaoToOffer(OFFER_DAO);
    }

    @Test
    public void shouldNotFindAnyOffer() {

        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        Collection<Offer> offers = offerService.getOffers(Optional.empty());

        assertTrue(offers.isEmpty());
        verify(offerRepository, times(1)).findAll();
        verifyZeroInteractions(mapper);
    }

    @Test
    public void shouldNotFindProductWhenCreatingOffer() {

        Long productId = OFFER.getProductId();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        try {
            offerService.createOffer(OFFER);
            fail();
        } catch (NotFoundException e) {
            assertEquals(String.format("Product with id [%s] does not exist", productId.toString()), e.getMessage());
        } catch (Exception e) {
            fail();
        }

        verify(productRepository, times(1)).findById(productId);
        verifyZeroInteractions(offerRepository, mapper);
    }

    @Test
    public void shouldCreateOffer() {

        ProductDao product = TestData.getProductDao();
        Long productId = OFFER.getProductId();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.transformOfferToOfferDao(OFFER, product)).thenReturn(OFFER_DAO);
        when(offerRepository.save(OFFER_DAO)).thenReturn(OFFER_DAO);

        Long saveOfferId = offerService.createOffer(OFFER);

        assertEquals(OFFER_DAO.getId(), saveOfferId);
        verify(productRepository, times(1)).findById(productId);
        verify(mapper, times(1)).transformOfferToOfferDao(OFFER, product);
        verify(offerRepository, times(1)).save(any(OfferDao.class));
    }

    @Test
    public void shouldThrowExceptionWhenCreatingOffer() {

        ProductDao product = TestData.getProductDao();
        Long productId = OFFER.getProductId();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.transformOfferToOfferDao(OFFER, product)).thenReturn(OFFER_DAO);
        when(offerRepository.save(OFFER_DAO)).thenThrow(new JDBCException("conflict", new SQLException()));

        try {
            offerService.createOffer(OFFER);
            fail();
        } catch (ConflictException ex) {
            assertEquals("Error encountered while saving the offer", ex.getMessage());
        } catch (Exception ex) {
            fail();
        }

        verify(productRepository, times(1)).findById(productId);
        verify(mapper, times(1)).transformOfferToOfferDao(OFFER, product);
        verify(offerRepository, times(1)).save(any(OfferDao.class));
    }

    @Test
    public void shouldNotCancelOffer() {

        Long offerId = OFFER.getId();
        when(offerRepository.cancelOffer(offerId)).thenReturn(0);

        try {
            offerService.cancelOffer(offerId);
            fail();
        } catch (BadRequestException e) {
            assertEquals(String.format("Offer with id [%s] is cancelled already or does not exist", offerId.toString()), e.getMessage());
        } catch (Exception e) {
            fail();
        }

        verify(offerRepository, times(1)).cancelOffer(offerId);
    }

    @Test
    public void shouldThrowExceptionWhenCancelOffer() {

        Long offerId = OFFER.getId();
        when(offerRepository.cancelOffer(offerId)).thenThrow(new JDBCException("conflict", new SQLException()));

        try {
            offerService.cancelOffer(offerId);
            fail();
        } catch (ConflictException e) {
            assertEquals(String.format("Failed to cancel offer with id [%s]", offerId), e.getMessage());
        } catch (Exception e) {
            fail();
        }

        verify(offerRepository, times(1)).cancelOffer(offerId);
    }

    @Test
    public void shouldCancelOffer() {

        Long offerId = OFFER.getId();
        when(offerRepository.cancelOffer(offerId)).thenReturn(1);

        Long cancelledOfferId = offerService.cancelOffer(offerId);
        assertEquals(offerId, cancelledOfferId);

        verify(offerRepository, times(1)).cancelOffer(offerId);
    }
}
