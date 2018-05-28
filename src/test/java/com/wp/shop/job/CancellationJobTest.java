package com.wp.shop.job;

import com.wp.shop.util.TestData;
import com.wp.shop.model.data.OfferDao;
import com.wp.shop.repository.OfferRepository;
import com.wp.shop.util.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CancellationJobTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private CancellationJob job = new CancellationJob();

    @Test
    public void cancelOffers() {

        OfferDao offer = TestData.getOfferDao(Status.ACTIVE);
        offer.setEndDate(new Date(Instant.now().minusMillis(2000).toEpochMilli()));

        when(offerRepository.findByStatus(Status.ACTIVE)).thenReturn(Arrays.asList(offer));

        job.cancelOffers();

        verify(offerRepository, times(1)).findByStatus(Status.ACTIVE);
        verify(offerRepository, times(1)).cancelOffer(offer.getId());
    }

}
