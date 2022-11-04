package com.kantor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "ACCOUNT_BALLANCES")
public class AccountBallance {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "DATETIME")
    private LocalDate datetime;

    @Column(name = "PLN")
    private double pln;

    @Column(name = "USD")
    private double usd;

    @Column(name = "EUR")
    private double eur;

    @Column(name = "CHF")
    private double chf;

    @Column(name = "BTC")
    private double btc;


}
