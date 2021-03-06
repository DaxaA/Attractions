package com.spring.daxa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rate")
    private Integer rate;
    @Column(name = "review")
    private String review;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    public Review(Integer rate, String review) {
        if (rate > 5) {
            this.rate = 5;
        } else {
            this.rate = rate;
        }
        this.review = review;
    }
}
