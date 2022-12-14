package com.kantor.service;

import com.kantor.domain.*;
import com.kantor.domain.dto.TransactionDto;
import com.kantor.exception.AccountBallanceNotFoundException;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.exception.WrongValidationException;
import com.kantor.mapper.AccountBallanceMapper;
import com.kantor.mapper.TransactionMapper;
import com.kantor.repository.TransactionRepository;
import com.kantor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountBallanceService accountBallanceService;
    private final CurrencyService currencyService;
    private final CryptoService cryptoService;
    private final UserRepository userRepository;

    public void createBuyTransaction(final Long userId, final TransactionDto transactionDto)
            throws UserNotFoundException, AccountBallanceNotFoundException, WrongValidationException, CurrencyNotFoundException {
        Transaction transaction = transactionMapper.mapToTransaction(userId, transactionDto);
        //pobranie kursu waluty którą chcemy kupić
        String curFrom = transaction.getCurFrom();
        String curTo = transaction.getCurTo();
        double buy = getBuyForAnyCurrencyFromRepository(curFrom); //getBuyForAnyCurrency(curFrom);
        double buyQty = transaction.getQtyFrom();
        //wyliczenie sumy
        double sum = buyQty * buy;
        //pobranie stanu konta - sprawdzenie czy znajduje się tyle na koncie
        if (accountBallanceValidationForBuy(userId,sum,curTo)) {
            //wyliczenie zmian stanu konta
            double oldCurFrom = accountBallanceService.getCurrencyAccountBallance(userId,curFrom);
            double newCurFrom = oldCurFrom + buyQty;
            double oldCurTo = accountBallanceService.getCurrencyAccountBallance(userId,curTo);
            double newCurTo = oldCurTo - sum;
            //zaktualizowanie stanu konta - zapis nowego stanu konta
            AccountBallance newAccountBallance = changeToNewAccountBallance(userId);
            updateCurBallance(curFrom, newCurFrom, newAccountBallance);
            updateCurBallance(curTo, newCurTo, newAccountBallance);
            accountBallanceService.saveAccountBallance(newAccountBallance);
            //zapis nowej tranzakcji
            Transaction newTransaction = new Transaction(
                    transaction.getUser(),
                    LocalDateTime.now(),
                    BuyOrSell.BUY,
                    curFrom,
                    transaction.getQtyFrom(),
                    curTo,
                    sum,
                    buy
            );
            transactionRepository.save(newTransaction);
        } else {
            throw new WrongValidationException();
        }
    }

    public void createSellTransaction(final Long userId, final TransactionDto transactionDto)
            throws UserNotFoundException, AccountBallanceNotFoundException, WrongValidationException, CurrencyNotFoundException {
        Transaction transaction = transactionMapper.mapToTransaction(userId, transactionDto);
        //pobranie kursu waluty którą chcemy sprzedać
        String curFrom = transaction.getCurFrom();
        String curTo = transaction.getCurTo();
        double sell = getSellForAnyCurrencyFromRepository(curFrom); //getSellForAnyCurrency(curFrom);
        double sellQty = transaction.getQtyFrom();
        //wyliczenie sumy
        double sum = sellQty * sell;
        if (accountBallanceValidationForSell(userId,sellQty,curFrom)) {
            //wyliczenie zmian stanu konta
            double oldCurFrom = accountBallanceService.getCurrencyAccountBallance(userId,curFrom);
            double newCurFrom = oldCurFrom - sellQty;
            double oldCurTo = accountBallanceService.getCurrencyAccountBallance(userId,curTo);
            double newCurTo = oldCurTo + sum;
            //zaktualizowanie stanu konta - zapis nowego stanu konta
            AccountBallance newAccountBallance = changeToNewAccountBallance(userId);
            updateCurBallance(curFrom, newCurFrom, newAccountBallance);
            updateCurBallance(curTo, newCurTo, newAccountBallance);
            accountBallanceService.saveAccountBallance(newAccountBallance);
            //zapis nowej tranzakcji
            Transaction newTransaction = new Transaction(
                    transaction.getUser(),
                    LocalDateTime.now(),
                    BuyOrSell.SELL,
                    curFrom,
                    sellQty,
                    curTo,
                    sum,
                    sell
            );
            transactionRepository.save(newTransaction);
        } else {
            throw new WrongValidationException();
        }
    }

    private AccountBallance changeToNewAccountBallance(final Long userId) throws UserNotFoundException, AccountBallanceNotFoundException {
        AccountBallance accountBallance = accountBallanceService.getAccountByUser(userId);
        return new AccountBallance(
                accountBallance.getUser(),
                accountBallance.getPln(),
                accountBallance.getUsd(),
                accountBallance.getEur(),
                accountBallance.getChf(),
                accountBallance.getBtc()
        );
    }

    public List<TransactionDto> getTransactionByUser(final Long userId) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Transaction> transactions = transactionRepository.findTransactionByUserId(user.getId());
        return transactionMapper.mapToListTransactionDto(transactions);
    }

    public double getBuyForAnyCurrencyFromRepository(String currency) throws CurrencyNotFoundException {
        if (currency.equals(Currencies.BTC)) {
            return cryptoService.getCryptoBuyFromRepository(currency);
        } else {
            return currencyService.getCurrencyBuyFromRepository(currency);
        }
    }

    public double getSellForAnyCurrencyFromRepository(String currency) throws CurrencyNotFoundException {
        if (currency.equals(Currencies.BTC)) {
            return cryptoService.getCryptoSellFromRepository(currency);
        } else {
            return currencyService.getCurrencySellFromRepository(currency);
        }
    }

    private boolean accountBallanceValidationForBuy(final Long userId, double sum, String curTo)
            throws UserNotFoundException, AccountBallanceNotFoundException {
        double curBallance = accountBallanceService.getCurrencyAccountBallance(userId,curTo);
        return curBallance >= sum;
    }

    private boolean accountBallanceValidationForSell(final Long userId, double sellQty, String curFrom)
            throws UserNotFoundException, AccountBallanceNotFoundException {
        double curBallance = accountBallanceService.getCurrencyAccountBallance(userId,curFrom);
        return curBallance >= sellQty;
    }

    public void updateCurBallance(String cur, double newCur, AccountBallance newAccountBallance) {
        if (cur.equals(Currencies.USD)) {
            newAccountBallance.setUsd(newCur);
        } else if (cur.equals(Currencies.EUR)) {
            newAccountBallance.setEur(newCur);
        } else if (cur.equals(Currencies.CHF)) {
            newAccountBallance.setChf(newCur);
        } else if (cur.equals(Currencies.BTC)) {
            newAccountBallance.setBtc(newCur);
        } else if (cur.equals(Currencies.PLN)) {
            newAccountBallance.setPln(newCur);
        }
    }
}
