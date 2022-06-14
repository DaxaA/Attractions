package com.spring.daxa.dto;

import lombok.Getter;

@Getter
public class ReviewDto {
    private final Integer rate;
    private final String review;

    public ReviewDto(Integer rate, String review) {
        this.rate = rate;
        this.review = review;
    }
}
