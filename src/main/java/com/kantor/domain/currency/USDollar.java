package com.kantor.domain.currency;

import com.kantor.domain.Currency;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Entity(name = "USDOLLAR")
public class USDollar extends Currency {

    public USDollar(Double buy, Double sell, String name) {
        super(buy, sell, name);
    }
}
