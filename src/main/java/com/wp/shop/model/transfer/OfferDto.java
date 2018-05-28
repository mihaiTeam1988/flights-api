package com.wp.shop.model.transfer;

import com.wp.shop.util.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
public class OfferDto {

    private Long id;
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
    @NotNull
    private Status status;

    @Override
    public String toString() {
        return "OfferDto {" +
                "productId=" + productId +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}
