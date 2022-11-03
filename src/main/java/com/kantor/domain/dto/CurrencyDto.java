package com.kantor.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
public class CurrencyDto {

    private String code;
    private double buy;
    private double sell;
}
