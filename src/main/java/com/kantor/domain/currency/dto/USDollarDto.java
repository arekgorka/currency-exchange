package com.kantor.domain.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class USDollarDto {
    private Long id;
    private LocalDateTime dateTime;
    private Double buy;
    private Double sell;
    private String name;
}
