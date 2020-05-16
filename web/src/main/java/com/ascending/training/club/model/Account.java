package com.ascending.training.club.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="accounts")
public class Account {

    public Account(){ }

    public Account(String bankName, String accountType, BigDecimal balance) {
        this.bankName = bankName;
        this.accountType = accountType;
        this.balance = balance;
    }

   /* id 		BIGSERIAL PRIMARY KEY ,
    bank_name       VARCHAR(30),
    account_type    VARCHAR(30),
    balance		NUMERIC(10,2),
    player_id         BIGINT NOT NULL*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",columnDefinition = "SERIAL")
    private Long id;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name="account_type")
    private String accountType;
    @Column(name="balance")
    private BigDecimal balance;
//    @Column(name="player_id")
//    private Long playerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    //@JsonIgnore
    @JsonIgnoreProperties({"account","club"})
    private Player player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
