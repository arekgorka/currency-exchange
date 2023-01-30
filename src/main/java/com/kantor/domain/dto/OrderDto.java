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

    //do testów
    public OrderDto(BuyOrSell buyOrSell, String curFrom, double qtyFrom, String curTo, double exchangeRate) {
        this.buyOrSell = buyOrSell;
        this.curFrom = curFrom;
        this.qtyFrom = qtyFrom;
        this.curTo = curTo;
        this.exchangeRate = exchangeRate;
    }

}

