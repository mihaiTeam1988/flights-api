package com.wp.shop.util;

import com.wp.shop.exception.BadRequestException;
import com.wp.shop.model.transfer.OfferDto;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    /*
     * These checks should be performed in a custom validator add added as annotations in the class
     */
    public void validateStatusAndDates(OfferDto offerDto) {
        if (!Status.ACTIVE.equals(offerDto.getStatus())) {
            throw new BadRequestException("The status of the offer must be ACTIVE");
        }

        if (offerDto.getStartDate().after(offerDto.getEndDate())) {
            throw new BadRequestException("The start date of the offer should be before the end date");
        }
    }
}
