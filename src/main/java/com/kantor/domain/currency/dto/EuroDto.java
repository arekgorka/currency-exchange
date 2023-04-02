package com.kantor.domain.currency.dto;

import com.kantor.domain.dto.CurrencyDto;
import com.kantor.domain.dto.CurrencyTemplateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.List;

@Getter
public class EuroDto extends CurrencyTemplateDto {

    public EuroDto(Long id, LocalDateTime dateTime, Double buy, Double sell, String name) {
        super(id, dateTime, buy, sell, name);
    }
}
