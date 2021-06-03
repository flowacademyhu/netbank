package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.dto.LoanRequestDTO;
import hu.flowacademy.netbank.dto.TransactionCreateDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private final TransactionService transactionService;
    private final UserService userService;
    private final AccountService accountService;

    public void request(Currency currency, LoanRequestDTO loanRequest) {
        Account bankAccount = userService.getBank()
                .flatMap(user ->
                        accountService.findByOwnerAndCurrency(user.getId(), currency)).orElseThrow();
        transactionService.save(
                TransactionCreateDTO.builder()
                        .amount(loanRequest.getAmount())
                        .senderAccountId(bankAccount.getId())
                        .receiverAccountId(loanRequest.getAccountId())
                        .build()
        );
    }
}
