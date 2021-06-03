package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.dto.AddMoneyDTO;
import hu.flowacademy.netbank.exception.ValidationException;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import hu.flowacademy.netbank.model.User;
import hu.flowacademy.netbank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    @Test
    public void testAccountSaveSuccess() {
        when(accountRepository.save(any())).thenReturn(Account.builder().build());

        accountService.save(Account.builder()
                .amount(BigDecimal.ZERO)
                .currency(Currency.HUF)
                .owner(User.builder().build())
                .build());

        verify(accountRepository).save(any());
    }

    @Test
    public void testAccountSaveFails() {
        assertThrows(ValidationException.class, () -> accountService.save(givenAccountWihoutCurrency(Account.builder())));
        assertThrows(ValidationException.class, () -> accountService.save(givenAccountWithoutAmount()));
        assertThrows(ValidationException.class, () -> accountService.save(givenAccountWithoutOwner()));
    }

    @Test
    public void testAccountAddMoneySuccess() {
        String accountId = UUID.randomUUID().toString();
        when(accountRepository.findById(accountId)).thenReturn(
                Optional.of(Account.builder()
                        .amount(BigDecimal.ZERO)
                        .currency(Currency.HUF)
                        .owner(User.builder().build())
                        .build())
        );

        accountService.addMoney(accountId, AddMoneyDTO
                .builder()
                .amount(BigDecimal.valueOf(100))
                .currency(Currency.HUF)
                .build());

        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(any());
    }

    @Test
    public void testAccountAddMoneyFail() {
        String accountId = UUID.randomUUID().toString();
        when(accountRepository.findById(accountId)).thenReturn(
                Optional.of(Account.builder()
                        .amount(BigDecimal.ZERO)
                        .currency(Currency.HUF)
                        .owner(User.builder().build())
                        .build())
        );

        assertThrows(ValidationException.class, () ->
                accountService.addMoney(accountId, AddMoneyDTO
                        .builder()
                        .amount(BigDecimal.valueOf(100))
                        .currency(Currency.USD)
                        .build())
        );

        verify(accountRepository).findById(accountId);
        verifyNoMoreInteractions(accountRepository);
    }

    private Account givenAccountWithoutOwner() {
        return Account.builder()
                .currency(Currency.HUF)
                .amount(BigDecimal.ZERO).build();
    }

    private Account givenAccountWithoutAmount() {
        return Account.builder()
                .currency(Currency.HUF).build();
    }

    private Account givenAccountWihoutCurrency(Account.AccountBuilder builder) {
        return builder.build();
    }

}