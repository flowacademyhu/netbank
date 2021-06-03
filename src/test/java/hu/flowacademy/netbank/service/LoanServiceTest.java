package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.dto.LoanRequestDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import hu.flowacademy.netbank.model.Transaction;
import hu.flowacademy.netbank.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private UserService userService;
    @Mock
    private AccountService accountService;

    @Test
    public void testGetALoanSuccess() {
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(userService.getBank()).thenReturn(
                Optional.of(User.builder().id(userId).build())
        );
        when(accountService.findByOwnerAndCurrency(userId, Currency.HUF))
                .thenReturn(Optional.of(
                        Account.builder().id(accountId).currency(Currency.HUF).build()
                ));
        when(transactionService.save(any())).thenReturn(Transaction.builder().build());

        loanService.request(Currency.HUF, LoanRequestDTO.builder()
                .amount(BigDecimal.valueOf(1000))
                .accountId(accountId)
                .build());

        verify(userService).getBank();
        verify(accountService).findByOwnerAndCurrency(userId, Currency.HUF);
        verify(transactionService).save(any());
    }
}