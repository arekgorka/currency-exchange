package com.kantor.service;

import com.kantor.domain.*;
import com.kantor.domain.dto.TransactionDto;
import com.kantor.exception.AccountBalanceNotFoundException;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.exception.WrongValidationException;
import com.kantor.mapper.TransactionMapper;
import com.kantor.repository.TransactionRepository;
import com.kantor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountBalanceService accountBalanceService;
    private final CurrencyService currencyService;
    private final CryptoService cryptoService;
    private final UserRepository userRepository;

    public void createBuyTransaction(final Long userId, final TransactionDto transactionDto)
            throws UserNotFoundException, AccountBalanceNotFoundException, WrongValidationException, CurrencyNotFoundException {
        Transaction transaction = transactionMapper.mapToTransaction(userId, transactionDto);
        //pobranie kursu waluty którą chcemy kupić
        String curFrom = transaction.getCurFrom();
        String curTo = transaction.getCurTo();
        double buy = getBuyForAnyCurrencyFromRepository(curFrom);
        double buyQty = transaction.getQtyFrom();
        //wyliczenie sumy
        double sum = buyQty * buy;
        //pobranie stanu konta - sprawdzenie czy znajduje się tyle na koncie
        if (accountBalanceValidationForBuy(userId,sum,curTo)) {
            //wyliczenie zmian stanu konta
            double oldCurFrom = accountBalanceService.getCurrencyAccountBalance(userId,curFrom);
            double newCurFrom = oldCurFrom + buyQty;
            double oldCurTo = accountBalanceService.getCurrencyAccountBalance(userId,curTo);
            double newCurTo = oldCurTo - sum / getSellForAnyCurrencyFromRepository(curTo);
            //zaktualizowanie stanu konta - zapis nowego stanu konta
            AccountBalance newAccountBalance = changeToNewAccountBalance(userId);
            updateCurBalance(curFrom, newCurFrom, newAccountBalance);
            updateCurBalance(curTo, newCurTo, newAccountBalance);
            accountBalanceService.saveAccountBallance(newAccountBalance);
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
            throws UserNotFoundException, AccountBalanceNotFoundException, WrongValidationException, CurrencyNotFoundException {
        Transaction transaction = transactionMapper.mapToTransaction(userId, transactionDto);
        //pobranie kursu waluty którą chcemy sprzedać
        String curFrom = transaction.getCurFrom();
        String curTo = transaction.getCurTo();
        double sell = getSellForAnyCurrencyFromRepository(curFrom);
        double sellQty = transaction.getQtyFrom();
        //wyliczenie sumy
        double sum = sellQty * sell;
        if (accountBalanceValidationForSell(userId,sellQty,curFrom)) {
            //wyliczenie zmian stanu konta
            double oldCurFrom = accountBalanceService.getCurrencyAccountBalance(userId,curFrom);
            double newCurFrom = oldCurFrom - sellQty;
            double oldCurTo = accountBalanceService.getCurrencyAccountBalance(userId,curTo);
            double newCurTo = oldCurTo + sum / getBuyForAnyCurrencyFromRepository(curTo);
            //zaktualizowanie stanu konta - zapis nowego stanu konta
            AccountBalance newAccountBalance = changeToNewAccountBalance(userId);
            updateCurBalance(curFrom, newCurFrom, newAccountBalance);
            updateCurBalance(curTo, newCurTo, newAccountBalance);
            accountBalanceService.saveAccountBallance(newAccountBalance);
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

    private AccountBalance changeToNewAccountBalance(final Long userId) throws UserNotFoundException, AccountBalanceNotFoundException {
        AccountBalance accountBalance = accountBalanceService.getAccountByUser(userId);
        return new AccountBalance(
                accountBalance.getUser(),
                accountBalance.getPln(),
                accountBalance.getUsd(),
                accountBalance.getEur(),
                accountBalance.getChf(),
                accountBalance.getBtc()
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

    public boolean accountBalanceValidationForBuy(final Long userId, double sum, String curTo)
            throws UserNotFoundException, AccountBalanceNotFoundException, CurrencyNotFoundException {
        double curBalance = accountBalanceService.getCurrencyAccountBalance(userId,curTo);
        return curBalance * getSellForAnyCurrencyFromRepository(curTo) >= sum;
    }

    //sellQty = 1000
    //curBalance = 3000
    public boolean accountBalanceValidationForSell(final Long userId, double sellQty, String curFrom)
            throws UserNotFoundException, AccountBalanceNotFoundException {
        double curBalance = accountBalanceService.getCurrencyAccountBalance(userId,curFrom);
        return curBalance >= sellQty;
    }

    public void updateCurBalance(String cur, double newCur, AccountBalance newAccountBalance) {
        switch (cur) {
            case Currencies.USD:
                newAccountBalance.setUsd(newCur);
                break;
            case Currencies.EUR:
                newAccountBalance.setEur(newCur);
                break;
            case Currencies.CHF:
                newAccountBalance.setChf(newCur);
                break;
            case Currencies.BTC:
                newAccountBalance.setBtc(newCur);
                break;
            case Currencies.PLN:
                newAccountBalance.setPln(newCur);
                break;
        }
    }
}
