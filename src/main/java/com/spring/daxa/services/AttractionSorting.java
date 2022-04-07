package com.spring.daxa.services;

import com.spring.daxa.entity.Attraction;
import com.spring.daxa.enums.AttractionFields;
import com.spring.daxa.enums.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AttractionSorting {
    private static final Map<AttractionFields, Comparator<Attraction>> COMPARATOR_MAP = Map.of(
            AttractionFields.ID, Comparator.comparing(Attraction::getId),
            AttractionFields.NAME, Comparator.comparing(Attraction::getName),
            AttractionFields.CATEGORY, Comparator.comparing(Attraction::getCategory),
            AttractionFields.LONGITUDE, Comparator.comparing(Attraction::getLongitude),
            AttractionFields.LATITUDE, Comparator.comparing(Attraction::getLatitude),
            AttractionFields.INFORMATION, Comparator.comparing(Attraction::getInformation),
            AttractionFields.MIDRATE, Comparator.comparing(Attraction::getMidRate)
    );

    public static List<Attraction> sorting(List<Attraction> personList, Map<AttractionFields, Order> way) {
        Stream<Attraction> attractionStream = personList.stream();
        List<Attraction> sorted = new ArrayList<>();
        for (Map.Entry<AttractionFields, Order> sortWay: way.entrySet()) {
            sorted = attractionStream.sorted(getComparator(sortWay.getKey(), sortWay.getValue()))
                    .collect(Collectors.toList());
        }
        return sorted;
    }

    private static Comparator<Attraction> getComparator(AttractionFields field, Order way) {
        Comparator<Attraction> comparator = COMPARATOR_MAP.get(field);
        if(Order.DESCENDING.equals(way)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
