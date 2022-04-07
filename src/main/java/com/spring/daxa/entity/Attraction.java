package com.spring.daxa.entity;

import com.spring.daxa.enums.AttractionFields;
import com.spring.daxa.enums.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Review> reviewList = new ArrayList<>();

    public Attraction() {}

    public Attraction(Long id, String name, Category category, Double longitude, Double latitude, String information, Double midRate, City city) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.information = information;
        this.midRate = midRate;
        this.city = city;
    }

    public Object getProperty(AttractionFields key) {
        if (key == AttractionFields.ID) {
            return id;
        } else if (key == AttractionFields.NAME) {
            return name;
        }  else if (key == AttractionFields.CATEGORY) {
            return category;
        } else if (key == AttractionFields.LONGITUDE) {
            return longitude;
        }else if (key == AttractionFields.LATITUDE) {
            return latitude;
        }else if (key == AttractionFields.INFORMATION) {
            return information;
        }else {
            return midRate;
        }
    }
}
