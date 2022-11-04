package com.kantor.domain.currency;

import com.kantor.domain.Currency;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Entity(name = "EURO")
public class Euro extends Currency {

    public Euro(Double buy, Double sell, String name) {
        super(buy, sell, name);
    }
}
