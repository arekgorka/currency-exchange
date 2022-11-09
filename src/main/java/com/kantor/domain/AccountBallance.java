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

    public AccountBallance(User user) {
        this.user = user;
        this.datetime = LocalDate.now();
        this.pln = 0.00;
        this.usd = 0.00;
        this.eur = 0.00;
        this.chf = 0.00;
        this.btc = 0.00;
    }

    public AccountBallance(User user, double pln, double usd, double eur, double chf, double btc) {
        this.user = user;
        this.datetime = LocalDate.now();
        this.pln = pln;
        this.usd = usd;
        this.eur = eur;
        this.chf = chf;
        this.btc = btc;
    }
}
