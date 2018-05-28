package com.wp.shop.job;

import com.wp.shop.model.data.OfferDao;
import com.wp.shop.repository.OfferRepository;
import com.wp.shop.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class CancellationJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CancellationJob.class);

    @Autowired
    private OfferRepository offerRepository;

    @Scheduled(cron = "0 1 0 * * *")
    public void cancelOffers() {

        LOGGER.info("Start offer cancellation process");

        Date now = new Date();

        Collection<OfferDao> offers = offerRepository.findByStatus(Status.ACTIVE);

        long count = offers.stream()
                .filter(o -> o.getEndDate().before(now))
                .map(o -> {
                    offerRepository.cancelOffer(o.getId());
                    return o.getId();
                })
                .count();

        LOGGER.info(String.format("Offer cancellation process finished. [%s] offers cancelled", count));
    }
}
