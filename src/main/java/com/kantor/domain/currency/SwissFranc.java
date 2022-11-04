package com.kantor.domain.currency;

import com.kantor.domain.Currency;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Entity(name = "SWISSFRANC")
public class SwissFranc extends Currency {

    public SwissFranc(Double buy, Double sell, String name) {
        super(buy, sell, name);
    }
}
