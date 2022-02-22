package com.spring.daxa.entity;

import javax.persistence.*;

@Entity
@Table
public class Attraction {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long cityid;
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private double coordinates;
    @Column
    private String information;
    @Column
    private double midrate;
    @Column
    private Long reviewid;

    public Attraction() {}

    public Attraction(Long id, Long cityid, String name, String category, double coordinates, String information, double midrate, Long reviewid) {
        this.id = id;
        this.cityid = cityid;
        this.name = name;
        this.category = category;
        this.coordinates = coordinates;
        this.information = information;
        this.midrate = midrate;
        this.reviewid = reviewid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityid() {
        return cityid;
    }

    public void setCityid(Long cityid) {
        this.cityid = cityid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double coordinates) {
        this.coordinates = coordinates;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public double getMidrate() {
        return midrate;
    }

    public void setMidrate(double midrate) {
        this.midrate = midrate;
    }

    public Long getReviewid() {
        return reviewid;
    }

    public void setReviewid(Long reviewid) {
        this.reviewid = reviewid;
    }
}
