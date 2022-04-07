package com.spring.daxa.services;

import com.spring.daxa.entity.Attraction;
import com.spring.daxa.enums.AttractionFields;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AttractionFilter {
    public static List<Attraction> filter(List<Attraction> personList, Map<AttractionFields, Object> way) {
        List<Predicate<Attraction>> filters = way.entrySet().stream()
                .map(AttractionFilter::mapToFilterBy).collect(Collectors.toList());
        Predicate<Attraction> multipleFilter = matchingAll(filters);
        return personList.stream().filter(multipleFilter).collect(Collectors.toList());
    }

    private static Predicate<Attraction> mapToFilterBy(Map.Entry<AttractionFields, Object> filterMapEntry) {
        return to -> to.getProperty(filterMapEntry.getKey()) == filterMapEntry.getValue();
    }

    public static <T> Predicate<T> matchingAll(List<Predicate<T>> predicates) {
        Predicate<T> multiPredicate = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            multiPredicate = multiPredicate.and(predicates.get(i));
        }
        return multiPredicate;
    }
}
