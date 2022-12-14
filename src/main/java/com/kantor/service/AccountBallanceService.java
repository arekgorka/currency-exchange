package com.kantor.service;

import com.kantor.domain.AccountBallance;
import com.kantor.domain.Currencies;
import com.kantor.domain.User;
import com.kantor.domain.dto.AccountBallanceDto;
import com.kantor.exception.AccountBallanceNotFoundException;
import com.kantor.exception.AccountWithdrawalException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.mapper.AccountBallanceMapper;
import com.kantor.repository.AccountBallanceRepository;
import com.kantor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBallanceService {

    private final AccountBallanceRepository accountBallanceRepository;
    private final AccountBallanceMapper accountBallanceMapper;
    private final UserRepository userRepository;

    public void createAccount(final Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        AccountBallance accountBallance = new AccountBallance(user);
        accountBallanceRepository.save(accountBallance);
    }

    public void saveAccountBallance(final AccountBallance accountBallance) {
        accountBallanceRepository.save(accountBallance);
    }

    public AccountBallanceDto updateAccountByDeposit(final Long userId, final double amount)
            throws UserNotFoundException, AccountBallanceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<AccountBallance> accountBallances = accountBallanceRepository.findAccountBallanceByUserId(user.getId());
        AccountBallance accountBallance = accountBallances.stream()
                .max(Comparator.comparing(AccountBallance::getId))
                .orElseThrow(AccountBallanceNotFoundException::new);
        double sumPln = accountBallance.getPln() + amount;
        AccountBallance updatedAccountBallance = new AccountBallance(
                user,
                sumPln,
                accountBallance.getUsd(),
                accountBallance.getEur(),
                accountBallance.getChf(),
                accountBallance.getBtc()
        );
        accountBallanceRepository.save(updatedAccountBallance);
        return accountBallanceMapper.mapToAccountBallanceDto(updatedAccountBallance);
    }

    public AccountBallanceDto updateAccountByWithdrawal(final Long userId, final double amount)
            throws UserNotFoundException, AccountBallanceNotFoundException, AccountWithdrawalException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<AccountBallance> accountBallances = accountBallanceRepository.findAccountBallanceByUserId(user.getId());
        AccountBallance accountBallance = accountBallances.stream()
                .max(Comparator.comparing(AccountBallance::getId))
                .orElseThrow(AccountBallanceNotFoundException::new);
        if (accountBallance.getPln() >= amount) {
            double subPln = accountBallance.getPln() - amount;
            AccountBallance updatedAccountBallance = new AccountBallance(
                    user,
                    subPln,
                    accountBallance.getUsd(),
                    accountBallance.getEur(),
                    accountBallance.getChf(),
                    accountBallance.getBtc()
            );
            accountBallanceRepository.save(updatedAccountBallance);
            return accountBallanceMapper.mapToAccountBallanceDto(updatedAccountBallance);
        } else {
            throw new AccountWithdrawalException();
        }
    }

    public AccountBallanceDto getAccountDtoByUser(final Long userId) throws UserNotFoundException, AccountBallanceNotFoundException {
        AccountBallance accountBallance = getAccountByUser(userId);
        return accountBallanceMapper.mapToAccountBallanceDto(accountBallance);
    }

    public AccountBallance getAccountByUser(final Long userId) throws UserNotFoundException, AccountBallanceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<AccountBallance> accountBallances = accountBallanceRepository.findAccountBallanceByUserId(user.getId());
        return accountBallances.stream()
                .max(Comparator.comparing(AccountBallance::getId))
                .orElseThrow(AccountBallanceNotFoundException::new);
    }

    public List<AccountBallanceDto> getAccountHistory(final Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<AccountBallance> accountBallances = accountBallanceRepository.findAccountBallanceByUserId(user.getId());
        return accountBallanceMapper.mapToListAccountBallanceDto(accountBallances);
    }

    public double getCurrencyAccountBallance(Long userId, String currency) throws UserNotFoundException, AccountBallanceNotFoundException {
        AccountBallance accountBallance = getAccountByUser(userId);
        if (currency.equals(Currencies.USD)) {
            return accountBallance.getUsd();
        } else if (currency.equals(Currencies.EUR)) {
            return accountBallance.getEur();
        } else if (currency.equals(Currencies.CHF)) {
            return accountBallance.getChf();
        } else if (currency.equals(Currencies.BTC)) {
            return accountBallance.getBtc();
        } else {
            return accountBallance.getPln();
        }
    }
}
