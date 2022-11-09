package com.kantor.mapper;

import com.kantor.domain.AccountBallance;
import com.kantor.domain.dto.AccountBallanceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountBallanceMapper {

    public AccountBallanceDto mapToAccountBallanceDto(final AccountBallance accountBallance) {
        return new AccountBallanceDto(
                accountBallance.getId(),
                accountBallance.getPln(),
                accountBallance.getUsd(),
                accountBallance.getEur(),
                accountBallance.getChf(),
                accountBallance.getBtc()
        );
    }

    public List<AccountBallanceDto> mapToListAccountBallanceDto(final List<AccountBallance> accountBallanceList) {
        return accountBallanceList.stream()
                .map(this::mapToAccountBallanceDto)
                .collect(Collectors.toList());
    }
}
