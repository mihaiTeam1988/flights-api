package com.wp.shop.model.transfer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class OfferDtoWrite {

    @NotNull(message = "ProductId cannot be empty")
    private Long productId;
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    @Positive(message = "Price should be a positive value")
    private Integer price;
    @NotEmpty(message = "Currency cannot be empty")
    private String currency;
    @FutureOrPresent(message = "Start date should be in the future")
    private Date startDate;
    @FutureOrPresent(message = "End date should be in the future")
    private Date endDate;

    @Override
    public String toString() {
        return "OfferDtoWrite {" +
                "productId=" + productId +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
