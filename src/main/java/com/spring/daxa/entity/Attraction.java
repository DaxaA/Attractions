package com.spring.daxa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

enum Category {
    military, culture, religious, historical, nature
}

@Entity
@Getter
@Setter
@Table(name = "attractions")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "category")
    private Category category;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "information")
    private String information;
    @Column(name = "middle_rate")
    private Double midRate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "attraction", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    public Attraction() {}

    public Attraction(Long id, String name, Category category, Double longitude, Double latitude, String information, Double midRate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.information = information;
        this.midRate = midRate;
    }
}
