package com.ispp.petcare.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Trip.
 */
@Entity
@Table(name = "trip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "trip")
public class Trip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description_text", nullable = false)
    private String descriptionText;
    
    @NotNull
    @Pattern(regexp = "(^SHORT|MEDIUM|LARGE$)")
    @Column(name = "distance", nullable = false)
    private String distance;
    
    @NotNull
    @Column(name = "moment", nullable = false)
    private ZonedDateTime moment;
    
    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "cost", nullable = false)
    private Double cost;
    
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptionText() {
        return descriptionText;
    }
    
    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getDistance() {
        return distance;
    }
    
    public void setDistance(String distance) {
        this.distance = distance;
    }

    public ZonedDateTime getMoment() {
        return moment;
    }
    
    public void setMoment(ZonedDateTime moment) {
        this.moment = moment;
    }

    public Double getCost() {
        return cost;
    }
    
    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trip trip = (Trip) o;
        if(trip.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Trip{" +
            "id=" + id +
            ", descriptionText='" + descriptionText + "'" +
            ", distance='" + distance + "'" +
            ", moment='" + moment + "'" +
            ", cost='" + cost + "'" +
            '}';
    }
}
