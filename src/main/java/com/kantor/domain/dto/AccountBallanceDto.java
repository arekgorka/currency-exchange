package com.kantor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBallanceDto {

    private Long id;
    private Long userId;
    private LocalDate datetime;
    private double pln;
    private double usd;
    private double eur;
    private double chf;
    private double btc;

    public AccountBallanceDto(Long userId, double pln, double usd, double eur, double chf, double btc) {
        this.userId = userId;
        this.pln = pln;
        this.usd = usd;
        this.eur = eur;
        this.chf = chf;
        this.btc = btc;
    }

    public AccountBallanceDto(Long userId, LocalDate datetime, double pln, double usd, double eur, double chf, double btc) {
        this.userId = userId;
        this.datetime = datetime;
        this.pln = pln;
        this.usd = usd;
        this.eur = eur;
        this.chf = chf;
        this.btc = btc;
    }
}
