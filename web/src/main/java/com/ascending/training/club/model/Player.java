package com.ascending.training.club.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="players")
public class Player {
//    id              BIGSERIAL  PRIMARY KEY,
//    name            VARCHAR(30) not null unique,
//    email           VARCHAR(50),
//    address         VARCHAR(150),
//    hired_date      varchar(6) default to_char(CURRENT_DATE, 'yyyymm'),
//    position        varchar(20),
//    club_id         BIGINT NOT NULL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",columnDefinition = "SERIAL")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="address")
    private String address;
    @Column(name="hired_date")
    @CreationTimestamp
    private LocalDate hireDate;
    @Column(name="position")
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @JsonIgnoreProperties({"player"})
    private Club club;

    @JsonIgnore
    //@JsonIgnoreProperties({"player"})
    @OneToMany(mappedBy = "player",cascade = CascadeType.REMOVE, fetch=FetchType.LAZY)
    private Set<Account> account;

    public Player() {}

    public Player(String name, String email, String address, LocalDate hireDate, String position) {
        //this.id=id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.hireDate = hireDate;
        this.position = position;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Set<Account> getAccount() {
        return account;
    }

    public void setAccount(Set<Account> account) {
        this.account = account;
    }
}
