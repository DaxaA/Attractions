package com.spring.daxa.repositories;

import com.spring.daxa.entity.Attraction;
import com.spring.daxa.enums.AttractionFields;

import java.util.List;
import java.util.Map;

public interface AttractionRepositoryOwn {
    List<Attraction> getAttractionsByCriteria(Map<AttractionFields, Object> criteria);
}
