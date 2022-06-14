package com.spring.daxa.services;

import com.spring.daxa.dto.ReviewDto;
import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.City;
import com.spring.daxa.entity.Review;
import com.spring.daxa.enums.Category;
import com.spring.daxa.repositories.AttractionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class AttractionServiceImplTest {
    @Mock
    private AttractionRepository attractionRepository;
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    @Autowired
    private AttractionServiceImpl attractionService;


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
        attractionService = new AttractionServiceImpl(attractionRepository, entityManager);
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
    void getInfoAndMidRate() {
        List<Object> galleryInfo = new ArrayList<>();
        galleryInfo.add(attraction3.getInformation());
        galleryInfo.add(attraction3.getMidRate());
        when(attractionRepository.getInformationAndMiddleRate("gallery")).thenReturn(galleryInfo);
        List<Object> actual = attractionService.getInformationAndMiddleRate("gallery");
        assertInstanceOf(String.class, actual.get(0));
        assertInstanceOf(Double.class, actual.get(1));
        assertEquals(galleryInfo, actual);
    }

    @Test
    void getReviews() {
        when(attractionRepository.findAll()).thenReturn(attractions);
        List<ReviewDto> reviews = attractionService.showReviewList("disney land");
        for (int i = 0; i < reviewList1.size(); i++) {
            assertEquals(reviews.get(i).getRate(), reviewList1.get(i).getRate());
            assertEquals(reviews.get(i).getReview(), reviewList1.get(i).getReview());
        }
    }


}