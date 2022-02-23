package com.spring.daxa.entity;

import javax.persistence.*;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "number")
    private Long number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Attraction attraction;

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

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }
}
