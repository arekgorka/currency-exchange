package com.kantor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "ORDERS")
public class Order {

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

    @NotNull
    @Column(name = "TRANSACTION")
    private BuyOrSell buyOrSell;

    @NotNull
    @Column(name = "CUR_FROM")
    private CurrenciesEnum curFrom;

    @NotNull
    @Column(name = "QTY_FROM")
    private double qtyFrom;

    @NotNull
    @Column(name = "CUR_TO")
    private CurrenciesEnum curTo;

    @NotNull
    @Column(name = "QTY_TO")
    private double qtyTo;

    @NotNull
    @Column(name = "EXCHANGE_RATE")
    private double exchangeRate;

    @NotNull
    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    public Order(User user, BuyOrSell buyOrSell, CurrenciesEnum curFrom,
                 double qtyFrom, CurrenciesEnum curTo, double qtyTo, double exchangeRate) {
        this.user = user;
        this.datetime = LocalDate.now();
        this.buyOrSell = buyOrSell;
        this.curFrom = curFrom;
        this.qtyFrom = qtyFrom;
        this.curTo = curTo;
        this.qtyTo = qtyTo;
        this.exchangeRate = exchangeRate;
        this.orderStatus = OrderStatus.ORDERED;
    }
}
