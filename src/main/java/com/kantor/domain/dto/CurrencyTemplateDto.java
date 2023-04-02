package com.kantor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CurrencyTemplateDto {
    private Long id;
    private LocalDateTime dateTime;
    private Double buy;
    private Double sell;
    private String name;
}
