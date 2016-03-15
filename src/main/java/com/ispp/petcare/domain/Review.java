package com.ispp.petcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "rating", nullable = false)
    private Integer rating;
    
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;
    
    @NotNull
    @Column(name = "creation_moment", nullable = false)
    private ZonedDateTime creationMoment;
    
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private PetOwner reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewed_id")
    private Supplier reviewed;

    @OneToOne(mappedBy = "review")
    @JsonIgnore
    private Booking booking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreationMoment() {
        return creationMoment;
    }
    
    public void setCreationMoment(ZonedDateTime creationMoment) {
        this.creationMoment = creationMoment;
    }

    public PetOwner getReviewer() {
        return reviewer;
    }

    public void setReviewer(PetOwner petOwner) {
        this.reviewer = petOwner;
    }

    public Supplier getReviewed() {
        return reviewed;
    }

    public void setReviewed(Supplier supplier) {
        this.reviewed = supplier;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        if(review.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Review{" +
            "id=" + id +
            ", rating='" + rating + "'" +
            ", description='" + description + "'" +
            ", creationMoment='" + creationMoment + "'" +
            '}';
    }
}
