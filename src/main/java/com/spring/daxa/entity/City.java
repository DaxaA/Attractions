package com.spring.daxa.entity;

import javax.persistence.*;

@Entity
@Table
public class City {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;
    @Column
    private Long number;

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
}
