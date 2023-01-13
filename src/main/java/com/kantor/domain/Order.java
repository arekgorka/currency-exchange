package com.kantor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name = "EXCHANGE_RATE")
    private double exchangeRate;

    @NotNull
    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    public Order(User user, BuyOrSell buyOrSell, String curFrom,
                 double qtyFrom, String curTo, double exchangeRate) {
        this.user = user;
        this.datetime = LocalDateTime.now();
        this.buyOrSell = buyOrSell;
        this.curFrom = curFrom;
        this.qtyFrom = qtyFrom;
        this.curTo = curTo;
        this.exchangeRate = exchangeRate;
        this.orderStatus = OrderStatus.ORDERED;
    }
}
