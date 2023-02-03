package de.cinetastisch.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "ticket_fare")
public class TicketFare {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @SequenceGenerator(name = "ticket_fare_sequence", sequenceName = "ticket_fare_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "ticket_fare_sequence")
    @Column(name = "id")
    private @Id Long id;

    private String name;
    private double price;
    private String fareCondition;

    public TicketFare() {
    }

    public TicketFare(Long id, String name, double price, String condition) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fareCondition = condition;
    }

    public TicketFare(String name, double price, String condition) {
        this.name = name;
        this.price = price;
        this.fareCondition = condition;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getFareCondition() {
        return fareCondition;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFareCondition(String condition) {
        this.fareCondition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketFare that = (TicketFare) o;
        return Double.compare(that.price, price) == 0 && id.equals(that.id) && Objects.equals(name,
                                                                                              that.name) && Objects.equals(
                fareCondition, that.fareCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, fareCondition);
    }

    @Override
    public String toString() {
        return "TicketFare{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", condition='" + fareCondition + '\'' +
                '}';
    }
}
