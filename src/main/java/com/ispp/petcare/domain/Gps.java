package com.ispp.petcare.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;

/**
 * Created by Llamas on 15/03/2016.
 */
@Embeddable
public class Gps {

    //Constructor-------------------------------------------------
    public Gps(){
        super();
    }

    //Attributes---------------------------------------------
    @NotBlank
    private String title;



    @Range(min = -90, max = 90)
    @Digits(integer=2,fraction=50)
    private Double longitude;

    @Range(min = -180, max = 180)
    @Digits(integer=2,fraction=50)
    @Column(name = "latitude")
    private Double latitude;


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }







    // toString---------------------------------
    @Override
    public String toString() {
        return "Gps{" +
            "title='" + title + '\'' +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            '}';
    }

}
