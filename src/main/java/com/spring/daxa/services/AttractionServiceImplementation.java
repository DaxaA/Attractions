package com.spring.daxa.services;

import com.spring.daxa.entity.Review;
import com.spring.daxa.enums.Order;
import com.spring.daxa.entity.Attraction;
import com.spring.daxa.enums.AttractionFields;
import com.spring.daxa.enums.Category;
import com.spring.daxa.entity.City;
import com.spring.daxa.repositories.AttractionRepository;

import java.util.*;

public class AttractionServiceImplementation implements AttractionService {
    private final AttractionRepository attractionRepository;
    Map<AttractionFields, Order> sortMap = new HashMap<>();
    Map<AttractionFields, Object> filterMap = new HashMap<>();
    List<Attraction> filteredAttractions;
    List<Attraction> sortedAttractions;

    public AttractionServiceImplementation(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    //list of nearby attractions
    @Override
    public List<Attraction> getAttractionsByLongitudeAndLatitude(Double longitude, Double latitude, List<Attraction> attractionList) {
        for (Attraction attraction: attractionList) {
            if ((Math.abs(attraction.getLongitude() - longitude) <= 10) && (Math.abs(attraction.getLatitude() - latitude) <= 10)) {
                sortedAttractions.add(attraction);
            }
        }
        sortMap.put(AttractionFields.LONGITUDE, Order.ASCENDING);
        sortedAttractions = AttractionSorting.sorting(sortedAttractions, sortMap);
        return sortedAttractions;
    }

    //list of nearby attractions filtered by category
    @Override
    public List<Attraction> getAttractionsByLongitudeAndLatitudeAndCategory(Double longitude, Double latitude, Category category) {
        List<Attraction> allAttractions = attractionRepository.findAll();
        filterMap.put(AttractionFields.CATEGORY, category);
        filteredAttractions = AttractionFilter.filter(allAttractions, filterMap);
        sortedAttractions = getAttractionsByLongitudeAndLatitude(longitude, latitude, filteredAttractions);
        return sortedAttractions;
    }

    //list of nearby attractions filtered by middle rate
    @Override
    public List<Attraction> getAttractionsByLongitudeAndLatitudeAndMidRate(Double longitude, Double latitude, Double midRate) {
        List<Attraction> allAttractions = attractionRepository.findAll();
        filterMap.put(AttractionFields.MIDRATE, midRate);
        filteredAttractions = AttractionFilter.filter(allAttractions, filterMap);
        sortedAttractions = getAttractionsByLongitudeAndLatitude(longitude, latitude, filteredAttractions);
        return sortedAttractions;
    }

    //list attractions in the selected city filtered by category and sorted by longitude
    @Override
    public List<Attraction> getAttractionsByCityAndCategory(City city, Category category) {
        List<Attraction> allAttractions = attractionRepository.findAll();
        filterMap.put(AttractionFields.CITY, city);
        filterMap.put(AttractionFields.CATEGORY, category);
        filteredAttractions = AttractionFilter.filter(allAttractions, filterMap);
        sortMap.put(AttractionFields.LONGITUDE, Order.ASCENDING);
        sortedAttractions = AttractionSorting.sorting(filteredAttractions, sortMap);
        return sortedAttractions;
    }

    //list of attractions in the selected city filtered by middle rate and sorted by longitude
    @Override
    public List<Attraction> getAttractionsByCityAndMidRate(City city, Double midRate) {
        List<Attraction> allAttractions = attractionRepository.findAll();
        filterMap.put(AttractionFields.CITY, city);
        filterMap.put(AttractionFields.MIDRATE, midRate);
        filteredAttractions = AttractionFilter.filter(allAttractions, filterMap);
        sortMap.put(AttractionFields.LONGITUDE, Order.ASCENDING);
        sortedAttractions = AttractionSorting.sorting(filteredAttractions, sortMap);
        return sortedAttractions;
    }

    //string with a description of the attraction and its middle rate
    @Override
    public String getInformationAndMiddleRate(Attraction attraction) {
        return "Attraction:" + attraction.getName() + ".\nDescription: " + attraction.getInformation() + ".\nMiddle rate: " + attraction.getMidRate() + ".";
    }

    //setting rating
    @Override
    public void setRate(Attraction attraction, Integer rate) {
        Review rateWithoutReview = new Review(rate, null);
        List<Review> attractionReviews = attraction.getReviewList();
        attractionReviews.add(rateWithoutReview);
    }

    //writing review and setting rating
    @Override
    public void setRateAndReview(Attraction attraction, Integer rate, String review) {
        Review fullReview = new Review(rate, review);
        List<Review> attractionReviews = attraction.getReviewList();
        attractionReviews.add(fullReview);
    }
}
