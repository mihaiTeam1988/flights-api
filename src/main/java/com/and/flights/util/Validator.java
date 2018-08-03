package com.and.flights.util;

import com.and.flights.exception.BadRequestException;
import com.and.flights.model.transfer.OfferDtoWrite;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    /*
     * These checks should be performed in a custom validator add added as annotations in the class
     */
    public void validateDates(OfferDtoWrite offerDto) {
        if (offerDto.getStartDate().after(offerDto.getEndDate())) {
            throw new BadRequestException("The start date of the offer should be before the end date");
        }
    }
}
