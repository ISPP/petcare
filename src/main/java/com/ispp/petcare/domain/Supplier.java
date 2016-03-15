package com.ispp.petcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Supplier.
 */
@Entity
@Table(name = "supplier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "supplier")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "rating")
    private Double rating;
    
    @Column(name = "blocked")
    private Boolean blocked;
    
    @OneToMany(mappedBy = "reviewed")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviewss = new HashSet<>();

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Booking> bookingss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getBlocked() {
        return blocked;
    }
    
    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Set<Review> getReviewss() {
        return reviewss;
    }

    public void setReviewss(Set<Review> reviews) {
        this.reviewss = reviews;
    }

    public Set<Booking> getBookingss() {
        return bookingss;
    }

    public void setBookingss(Set<Booking> bookings) {
        this.bookingss = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Supplier supplier = (Supplier) o;
        if(supplier.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, supplier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Supplier{" +
            "id=" + id +
            ", rating='" + rating + "'" +
            ", blocked='" + blocked + "'" +
            '}';
    }
}
