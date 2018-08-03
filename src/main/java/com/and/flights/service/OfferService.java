package com.and.flights.service;

import com.and.flights.model.domain.Offer;
import com.and.flights.util.Status;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface for the Offer service
 */
public interface OfferService {

    Offer getOffer(Long offerId);

    Collection<Offer> getOffers(Optional<Status> status);

    Long createOffer(Offer offer);

    Long cancelOffer(Long offerId);
}
