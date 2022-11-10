package com.kantor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CURRENCIES")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Currency {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "DATETIME")
    private LocalDateTime dateTime;

    @Column(name = "BUY")
    private Double buy;

    @Column(name = "SELL")
    private Double sell;

    @Column(name = "NAME")
    private String name;

    public Currency(Double buy, Double sell, String name) {
        this.dateTime = LocalDateTime.now();
        this.buy = buy;
        this.sell = sell;
        this.name = name;
    }
}
