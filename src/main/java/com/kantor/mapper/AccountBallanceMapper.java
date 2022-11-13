package com.kantor.mapper;

import com.kantor.domain.AccountBallance;
import com.kantor.domain.dto.AccountBallanceDto;
import com.kantor.exception.AccountBallanceNotFoundException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.service.AccountBallanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountBallanceMapper {

    private final AccountBallanceService accountBallanceService;

    public AccountBallanceDto mapToAccountBallanceDto(final AccountBallance accountBallance) {
        return new AccountBallanceDto(
                accountBallance.getPln(),
                accountBallance.getUsd(),
                accountBallance.getEur(),
                accountBallance.getChf(),
                accountBallance.getBtc()
        );
    }

    public AccountBallanceDto mapToAccountBallanceDtoHistory(final  AccountBallance accountBallance) {
        return new AccountBallanceDto(
                accountBallance.getDatetime(),
                accountBallance.getPln(),
                accountBallance.getUsd(),
                accountBallance.getEur(),
                accountBallance.getChf(),
                accountBallance.getBtc()
        );
    }

    public List<AccountBallanceDto> mapToListAccountBallanceDto(final List<AccountBallance> accountBallanceList) {
        return accountBallanceList.stream()
                .map(this::mapToAccountBallanceDtoHistory)
                .collect(Collectors.toList());
    }

    public AccountBallance mapToNewAccountBallance(final Long userId) throws UserNotFoundException, AccountBallanceNotFoundException {
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
}
