package com.ascending.training.club.model;

import java.time.LocalDate;

public class ClubJDBC {
//    id                BIGSERIAL  PRIMARY KEY,
//    name              VARCHAR(30) not null unique,
//    description       VARCHAR(150),
//    location          VARCHAR(100),
//    start_date	      varchar(6) default to_char(CURRENT_DATE, 'yyyymm')

    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDate startDate;

    public ClubJDBC() {
    }

    public ClubJDBC(Long id, String name, String description, String location, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
