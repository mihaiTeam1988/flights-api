package com.and.flights.util;


import com.and.flights.model.transfer.OfferDtoWrite;
import com.and.flights.exception.BadRequestException;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class ValidatorTest {

    private Validator validator = new Validator();

    @Test
    public void shouldTestDates() {
        Date endDate = new Date(Instant.now().toEpochMilli());
        Date startDate = new Date(Instant.now().plusMillis(500000).toEpochMilli());

        OfferDtoWrite offerDto = TestData.getOfferDtoWrite();
        offerDto.setStartDate(startDate);
        offerDto.setEndDate(endDate);

        try {
            validator.validateDates(offerDto);
        } catch (BadRequestException ex) {
            assertEquals("The start date of the offer should be before the end date", ex.getMessage());
        }
    }
}
