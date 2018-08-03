package com.and.flights.model.data;

import com.and.flights.util.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Offer entity class
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "OFFER")
public class OfferDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductDao product;

    @Column(name = "DESCRIPTION", nullable = false, unique = true)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return "OfferDao {" +
                "id=" + id +
                ", product=" + product +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}
