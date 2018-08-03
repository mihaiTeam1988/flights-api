package com.and.flights.model.transfer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
@EqualsAndHashCode
public class ProductDtoWrite {

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @Positive(message = "Price should be a positive value")
    private Integer price;
    @NotEmpty(message = "Currency cannot be empty")
    private String currency;

    @Override
    public String toString() {
        return "ProductDtoWrite {" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}
