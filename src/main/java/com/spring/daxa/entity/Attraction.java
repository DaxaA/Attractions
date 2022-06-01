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

    public Attraction() {
        setMidRate();
    }

    public void setMidRate() {
        if (reviewList.size() == 0) {
            this.midRate = 0.0;
        } else {
            Integer rates = 0;
            for (Review review : reviewList) {
                rates += review.getRate();
            }
            this.midRate = (double) rates / (double) reviewList.size();
        }
    }
}
