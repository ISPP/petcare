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
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "booking")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "code", nullable = false)
    private String code;
    
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;
    
    @NotNull
    @Column(name = "start_moment", nullable = false)
    private ZonedDateTime startMoment;
    
    @NotNull
    @Column(name = "end_moment", nullable = false)
    private ZonedDateTime endMoment;
    
    @NotNull
    @Pattern(regexp = "(^PENDING|ACCEPTED|REJECTED$)")
    @Column(name = "status", nullable = false)
    private String status;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private Double price;
    
    @NotNull
    @Column(name = "night", nullable = false)
    private Boolean night;
    
    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToOne
    private Review review;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStartMoment() {
        return startMoment;
    }
    
    public void setStartMoment(ZonedDateTime startMoment) {
        this.startMoment = startMoment;
    }

    public ZonedDateTime getEndMoment() {
        return endMoment;
    }
    
    public void setEndMoment(ZonedDateTime endMoment) {
        this.endMoment = endMoment;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getNight() {
        return night;
    }
    
    public void setNight(Boolean night) {
        this.night = night;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        if(booking.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", description='" + description + "'" +
            ", startMoment='" + startMoment + "'" +
            ", endMoment='" + endMoment + "'" +
            ", status='" + status + "'" +
            ", price='" + price + "'" +
            ", night='" + night + "'" +
            '}';
    }
}
