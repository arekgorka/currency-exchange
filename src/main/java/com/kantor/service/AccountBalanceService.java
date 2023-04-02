package com.kantor.service;

import com.kantor.domain.AccountBalance;
import com.kantor.domain.Currencies;
import com.kantor.domain.User;
import com.kantor.domain.dto.AccountBalanceDto;
import com.kantor.exception.AccountBalanceNotFoundException;
import com.kantor.exception.AccountWithdrawalException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.mapper.AccountBalanceMapper;
import com.kantor.repository.AccountBalanceRepository;
import com.kantor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBalanceService {

    private final AccountBalanceRepository accountBalanceRepository;
    private final AccountBalanceMapper accountBalanceMapper;
    private final UserRepository userRepository;

    public void createAccount(final Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        AccountBalance accountBalance = new AccountBalance(user);
        accountBalanceRepository.save(accountBalance);
    }

    public void saveAccountBallance(final AccountBalance accountBalance) {
        accountBalanceRepository.save(accountBalance);
    }

    public AccountBalanceDto updateAccountByDeposit(final Long userId, final double amount)
            throws UserNotFoundException, AccountBalanceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<AccountBalance> accountBalances = accountBalanceRepository.findAccountBalanceByUserId(user.getId());
        AccountBalance accountBalance = accountBalances.stream()
                .max(Comparator.comparing(AccountBalance::getId))
                .orElseThrow(AccountBalanceNotFoundException::new);
        double sumPln = accountBalance.getPln() + amount;
        AccountBalance updatedAccountBalance = new AccountBalance(
                user,
                sumPln,
                accountBalance.getUsd(),
                accountBalance.getEur(),
                accountBalance.getChf(),
                accountBalance.getBtc()
        );
        accountBalanceRepository.save(updatedAccountBalance);
        return accountBalanceMapper.mapToAccountBalanceDto(updatedAccountBalance);
    }

    public AccountBalanceDto updateAccountByWithdrawal(final Long userId, final double amount)
            throws UserNotFoundException, AccountBalanceNotFoundException, AccountWithdrawalException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<AccountBalance> accountBalances = accountBalanceRepository.findAccountBalanceByUserId(user.getId());
        AccountBalance accountBalance = accountBalances.stream()
                .max(Comparator.comparing(AccountBalance::getId))
                .orElseThrow(AccountBalanceNotFoundException::new);
        if (accountBalance.getPln() >= amount) {
            double subPln = accountBalance.getPln() - amount;
            AccountBalance updatedAccountBalance = new AccountBalance(
                    user,
                    subPln,
                    accountBalance.getUsd(),
                    accountBalance.getEur(),
                    accountBalance.getChf(),
                    accountBalance.getBtc()
            );
            accountBalanceRepository.save(updatedAccountBalance);
            return accountBalanceMapper.mapToAccountBalanceDto(updatedAccountBalance);
        } else {
            throw new AccountWithdrawalException();
        }
    }

    public AccountBalanceDto getAccountDtoByUser(final Long userId) throws UserNotFoundException, AccountBalanceNotFoundException {
        AccountBalance accountBalance = getAccountByUser(userId);
        return accountBalanceMapper.mapToAccountBalanceDto(accountBalance);
    }

    public AccountBalance getAccountByUser(final Long userId) throws UserNotFoundException, AccountBalanceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<AccountBalance> accountBalances = accountBalanceRepository.findAccountBalanceByUserId(user.getId());
        return accountBalances.stream()
                .max(Comparator.comparing(AccountBalance::getId))
                .orElseThrow(AccountBalanceNotFoundException::new);
    }

    public List<AccountBalanceDto> getAccountHistory(final Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<AccountBalance> accountBalances = accountBalanceRepository.findAccountBalanceByUserId(user.getId());
        return accountBalanceMapper.mapToListAccountBalanceDto(accountBalances);
    }

    public double getCurrencyAccountBalance(Long userId, String currency) throws UserNotFoundException, AccountBalanceNotFoundException {
        AccountBalance accountBalance = getAccountByUser(userId);
        switch (currency) {
            case Currencies.USD:
                return accountBalance.getUsd();
            case Currencies.EUR:
                return accountBalance.getEur();
            case Currencies.CHF:
                return accountBalance.getChf();
            case Currencies.BTC:
                return accountBalance.getBtc();
            default:
                return accountBalance.getPln();
        }
    }
}
