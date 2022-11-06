package com.spring.daxa.services;

import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.City;
import com.spring.daxa.entity.Review;
import com.spring.daxa.enums.AttractionFields;
import com.spring.daxa.enums.Category;
import com.spring.daxa.repositories.AttractionRepository;
import com.spring.daxa.repositories.AttractionRepositoryOwnImpl;
import com.spring.daxa.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceTest {
    @Mock
    private static AttractionRepository attractionRepository;
    @Mock
    private static AttractionRepositoryOwnImpl attractionRepositoryOwn;
    @InjectMocks
    private static AttractionServiceImpl attractionService;

    Map<AttractionFields, Object> fieldsMap;
    List<Attraction> attractions = new ArrayList<>();
    List<Review> reviewList1 = new ArrayList<>();
    List<Review> reviewList2 = new ArrayList<>();
    List<Review> reviewList3 = new ArrayList<>();
    Attraction attraction1;
    Attraction attraction2;
    Attraction attraction3;
    City moscow = new City();
    City california = new City();
    City ny = new City();

    @BeforeEach
    void setUp() {
        attractionService = new AttractionServiceImpl(attractionRepository, attractionRepositoryOwn);
        fieldsMap = new HashMap<>();
        moscow.setName("Moscow");
        california.setName("California");
        ny.setName("New York");
        attraction1 = new Attraction(
                1L,
                "disney land",
                Category.CULTURE,
                23.1,
                54.3,
                "amusement park",
                california);
        attraction2 = new Attraction(
                2L,
                "moscow-city",
                Category.HISTORICAL,
                51.1,
                50.2,
                "region",
                moscow);
        attraction3 = new Attraction(
                3L,
                "gallery",
                Category.CULTURE,
                42.1,
                9.8,
                "exhibition",
                ny
        );
        reviewList1.add(new Review(5, "wow"));
        reviewList1.add(new Review(4, "good"));
        reviewList1.add(new Review(4, "norm"));
        reviewList2.add(new Review(3, "so-so"));
        reviewList2.add(new Review(2, "not bad, but boring"));
        reviewList2.add(new Review(4, "okay"));
        reviewList3.add(new Review(0, "i'm disappointed"));
        reviewList3.add(new Review(1, "so bad, but there is ice cream"));
        reviewList3.add(new Review(2, "((("));
        attraction1.setReviewList(reviewList1);
        attraction2.setReviewList(reviewList2);
        attraction3.setReviewList(reviewList3);
        attractions.add(attraction1);
        attractions.add(attraction2);
        attractions.add(attraction3);
    }

    @Test
    void get_list_of_all_attractions() {
        when(attractionRepository.findAll()).thenReturn(attractions);
        List<Attraction> actual = attractionService.showAllAttractions();
        assertEquals(actual, attractions);
    }

    @Test
    void add_new_attraction() {
        Attraction a = new Attraction();
        a.setId(4L);
        a.setName("Park");
        a.setCategory(Category.CULTURE);
        a.setLongitude(13.23);
        a.setLatitude(98.1);
        a.setInformation("yoooohoooo park");
        a.setMidRate(0.);
        a.setCity(moscow);
        Attraction actual = attractionService.addNewAttraction(a);
        attractions.add(a);
        Attraction founded = attractions.get(3);
        assertEquals(actual.getName(), founded.getName());
        verify(attractionRepository, times(1)).save(a);
    }

    @Test
    void update_attraction() {
        attraction3.setInformation("new info");
        when(attractionRepository.findById(attraction3.getId())).thenReturn(Optional.of(attraction3));
        Attraction updated = attractionService.updateAttraction(attraction3);
        assertEquals(attraction3, updated);
        verify(attractionRepository, times(1)).findById(attraction3.getId());
    }
}