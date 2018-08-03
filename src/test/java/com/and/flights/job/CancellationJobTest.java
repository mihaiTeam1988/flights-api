package com.and.flights.job;

import com.and.flights.util.TestData;
import com.and.flights.model.data.OfferDao;
import com.and.flights.repository.OfferRepository;
import com.and.flights.util.Status;
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
