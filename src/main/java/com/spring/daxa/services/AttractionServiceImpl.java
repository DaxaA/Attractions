package com.spring.daxa.services;

import com.spring.daxa.dto.AttractionDto;
import com.spring.daxa.dto.ReviewDto;
import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.Review;
import com.spring.daxa.enums.AttractionFields;
import com.spring.daxa.enums.Category;
import com.spring.daxa.repositories.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttractionServiceImpl implements AttractionService {
    private final AttractionRepository attractionRepository;
    Map<AttractionFields, Object> fieldsMap;
    private final EntityManager entityManager;
    private final CriteriaBuilder cb;

    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository, EntityManager entityManager) {
        this.attractionRepository = attractionRepository;
        this.entityManager = entityManager;
        cb = entityManager.getCriteriaBuilder();
    }

    //generating clear output for attraction
    @Override
    public List<AttractionDto> createAttractionList(List<Attraction> attractionList) {
        List<AttractionDto> dto = new ArrayList<>();
        for (Attraction attraction: attractionList) {
            dto.add(new AttractionDto(attraction.getId(), attraction.getName(), attraction.getCategory(),
                    attraction.getLongitude(), attraction.getLatitude(), attraction.getInformation(), attraction.getMidRate(), attraction.getCity().getName()));
        }
        return dto;
    }

    //list of all attractions
    @Override
    public List<Attraction> showAllAttractions() {
        return attractionRepository.findAll();
    }

    //general method for search by criteria
    public List<Attraction> getAttractionsByCriteria(Map<AttractionFields, Object> criteria) {
        CriteriaQuery<Attraction> cr = cb.createQuery(Attraction.class);
        Root<Attraction> root = cr.from(Attraction.class);
        Predicate[] predicates = new Predicate[criteria.size()];
        int i = 0;
        for (Map.Entry<AttractionFields, Object> af : criteria.entrySet()) {
            switch (af.getKey()) {
                case LONGITUDE -> predicates[i] = cb.between(root.get("longitude"), Math.abs((Double) af.getValue() - 15), ((Double) af.getValue() + 15));
                case LATITUDE -> predicates[i] = cb.between(root.get("latitude"), Math.abs((Double) af.getValue() - 15), ((Double) af.getValue() + 15));
                case CATEGORY -> predicates[i] = cb.equal(root.get("category"), af.getValue());
                case MIDRATE -> predicates[i] = cb.gt(root.get("midRate"), ((Double) af.getValue() - 0.1));
                case CITY -> predicates[i] = root.get("city").get("name").in(af.getValue());
            }
            i++;
        }
        cr.select(root).where(predicates).orderBy(cb.asc(root.get("longitude")), cb.asc(root.get("latitude")));
        TypedQuery<Attraction> query = entityManager.createQuery(cr);
        return query.getResultList();
    }

    //lis of nearby attractions in a city
    @Override
    public List<Attraction> getAttractionByCityName(String city) {
        fieldsMap = new HashMap<>();
        fieldsMap.put(AttractionFields.CITY, city);
        return getAttractionsByCriteria(fieldsMap);
    }

    //list of nearby attractions
    @Override
    public List<Attraction> getAttractionsByLongitudeAndLatitude(Double longitude, Double latitude) {
        fieldsMap = new HashMap<>();
        fieldsMap.put(AttractionFields.LONGITUDE, longitude);
        fieldsMap.put(AttractionFields.LATITUDE, latitude);
        return getAttractionsByCriteria(fieldsMap);
    }

    //list of nearby attractions filtered by category
    @Override
    public List<Attraction> getAttractionsByLongitudeAndLatitudeAndCategory(Double longitude, Double latitude, String category) {
        fieldsMap = new HashMap<>();
        fieldsMap.put(AttractionFields.LONGITUDE, longitude);
        fieldsMap.put(AttractionFields.LATITUDE, latitude);
        fieldsMap.put(AttractionFields.CATEGORY, convertCategory(category));
        return getAttractionsByCriteria(fieldsMap);
    }

    //list of nearby attractions filtered by middle rate
    @Override
    public List<Attraction> getAttractionsByLongitudeAndLatitudeAndMidRate(Double longitude, Double latitude, Double midRate) {
        fieldsMap = new HashMap<>();
        fieldsMap.put(AttractionFields.LONGITUDE, longitude);
        fieldsMap.put(AttractionFields.LATITUDE, latitude);
        fieldsMap.put(AttractionFields.MIDRATE, midRate);
        return getAttractionsByCriteria(fieldsMap);
    }

    //list attractions in the selected city filtered by category and sorted by longitude
    @Override
    public List<Attraction> getAttractionsByCityAndCategory(String city, String category) {
        fieldsMap = new HashMap<>();
        fieldsMap.put(AttractionFields.CITY, city);
        fieldsMap.put(AttractionFields.CATEGORY, convertCategory(category));
        return getAttractionsByCriteria(fieldsMap);
    }

    //list of attractions in the selected city filtered by middle rate and sorted by longitude
    @Override
    public List<Attraction> getAttractionsByCityAndMidRate(String city, Double midRate) {
        fieldsMap = new HashMap<>();
        fieldsMap.put(AttractionFields.CITY, city);
        fieldsMap.put(AttractionFields.MIDRATE, midRate);
        return getAttractionsByCriteria(fieldsMap);
    }

    //string with a description of the attraction and its middle rate
    @Override
    public List<Object> getInformationAndMiddleRate(String attraction) {
        return attractionRepository.getInformationAndMiddleRate(attraction);
    }

    //list of reviews for selected attraction
    @Override
    public List<ReviewDto> showReviewList(String attraction) {
        Attraction attr = new Attraction();
        for (Attraction a : showAllAttractions()) {
            if (a.getName().equals(attraction)) attr = a;
        }
        List<ReviewDto> dto = new ArrayList<>();
        for (Review rev: attr.getReviewList()) {
            dto.add(new ReviewDto(rev.getId(), rev.getRate(), rev.getReview()));
        }
        return dto;
    }

    //setting review for attraction
    @Override
    public void setReview(String attraction, Integer rate, String review) {
        Attraction attr = new Attraction();
        for (Attraction a : showAllAttractions()) {
            if (a.getName().equals(attraction)) attr = a;
        }
        Review rev = new Review(rate, review);
        rev.setAttraction(attr);
        attr.getReviewList().add(rev);
        attr.setMidRate();
        attractionRepository.save(attr);
    }

    //for converting from string to enum
    Category convertCategory(String category) {
        Category categoryEnum = null;
        if ("military".equals(category)) {
            categoryEnum = Category.MILITARY;
        } else if ("culture".equals(category)) {
            categoryEnum = Category.CULTURE;
        } else if ("religious".equals(category)) {
            categoryEnum = Category.RELIGIOUS;
        } else if ("historical".equals(category)) {
            categoryEnum = Category.HISTORICAL;
        } else if ("nature".equals(category)) {
            categoryEnum = Category.NATURE;
        }
        return categoryEnum;
    }
}
