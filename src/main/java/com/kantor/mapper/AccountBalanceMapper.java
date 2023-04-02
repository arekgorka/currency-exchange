package com.kantor.mapper;

import com.kantor.domain.AccountBalance;
import com.kantor.domain.dto.AccountBalanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountBalanceMapper {

    public AccountBalanceDto mapToAccountBalanceDto(final AccountBalance accountBalance) {
        return new AccountBalanceDto(
                accountBalance.getPln(),
                accountBalance.getUsd(),
                accountBalance.getEur(),
                accountBalance.getChf(),
                accountBalance.getBtc()
        );
    }

    public AccountBalanceDto mapToAccountBalanceDtoHistory(final AccountBalance accountBalance) {
        return new AccountBalanceDto(
                accountBalance.getDatetime(),
                accountBalance.getPln(),
                accountBalance.getUsd(),
                accountBalance.getEur(),
                accountBalance.getChf(),
                accountBalance.getBtc()
        );
    }

    public List<AccountBalanceDto> mapToListAccountBalanceDto(final List<AccountBalance> accountBalanceList) {
        return accountBalanceList.stream()
                .map(this::mapToAccountBalanceDtoHistory)
                .collect(Collectors.toList());
    }

}
