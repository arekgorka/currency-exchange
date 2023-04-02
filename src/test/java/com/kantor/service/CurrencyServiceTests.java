package com.kantor.service;

import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import com.kantor.domain.currency.USDollar;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.repository.EuroRepository;
import com.kantor.repository.SwissFrancRepository;
import com.kantor.repository.USDollarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurrencyServiceTests {

    private final USDollar usDollar = new USDollar(4.80,4.75,"USD");
    private final Euro euro = new Euro(4.50,4.40,"EUR");
    private final SwissFranc swissFranc = new SwissFranc(4.90, 4.80,"CHF");

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private USDollarRepository usDollarRepository;
    @Autowired
    private EuroRepository euroRepository;
    @Autowired
    private SwissFrancRepository swissFrancRepository;


    @Test
    void getCurrencyBuyFromRepositoryTest() throws CurrencyNotFoundException {
        //Given
        usDollarRepository.save(usDollar);
        euroRepository.save(euro);
        swissFrancRepository.save(swissFranc);
        //When
        double usd = currencyService.getCurrencyBuyFromRepository("usd");
        double eur = currencyService.getCurrencyBuyFromRepository("eur");
        double chf = currencyService.getCurrencyBuyFromRepository("chf");
        double pln = currencyService.getCurrencyBuyFromRepository("pln");
        //Then
        assertEquals(4.80,usd);
        assertEquals(4.50,eur);
        assertEquals(4.90,chf);
        assertEquals(1,pln);
        //CleanUp
        usDollarRepository.delete(usDollar);
        euroRepository.delete(euro);
        swissFrancRepository.delete(swissFranc);
    }

    @Test
    void getCurrencyBuyWithWrongCurrencyTest() {
        //Given
        //When & Then
        assertThrows(
                CurrencyNotFoundException.class,
                ()->currencyService.getCurrencyBuyFromRepository("zlotys"),
                "Wrong currency");
    }

    @Test
    void getCurrencySellFromRepositoryTest() throws CurrencyNotFoundException {
        //Given
        usDollarRepository.save(usDollar);
        euroRepository.save(euro);
        swissFrancRepository.save(swissFranc);
        //When
        double usd = currencyService.getCurrencySellFromRepository("usd");
        double eur = currencyService.getCurrencySellFromRepository("eur");
        double chf = currencyService.getCurrencySellFromRepository("chf");
        double pln = currencyService.getCurrencySellFromRepository("pln");
        //Then
        assertEquals(4.75,usd);
        assertEquals(4.40,eur);
        assertEquals(4.80,chf);
        assertEquals(1,pln);
        //CleanUp
        usDollarRepository.delete(usDollar);
        euroRepository.delete(euro);
        swissFrancRepository.delete(swissFranc);
    }

    @Test
    void getCurrencySellWithWrongCurrencyTest() {
        //Given
        //When & Then
        assertThrows(
                CurrencyNotFoundException.class,
                ()->currencyService.getCurrencySellFromRepository("zlotys"),
                "Wrong currency");
    }
}
