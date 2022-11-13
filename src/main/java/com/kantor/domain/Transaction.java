package com.kantor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "DATETIME")
    private LocalDateTime datetime;

    @NotNull
    @Column(name = "TRANSACTION")
    private BuyOrSell buyOrSell;

    @NotNull
    @Column(name = "CUR_FROM")
    private String curFrom;

    @NotNull
    @Column(name = "QTY_FROM")
    private double qtyFrom;

    @NotNull
    @Column(name = "CUR_TO")
    private String curTo;

    @NotNull
    @Column(name = "SUM")
    private double sum;

    @NotNull
    @Column(name = "EXCHANGE_RATE")
    private double exchangeRate;

    public Transaction(User user, BuyOrSell buyOrSell, String curFrom, double qtyFrom,
                       String curTo) {
        this.user = user;
        this.datetime = LocalDateTime.now();
        this.buyOrSell = buyOrSell;
        this.curFrom = curFrom;
        this.qtyFrom = qtyFrom;
        this.curTo = curTo;
    }

    public Transaction(User user, BuyOrSell buyOrSell, String curFrom, double qtyFrom,
                       String curTo, double sum, double exchangeRate) {
        this.user = user;
        this.datetime = LocalDateTime.now();
        this.buyOrSell = buyOrSell;
        this.curFrom = curFrom;
        this.qtyFrom = qtyFrom;
        this.curTo = curTo;
        this.sum = sum;
        this.exchangeRate = exchangeRate;
    }
}
