package com.and.flights.model.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Product entity class
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "PRODUCT")
public class ProductDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    @Override
    public String toString() {
        return "ProductDao {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}
