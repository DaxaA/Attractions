package com.spring.daxa.services.mapper;

import com.spring.daxa.dto.AttractionDto;
import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttractionMapper {

    public AttractionDto toDto(Attraction attraction){
        return new AttractionDto(
                attraction.getId(),
                attraction.getName(),
                attraction.getCategory(),
                attraction.getLongitude(),
                attraction.getLatitude(),
                attraction.getInformation(),
                attraction.getMidRate(),
                attraction.getCity().getName()
        );
    }

    public List<AttractionDto> mapToDtoList(List<Attraction> attractionList){
        return attractionList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Attraction toAttraction(AttractionDto attractionDto){
        City city = new City();
        city.setName(attractionDto.getCity());
        return new Attraction(
                attractionDto.getId(),
                attractionDto.getName(),
                attractionDto.getCategory(),
                attractionDto.getLongitude(),
                attractionDto.getLatitude(),
                attractionDto.getInformation(),
                city
        );
    }
}
