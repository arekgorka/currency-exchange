package com.kantor.domain.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BitcoinDto {
    private Long id;
    private LocalDate dateTime;
    private Double buy;
    private Double sell;
    private String name;
}
