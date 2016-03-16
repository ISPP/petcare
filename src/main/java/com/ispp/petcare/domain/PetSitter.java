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
 * A PetSitter.
 */
@Entity
@Table(name = "pet_sitter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "petsitter")
public class PetSitter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "price_night", nullable = false)
    private Double priceNight;
    
    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "price_hour", nullable = false)
    private Double priceHour;
    
    @OneToMany(mappedBy = "petSitter")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pet> pets = new HashSet<>();

    @OneToMany(mappedBy = "petSitter")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Place> places = new HashSet<>();

    @OneToOne
    private Supplier supplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPriceNight() {
        return priceNight;
    }
    
    public void setPriceNight(Double priceNight) {
        this.priceNight = priceNight;
    }

    public Double getPriceHour() {
        return priceHour;
    }
    
    public void setPriceHour(Double priceHour) {
        this.priceHour = priceHour;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PetSitter petSitter = (PetSitter) o;
        if(petSitter.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, petSitter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PetSitter{" +
            "id=" + id +
            ", priceNight='" + priceNight + "'" +
            ", priceHour='" + priceHour + "'" +
            '}';
    }
}
