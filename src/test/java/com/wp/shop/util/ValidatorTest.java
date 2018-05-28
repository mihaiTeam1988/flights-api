package com.wp.shop.util;


import com.wp.shop.exception.BadRequestException;
import com.wp.shop.model.transfer.OfferDto;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class ValidatorTest {

    private Validator validator = new Validator();

    @Test
    public void shouldTestStatus() {
        OfferDto offerDto = TestData.getOfferDto(Status.EXPIRED);
        try {
            validator.validateStatusAndDates(offerDto);
        } catch (BadRequestException ex) {
            assertEquals("The status of the offer must be ACTIVE", ex.getMessage());
        }
    }

    @Test
    public void shouldTestDates() {
        Date endDate = new Date(Instant.now().toEpochMilli());
        Date startDate = new Date(Instant.now().plusMillis(500000).toEpochMilli());

        OfferDto offerDto = TestData.getOfferDto(Status.ACTIVE);
        offerDto.setStartDate(startDate);
        offerDto.setEndDate(endDate);

        try {
            validator.validateStatusAndDates(offerDto);
        } catch (BadRequestException ex) {
            assertEquals("The start date of the offer should be before the end date", ex.getMessage());
        }
    }
}
