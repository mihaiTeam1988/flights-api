package com.wp.shop.repository;

import com.wp.shop.ShopApplication;
import com.wp.shop.model.data.OfferDao;
import com.wp.shop.util.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopApplication.class)
public class OfferRepositoryTest {

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void shouldFindByStatus() {
        Collection<OfferDao> offers = offerRepository.findByStatus(Status.ACTIVE);
        assertFalse(offers.isEmpty());
    }

    @Test
    public void shouldCancelOffer() {

        Long orderIdToCancel = new Long(2001);

        //status before
        OfferDao offerBefore = offerRepository.findById(orderIdToCancel).get();
        assertEquals(Status.ACTIVE, offerBefore.getStatus());

        //cancel
        int cancelledOffers = offerRepository.cancelOffer(orderIdToCancel);
        assertEquals(1, cancelledOffers);

        //status after
        OfferDao offerAfter = offerRepository.findById(orderIdToCancel).get();
        assertEquals(Status.EXPIRED, offerAfter.getStatus());
    }

    @Test
    public void shouldFailIfCancelAlreadyCancelledOffer() {

        Long expiredOderId = new Long(2004);

        //status before
        OfferDao offerBefore = offerRepository.findById(expiredOderId).get();
        assertEquals(Status.EXPIRED, offerBefore.getStatus());

        //cancel
        int cancelledOffers = offerRepository.cancelOffer(expiredOderId);
        assertEquals(0, cancelledOffers);
    }

}
