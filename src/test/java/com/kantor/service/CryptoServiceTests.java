package com.kantor.service;

import com.kantor.domain.currency.Bitcoin;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.repository.BitcoinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CryptoServiceTests {

    private final Bitcoin bitcoin = new Bitcoin(41000.3544,40500.3544,"BTC");

    @Autowired
    private CryptoService cryptoService;
    @Autowired
    private BitcoinRepository bitcoinRepository;

    @Test
    void getCryptoBuyFromRepositoryTest() throws CurrencyNotFoundException {
        //Given
        bitcoinRepository.save(bitcoin);
        //When
        double btc = cryptoService.getCryptoBuyFromRepository("bitcoin");
        //Then
        assertEquals(41000.3544, btc);
    }

    @Test
    void getCryptoBuyWithWrongCurrencyTest() {
        //Given
        //When & Then
        assertThrows(
                CurrencyNotFoundException.class,
                ()->cryptoService.getCryptoBuyFromRepository(""),
                "Wrong currency");
    }

    @Test
    void getCryptoSellFromRepositoryTest() throws CurrencyNotFoundException {
        //Given
        bitcoinRepository.save(bitcoin);
        //When
        double btc = cryptoService.getCryptoSellFromRepository("bitcoin");
        //Then
        assertEquals(41000.3544, btc);
    }

    @Test
    void getCryptoSellWithWrongCurrencyTest() {
        //Given
        //When & Then
        assertThrows(
                CurrencyNotFoundException.class,
                ()->cryptoService.getCryptoSellFromRepository(""),
                "Wrong currency");
    }

}
