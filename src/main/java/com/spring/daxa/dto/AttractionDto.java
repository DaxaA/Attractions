package com.spring.daxa.dto;

import com.spring.daxa.enums.Category;
import lombok.Getter;

@Getter
public class AttractionDto {
    private final Long id;
    private final String name;
    private final Category category;
    private final Double longitude;
    private final Double latitude;
    private final String information;
    private final Double midRate;
    private final String city;

    public AttractionDto(Long id, String name, Category category, Double longitude, Double latitude, String information, Double midRate, String city) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.information = information;
        this.midRate = midRate;
        this.city = city;
    }
}