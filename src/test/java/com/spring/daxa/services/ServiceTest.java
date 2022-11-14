package com.spring.daxa.services;

import com.spring.daxa.dto.AttractionDto;
import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.City;
import com.spring.daxa.entity.Review;
import com.spring.daxa.enums.AttractionFields;
import com.spring.daxa.enums.Category;
import com.spring.daxa.repositories.AttractionRepository;
import com.spring.daxa.repositories.impl.AttractionRepositoryOwnImpl;
import com.spring.daxa.services.mapper.AttractionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceTest {
    @Spy
    private static AttractionMapper attractionMapper;
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
        attractionService = new AttractionServiceImpl(attractionRepository, attractionRepositoryOwn, attractionMapper);
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
        assertEquals(actual.size(), attractions.size());
        for (int i = 0; i < attractions.size(); i++) {
            assertEquals(actual.get(i).getId(), attractions.get(i).getId());
            assertEquals(actual.get(i).getName(), attractions.get(i).getName());
        }
    }

    @Test
    void add_new_attraction() {
        Attraction a = new Attraction(
                4L,
                "Park",
                Category.CULTURE,
                13.23,
                98.1,
                "yoooohooo park",
                moscow
        );
        Attraction actual = attractionService.addNewAttraction(a);
        assertEquals(actual.getId(), a.getId());
        assertEquals(actual.getName(), a.getName());
        assertEquals(actual.getCategory(), a.getCategory());
        assertEquals(actual.getLongitude(), a.getLongitude());
        assertEquals(actual.getLatitude(), a.getLatitude());
        assertEquals(actual.getInformation(), a.getInformation());
        assertEquals(actual.getCity(), a.getCity());
        //verify(attractionRepository, times(1)).save(a);
    }

    @Test
    void update_attraction() {
        AttractionDto attractionDto = new AttractionDto(
                3L,
                "gallery",
                Category.CULTURE,
                42.1,
                9.8,
                "new info",
                attraction3.getMidRate(),
                ny.getName()
        );
        when(attractionRepository.findAttractionById(attraction3.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> attractionService.updateAttraction(attractionDto));
        //verify(attractionRepository, times(1)).findById(attraction3.getId());
    }
}