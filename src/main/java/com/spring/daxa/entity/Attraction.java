package com.spring.daxa.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attractions")
public class Attraction {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "city_id")
    private Long cityId;
    @Column(name = "name")
    private String name;
    @Column(name = "category")
    private String category;
    @Column(name = "coordinates")
    private double coordinates;
    @Column(name = "information")
    private String information;
    @Column(name = "middle_rate")
    private double midRate;
    @Column(name = "review_id")
    private Long reviewId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;

    @OneToMany(mappedBy = "attraction", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    public Attraction() {}

    public Attraction(Long id, Long cityId, String name, String category, double coordinates, String information, double midRate, Long reviewId) {
        this.id = id;
        this.cityId = cityId;
        this.name = name;
        this.category = category;
        this.coordinates = coordinates;
        this.information = information;
        this.midRate = midRate;
        this.reviewId = reviewId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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

    public double getMidRate() {
        return midRate;
    }

    public void setMidRate(double midRate) {
        this.midRate = midRate;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
