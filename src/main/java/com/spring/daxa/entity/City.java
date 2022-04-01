package com.spring.daxa.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "number")
    private Long number;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Attraction> attractionList;

    public City() {}

    public City(int id, String name, Long number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public List<Attraction> getAttractionList() {
        return attractionList;
    }

    public void setAttractionList(List<Attraction> attractionList) {
        this.attractionList = attractionList;
    }
}
