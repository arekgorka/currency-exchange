package com.kantor.domain.currency.dto;

import com.kantor.domain.dto.CurrencyTemplateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class USDollarDto extends CurrencyTemplateDto {

    public USDollarDto(Long id, LocalDateTime dateTime, Double buy, Double sell, String name) {
        super(id, dateTime, buy, sell, name);
    }
}
