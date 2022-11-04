package com.kantor.domain.currency;

import com.kantor.domain.Currency;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Entity(name = "BITCOIN")
public class Bitcoin extends Currency {

    public Bitcoin(Double buy, Double sell, String name) {
        super(buy, sell, name);
    }
}
