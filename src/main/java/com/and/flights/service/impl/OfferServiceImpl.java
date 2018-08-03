package com.and.flights.service.impl;

import com.and.flights.exception.ConflictException;
import com.and.flights.exception.NotFoundException;
import com.and.flights.model.data.ProductDao;
import com.and.flights.model.domain.Offer;
import com.and.flights.repository.OfferRepository;
import com.and.flights.repository.ProductRepository;
import com.and.flights.service.OfferService;
import com.and.flights.util.Mapper;
import com.and.flights.exception.BadRequestException;
import com.and.flights.model.data.OfferDao;
import com.and.flights.util.Status;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public Offer getOffer(Long offerId) {

        Optional<OfferDao> offerDao = offerRepository.findById(offerId);

        if (offerDao.isPresent()) {
            Offer offer = mapper.transformOfferDaoToOffer(offerDao.get());
            LOGGER.info(String.format("Retrieved offer [%s]", offer));
            return offer;
        } else {
            throw new NotFoundException(String.format("Offer with id [%s] does not exist", offerId.toString()));
        }
    }

    @Override
    public Collection<Offer> getOffers(Optional<Status> status) {

        Collection<Offer> offers = new ArrayList<>();

        if (status.isPresent()) {
            offerRepository.findByStatus(status.get())
                    .forEach(o -> offers.add(mapper.transformOfferDaoToOffer(o)));
            LOGGER.info(String.format("Retrieved [%s] offers with status [%s]", offers.size(), status.get()));
        } else {
            offerRepository.findAll()
                    .forEach(o -> offers.add(mapper.transformOfferDaoToOffer(o)));
            LOGGER.info(String.format("Retrieved [%s] offers", offers.size()));
        }

        return offers;
    }

    @Override
    public Long createOffer(Offer offer) {

        Long productId = offer.getProductId();

        Optional<ProductDao> product = productRepository.findById(productId);

        if (product.isPresent()) {

            OfferDao offerDao = mapper.transformOfferToOfferDao(offer, product.get());

            try {
                OfferDao savedOffer = offerRepository.save(offerDao);
                LOGGER.info(String.format("Created offer [%s]", savedOffer));
                return savedOffer.getId();
            } catch (Exception ex) {
                LOGGER.error("Error encountered while saving the offer ", ex);
                throw new ConflictException("Error encountered while saving the offer");
            }
        } else {
            throw new NotFoundException(String.format("Product with id [%s] does not exist", productId.toString()));
        }
    }

    @Override
    public Long cancelOffer(Long offerId) {
        try {
            int rows = offerRepository.cancelOffer(offerId);
            if (rows == 1) {
                LOGGER.info(String.format("Cancelled offer with id [%s]", offerId));
                return offerId;
            } else {
                throw new BadRequestException(String.format("Offer with id [%s] is cancelled already or does not exist", offerId));
            }
        } catch (JDBCException ex) {
            LOGGER.error(String.format("Failed to cancel offer with id [%s]", offerId), ex);
            throw new ConflictException(String.format("Failed to cancel offer with id [%s]", offerId));
        }
    }
}
