package com.spring.daxa.services;

import com.spring.daxa.dto.ReviewDto;
import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.City;
import com.spring.daxa.entity.Review;
import com.spring.daxa.enums.AttractionFields;
import com.spring.daxa.enums.Category;
import com.spring.daxa.repositories.AttractionRepository;
import com.spring.daxa.repositories.AttractionRepositoryOwnImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AttractionServiceImplTest {
    @Mock
    private AttractionRepository attractionRepository;
    @Mock
    private AttractionRepositoryOwnImpl attractionRepositoryOwn;
    @InjectMocks
    private AttractionServiceImpl attractionService;


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
    void getAll() {
        when(attractionRepository.findAll()).thenReturn(attractions);
        List<Attraction> attractionList = attractionService.showAllAttractions();
        assertEquals(attractionList, attractions);
    }

    @Test
    void getByCityName() {
        fieldsMap.put(AttractionFields.CITY, "Moscow");
        when(attractionRepositoryOwn.getAttractionsByCriteria(fieldsMap)).thenReturn(List.of(attraction2));
        List<Attraction> moscowAttractions = attractionService.getAttractionByCityName("Moscow");
        assertEquals(List.of(attraction2), moscowAttractions);
        verify(attractionRepositoryOwn, times(1)).getAttractionsByCriteria(fieldsMap);
    }

    @Test
    void getByLongitudeAndLatitude() {
        fieldsMap.put(AttractionFields.LONGITUDE, 20.);
        fieldsMap.put(AttractionFields.LATITUDE, 50.);
        when(attractionRepositoryOwn.getAttractionsByCriteria(fieldsMap)).thenReturn(List.of(attraction1));
        List<Attraction> nearAttractions = attractionService.getAttractionsByLongitudeAndLatitude(20., 50.);
        assertEquals(List.of(attraction1), nearAttractions);
        verify(attractionRepositoryOwn, times(1)).getAttractionsByCriteria(fieldsMap);
    }

    @Test
    void getNearByCategory() {
        fieldsMap.put(AttractionFields.LONGITUDE, 20.);
        fieldsMap.put(AttractionFields.LATITUDE, 50.);
        fieldsMap.put(AttractionFields.CATEGORY, Category.CULTURE);
        when(attractionRepositoryOwn.getAttractionsByCriteria(fieldsMap)).thenReturn(List.of(attraction1, attraction3));
        List<Attraction> categoryAttractions = attractionService.getAttractionsByLongitudeAndLatitudeAndCategory(20., 50., "culture");
        assertEquals(List.of(attraction1, attraction3), categoryAttractions);
        verify(attractionRepositoryOwn, times(1)).getAttractionsByCriteria(fieldsMap);
    }

    @Test
    void getNearByMidRate() {
        fieldsMap.put(AttractionFields.LONGITUDE, 20.);
        fieldsMap.put(AttractionFields.LATITUDE, 50.);
        fieldsMap.put(AttractionFields.MIDRATE, 3.);
        when(attractionRepositoryOwn.getAttractionsByCriteria(fieldsMap)).thenReturn(List.of(attraction1, attraction2));
        List<Attraction> midrateAttractions = attractionService.getAttractionsByLongitudeAndLatitudeAndMidRate(20., 50., 3.);
        assertEquals(List.of(attraction1, attraction2), midrateAttractions);
        verify(attractionRepositoryOwn, times(1)).getAttractionsByCriteria(fieldsMap);
    }

    @Test
    void getByCityAndCategory() {
        fieldsMap.put(AttractionFields.CITY, "California");
        fieldsMap.put(AttractionFields.CATEGORY, Category.CULTURE);
        when(attractionRepositoryOwn.getAttractionsByCriteria(fieldsMap)).thenReturn(List.of(attraction1));
        List<Attraction> ccAttractions = attractionService.getAttractionsByCityAndCategory("California", "culture");
        assertEquals(List.of(attraction1), ccAttractions);
        verify(attractionRepositoryOwn, times(1)).getAttractionsByCriteria(fieldsMap);
    }

    @Test
    void getByCityAndMidRate() {
        fieldsMap.put(AttractionFields.CITY, "California");
        fieldsMap.put(AttractionFields.MIDRATE, 3.);
        when(attractionRepositoryOwn.getAttractionsByCriteria(fieldsMap)).thenReturn(List.of(attraction1));
        List<Attraction> midrateAttractions = attractionService.getAttractionsByCityAndMidRate("California", 3.);
        assertEquals(List.of(attraction1), midrateAttractions);
        verify(attractionRepositoryOwn, times(1)).getAttractionsByCriteria(fieldsMap);
    }

    @Test
    void getInfoAndMidRate() {
        List<Object> galleryInfo = new ArrayList<>();
        galleryInfo.add(attraction3.getInformation());
        galleryInfo.add(attraction3.getMidRate());
        when(attractionRepository.getInformationAndMiddleRate("gallery")).thenReturn(galleryInfo);
        List<Object> actual = attractionService.getInformationAndMiddleRate("gallery");
        assertInstanceOf(String.class, actual.get(0));
        assertInstanceOf(Double.class, actual.get(1));
        assertEquals(galleryInfo, actual);
        verify(attractionRepository, times(1)).getInformationAndMiddleRate("gallery");
    }

    @Test
    void getReviews() {
        when(attractionRepository.findAll()).thenReturn(attractions);
        List<ReviewDto> reviews = attractionService.showReviewList("disney land");
        for (int i = 0; i < reviewList1.size(); i++) {
            assertEquals(reviews.get(i).getRate(), reviewList1.get(i).getRate());
            assertEquals(reviews.get(i).getReview(), reviewList1.get(i).getReview());
        }
        verify(attractionRepository, times(1)).findAll();
    }
}