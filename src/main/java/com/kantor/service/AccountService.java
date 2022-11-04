package com.kantor.service;

import com.kantor.domain.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    public AccountDto createAccount(final Long userId) {
        return new AccountDto();
    }

    public AccountDto updateAccountByDeposit(final Long userId) {
        return new AccountDto();
    }

    public AccountDto updateAccountByWithdrawal(final Long userId) {
        return new AccountDto();
    }

    public AccountDto getAccountByUser(final Long userId) {
        return new AccountDto();
    }

    public List<AccountDto> getAccountHistory(final Long userId) {
        return new ArrayList<>();
    }
}
