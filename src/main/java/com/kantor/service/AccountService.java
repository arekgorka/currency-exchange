package com.kantor.service;

import com.kantor.domain.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
