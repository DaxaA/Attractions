package com.spring.daxa.entity;

import javax.persistence.*;

@Entity
@Table
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int rate;
    @Column
    private String review;

    public Review() {}

    public Review(Long id, int rate, String review) {
        this.id = id;
        this.rate = rate;
        this.review = review;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
