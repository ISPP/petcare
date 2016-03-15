package com.ispp.petcare.domain;

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
 * A Place.
 */
@Entity
@Table(name = "place")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "place")
public class Place implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "has_garden", nullable = false)
    private Boolean hasGarden;

    @NotNull
    @Column(name = "has_patio", nullable = false)
    private Boolean hasPatio;

    @NotNull
    @Pattern(regexp = "(^FLAT|HOUSE|PETLODGE|OTHER$)")
    @Column(name = "building", nullable = false)
    private String building;


    public Gps getLocation() {
        return location;
    }

    public void setLocation(Gps location) {
        this.location = location;
    }

    @Column(name = "location")
    private Gps location;

    @ManyToOne
    @JoinColumn(name = "pet_sitter_id")
    private PetSitter petSitter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(Boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public Boolean getHasPatio() {
        return hasPatio;
    }

    public void setHasPatio(Boolean hasPatio) {
        this.hasPatio = hasPatio;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public PetSitter getPetSitter() {
        return petSitter;
    }

    public void setPetSitter(PetSitter petSitter) {
        this.petSitter = petSitter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Place place = (Place) o;
        if(place.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, place.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Place{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", hasGarden='" + hasGarden + "'" +
            ", hasPatio='" + hasPatio + "'" +
            ", building='" + building + "'" +
            '}';
    }
}
