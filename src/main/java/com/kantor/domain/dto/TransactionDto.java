package com.kantor.domain.dto;

import com.kantor.domain.BuyOrSell;
import com.kantor.domain.CurrenciesEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TransactionDto {
    private Long id;
    private Long userId;
    private LocalDateTime datetime;
    private BuyOrSell buyOrSell;
    private String curFrom;
    private double qtyFrom;
    private String curTo;
    private double sum;
    private double exchangeRate;

    public TransactionDto(LocalDateTime datetime, BuyOrSell buyOrSell, String curFrom,
                          double qtyFrom, String curTo, double sum, double exchangeRate) {
        this.datetime = datetime;
        this.buyOrSell = buyOrSell;
        this.curFrom = curFrom;
        this.qtyFrom = qtyFrom;
        this.curTo = curTo;
        this.sum = sum;
        this.exchangeRate = exchangeRate;
    }
}
