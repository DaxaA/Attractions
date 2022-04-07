package com.spring.daxa.services;

import com.spring.daxa.entity.Attraction;
import com.spring.daxa.enums.Category;
import com.spring.daxa.entity.City;

import java.util.List;

public interface AttractionService {
    List<Attraction> getAttractionsByLongitudeAndLatitude(Double longitude, Double latitude, List<Attraction> attractionList);
    List<Attraction> getAttractionsByLongitudeAndLatitudeAndCategory(Double longitude, Double latitude, Category category);
    List<Attraction> getAttractionsByLongitudeAndLatitudeAndMidRate(Double longitude, Double latitude, Double midRate);
    List<Attraction> getAttractionsByCityAndCategory(City city, Category category);
    List<Attraction> getAttractionsByCityAndMidRate(City city, Double midRate);
    String getInformationAndMiddleRate(Attraction attraction);
    void setRate(Attraction attraction, Integer rate);
    void setRateAndReview(Attraction attraction, Integer rate, String review);
}
