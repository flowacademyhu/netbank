package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.bootstrap.InitDataLoader;
import hu.flowacademy.netbank.dto.LoanRequestDTO;
import hu.flowacademy.netbank.dto.TransactionCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private final TransactionService transactionService;

    public void request(LoanRequestDTO loanRequest) {
        transactionService.save(
                TransactionCreateDTO.builder()
                        .amount(loanRequest.getAmount())
                        .senderAccountId(InitDataLoader.BANK_ACCOUNT_ID)
                        .receiverAccountId(loanRequest.getAccountId())
                        .build()
        );
    }
}
