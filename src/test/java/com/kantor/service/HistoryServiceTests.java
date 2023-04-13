package com.kantor.service;

import com.kantor.domain.Currencies;
import com.kantor.domain.currency.Bitcoin;
import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import com.kantor.domain.currency.USDollar;
import com.kantor.domain.currency.dto.BitcoinDto;
import com.kantor.domain.currency.dto.EuroDto;
import com.kantor.domain.currency.dto.SwissFrancDto;
import com.kantor.domain.currency.dto.USDollarDto;
import com.kantor.domain.dto.CurrencyTemplateDto;
import com.kantor.exception.CurrencyNotSupportedException;
import com.kantor.repository.BitcoinRepository;
import com.kantor.repository.EuroRepository;
import com.kantor.repository.SwissFrancRepository;
import com.kantor.repository.USDollarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HistoryServiceTests {

    @Autowired
    private HistoryService historyService;
    @Autowired
    private USDollarRepository usDollarRepository;
    @Autowired
    private EuroRepository euroRepository;
    @Autowired
    private SwissFrancRepository swissFrancRepository;
    @Autowired
    private BitcoinRepository bitcoinRepository;

    @Test
    void getUSDollarHistoryTest() {
        //Given
        USDollar usDollar1 = new USDollar(4.45,4.47, Currencies.USD);
        USDollar usDollar2 = new USDollar(4.44,4.46, Currencies.USD);
        USDollar usDollar3 = new USDollar(4.41,4.43, Currencies.USD);
        USDollar usDollar4 = new USDollar(4.50,4.52,Currencies.USD);
        usDollar4.setDateTime(LocalDateTime.now().minusDays(5));
        usDollarRepository.save(usDollar1);
        usDollarRepository.save(usDollar2);
        usDollarRepository.save(usDollar3);
        usDollarRepository.save(usDollar4);
        //When
        List<USDollarDto> usDollarList = historyService.getUSDollarHistory();
        //Then
        assertEquals(3, usDollarList.size());
        //CleanUp
        usDollarRepository.delete(usDollar1);
        usDollarRepository.delete(usDollar2);
        usDollarRepository.delete(usDollar3);
        usDollarRepository.delete(usDollar4);
    }

    @Test
    void getEuroHistoryTest() {
        //Given
        Euro euro1 = new Euro(4.45,4.47, Currencies.EUR);
        Euro euro2 = new Euro(4.44,4.46, Currencies.EUR);
        Euro euro3 = new Euro(4.41,4.43, Currencies.EUR);
        Euro euro4 = new Euro(4.50,4.52,Currencies.EUR);
        euro4.setDateTime(LocalDateTime.now().minusDays(5));
        euroRepository.save(euro1);
        euroRepository.save(euro2);
        euroRepository.save(euro3);
        euroRepository.save(euro4);
        //When
        List<EuroDto> euroList = historyService.getEuroHistory();
        //Then
        assertEquals(3, euroList.size());
        //CleanUp
        euroRepository.delete(euro1);
        euroRepository.delete(euro2);
        euroRepository.delete(euro3);
        euroRepository.delete(euro4);
    }

    @Test
    void getSwissFrancHistoryTest() {
        //Given
        SwissFranc swissFranc1 = new SwissFranc(4.45,4.47, Currencies.CHF);
        SwissFranc swissFranc2 = new SwissFranc(4.44,4.46, Currencies.CHF);
        SwissFranc swissFranc3 = new SwissFranc(4.41,4.43, Currencies.CHF);
        SwissFranc swissFranc4 = new SwissFranc(4.50,4.52,Currencies.CHF);
        swissFranc4.setDateTime(LocalDateTime.now().minusDays(5));
        swissFrancRepository.save(swissFranc1);
        swissFrancRepository.save(swissFranc2);
        swissFrancRepository.save(swissFranc3);
        swissFrancRepository.save(swissFranc4);
        //When
        List<SwissFrancDto> swissFrancList = historyService.getSwissFrancHistory();
        //Then
        assertEquals(3, swissFrancList.size());
        //CleanUp
        swissFrancRepository.delete(swissFranc1);
        swissFrancRepository.delete(swissFranc2);
        swissFrancRepository.delete(swissFranc3);
        swissFrancRepository.delete(swissFranc4);
    }

    @Test
    void getBitcoinHistoryTest() {
        //Given
        Bitcoin bitcoin1 = new Bitcoin(4.45,4.47, Currencies.CHF);
        Bitcoin bitcoin2 = new Bitcoin(4.44,4.46, Currencies.CHF);
        Bitcoin bitcoin3 = new Bitcoin(4.41,4.43, Currencies.CHF);
        Bitcoin bitcoin4 = new Bitcoin(4.50,4.52,Currencies.CHF);
        bitcoin4.setDateTime(LocalDateTime.now().minusDays(5));
        bitcoinRepository.save(bitcoin1);
        bitcoinRepository.save(bitcoin2);
        bitcoinRepository.save(bitcoin3);
        bitcoinRepository.save(bitcoin4);
        //When
        List<BitcoinDto> bitcoinList = historyService.getBitcoinHistory();
        //Then
        assertEquals(3, bitcoinList.size());
        //CleanUp
        bitcoinRepository.delete(bitcoin1);
        bitcoinRepository.delete(bitcoin2);
        bitcoinRepository.delete(bitcoin3);
        bitcoinRepository.delete(bitcoin4);
    }

    @Test
    void getCurrencyHistoryTest() throws CurrencyNotSupportedException {
        //Given
        USDollar usDollar1 = new USDollar(4.45,4.47, Currencies.USD);
        USDollar usDollar2 = new USDollar(4.44,4.46, Currencies.USD);
        USDollar usDollar3 = new USDollar(4.41,4.43, Currencies.USD);
        USDollar usDollar4 = new USDollar(4.50,4.52,Currencies.USD);
        usDollar4.setDateTime(LocalDateTime.now().minusDays(5));
        usDollarRepository.save(usDollar1);
        usDollarRepository.save(usDollar2);
        usDollarRepository.save(usDollar3);
        usDollarRepository.save(usDollar4);
        //When
        List<CurrencyTemplateDto> usDollarList = historyService.getCurrencyHistory(Currencies.USD);
        //Then
        assertEquals(3, usDollarList.size());
        //CleanUp
        usDollarRepository.delete(usDollar1);
        usDollarRepository.delete(usDollar2);
        usDollarRepository.delete(usDollar3);
        usDollarRepository.delete(usDollar4);
    }

    @Test
    void getCurrencyHistoryWithNotSupportedCurrencyTest() {
        //Given
        //When & Then
        assertThrows(
                CurrencyNotSupportedException.class,
                ()-> historyService.getCurrencyHistory("AUD"),
                "Currency not supported"
        );
    }

}
