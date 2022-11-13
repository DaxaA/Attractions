package com.spring.daxa.services;

import com.spring.daxa.dto.AttractionDto;
import com.spring.daxa.dto.ReviewDto;
import com.spring.daxa.entity.Attraction;

import java.util.List;

public interface AttractionService {
    List<Attraction> getAttractionsByLongitudeAndLatitude(Double longitude, Double latitude);
    List<Attraction> getAttractionsByLongitudeAndLatitudeAndCategory(Double longitude, Double latitude, String category);
    List<Attraction> getAttractionsByLongitudeAndLatitudeAndMidRate(Double longitude, Double latitude, Double midRate);
    List<Attraction> getAttractionsByCityAndCategory(String city, String category);
    List<Attraction> getAttractionsByCityAndMidRate(String city, Double midRate);
    List<Attraction> getAttractionByCityName(String city);
    List<Object> getInformationAndMiddleRate(String attraction);
    List<Attraction> showAllAttractions();
    List<AttractionDto> createAttractionList(List<Attraction> attractions);
    List<ReviewDto> showReviewList(String attraction);
    void setReview(String attraction, Integer rate, String review);
    Attraction addNewAttraction(Attraction attraction);
    AttractionDto updateAttraction(AttractionDto attractionDto);
}
