package com.and.flights.model.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
public class Product {

    private Long id;
    private String name;
    private Integer price;
    private String currency;
}
