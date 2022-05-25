package com.spring.daxa.controllers;

import com.spring.daxa.dto.AttractionDto;
import com.spring.daxa.dto.ReviewDto;
import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.Review;
import com.spring.daxa.services.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/attractions")
public class AttractionController {
    private final AttractionService attractionService;

    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping
    public List<AttractionDto> getAll() {
        List<Attraction> attractions = attractionService.showAllAttractions();
        return attractionService.createAttractionList(attractions);
    }

    @GetMapping("/city/{city}")
    public List<AttractionDto> getByCityName(@PathVariable String city) {
        List<Attraction> attractionByCity = new ArrayList<>();
        for (Attraction a : attractionService.showAllAttractions()) {
            if (Objects.equals(a.getCity().getName(), city))
                attractionByCity.add(a);
        }
        return attractionService.createAttractionList(attractionByCity);
    }

    @GetMapping("/near/{longitude}/{latitude}")
    public List<AttractionDto> getAttrByLoAndLa(@PathVariable Double longitude, @PathVariable Double latitude) {
        List<Attraction> attractionByLL = attractionService.getAttractionsByLongitudeAndLatitude(longitude, latitude);
        return attractionService.createAttractionList(attractionByLL);
    }

    @GetMapping("/near/{longitude}/{latitude}/category/{category}")
    public List<AttractionDto> getAttrByCat(@PathVariable Double longitude, @PathVariable Double latitude, @PathVariable String category) {
        List<Attraction> attractionByLLC = attractionService.getAttractionsByLongitudeAndLatitudeAndCategory(longitude, latitude, category);
        return attractionService.createAttractionList(attractionByLLC);

    }

    @GetMapping("/near/{longitude}/{latitude}/rate/{midrate}")
    public List<AttractionDto> getAttrByMidRate(@PathVariable Double longitude, @PathVariable Double latitude, @PathVariable Double midrate) {
        List<Attraction> attractionByLLMR = attractionService.getAttractionsByLongitudeAndLatitudeAndMidRate(longitude, latitude, midrate);
        return attractionService.createAttractionList(attractionByLLMR);
    }

    @GetMapping("/city/{city}/category/{category}")
    public List<AttractionDto> getAttrByCityAndCat(@PathVariable String city, @PathVariable String category) {
        List<Attraction> attractionList = attractionService.getAttractionsByCityAndCategory(city, category);
        return attractionService.createAttractionList(attractionList);
    }

    @GetMapping("/city/{city}/rate/{midrate}")
    public List<AttractionDto> getAttrByCityAndMidRate(@PathVariable String city, @PathVariable Double midrate) {
        List<Attraction> attractionList = attractionService.getAttractionsByCityAndMidRate(city, midrate);
        return attractionService.createAttractionList(attractionList);
    }

    @GetMapping("/{attraction}")
    public String getOnlyInfoAndMidRate(@PathVariable String attraction) {
        return attractionService.getInformationAndMiddleRate(attraction);
    }

    @GetMapping("/{attraction}/reviews")
    public List<ReviewDto> getReviewList(@PathVariable String attraction) {
        return attractionService.showReviewList(attraction);
    }
}