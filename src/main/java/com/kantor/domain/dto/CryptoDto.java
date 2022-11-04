package com.kantor.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CryptoDto {

    private String symbol;
    private double buy;
    private double sell;
}
