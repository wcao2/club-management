package com.ascending.training.club.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Clubs")
public class Club {
//    id                BIGSERIAL  PRIMARY KEY,
//    name              VARCHAR(30) not null unique,
//    description       VARCHAR(150),
//    location          VARCHAR(100),
//    start_date	      varchar(6) default to_char(CURRENT_DATE, 'yyyymm')

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "SERIAL")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="location")
    private String location;
    @Column(name="start_date")
    //@CreationTimestamp
    private LocalDate startDate;

    @OneToMany(mappedBy = "club",cascade = CascadeType.REMOVE, fetch=FetchType.LAZY)
    @JsonIgnoreProperties({"club","account"})
    private List<Player> player;

    public Club() {
    }

    public Club(Long id, String name, String description, String location, LocalDate startDate) {
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

    public List<Player> getPlayer() {
        return player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }
}
