package com.and.flights.repository;

import com.and.flights.model.data.OfferDao;
import com.and.flights.util.Status;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface OfferRepository extends CrudRepository<OfferDao, Long> {

    Collection<OfferDao> findByStatus(Status status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE OFFER o SET o.status = 'EXPIRED' WHERE o.id= :id AND o.status<>'EXPIRED'")
    int cancelOffer(@Param("id") Long offerId);
}
