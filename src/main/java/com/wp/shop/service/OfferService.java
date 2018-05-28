package com.wp.shop.service;

import com.wp.shop.model.domain.Offer;
import com.wp.shop.util.Status;

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
