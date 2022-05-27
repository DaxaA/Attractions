package com.spring.daxa.repositories;

import com.spring.daxa.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    @Query(value = "select information, middle_rate from attractions where name = ?1", nativeQuery = true)
    List<Object> getInformationAndMiddleRate(String attraction);
}
