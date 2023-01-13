package com.kantor.domain.dto;

import com.kantor.domain.BuyOrSell;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime dateTime;
    private BuyOrSell buyOrSell;
    private String curFrom;
    private double qtyFrom;
    private String curTo;
    private double exchangeRate;

    public OrderDto(LocalDateTime dateTime, BuyOrSell buyOrSell, String curFrom, double qtyFrom, String curTo, double exchangeRate) {
        this.dateTime = dateTime;
        this.buyOrSell = buyOrSell;
        this.curFrom = curFrom;
        this.qtyFrom = qtyFrom;
        this.curTo = curTo;
        this.exchangeRate = exchangeRate;
    }

}

