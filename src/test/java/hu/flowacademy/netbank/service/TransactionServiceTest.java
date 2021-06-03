package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.dto.TransactionCreateDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import hu.flowacademy.netbank.model.Transaction;
import hu.flowacademy.netbank.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @Test
    public void testTransactionSaveSuccess() {
        String senderId = UUID.randomUUID().toString();
        String receiverId = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.valueOf(200);

        when(accountService.findOne(senderId)).thenReturn(
                Optional.of(
                        Account.builder()
                                .id(senderId)
                                .currency(Currency.HUF)
                                .amount(BigDecimal.valueOf(1000))
                                .build()
                )
        );
        when(accountService.findOne(receiverId)).thenReturn(
                Optional.of(
                        Account.builder()
                                .id(senderId)
                                .currency(Currency.HUF)
                                .amount(BigDecimal.valueOf(1000))
                                .build()
                )
        );

        when(transactionRepository.save(any())).thenReturn(
                Transaction.builder()
                        .amount(amount)
                        .build());

        when(accountService.update(any())).thenReturn(Account.builder().build());

        transactionService.save(TransactionCreateDTO.builder()
                .senderAccountId(senderId)
                .receiverAccountId(receiverId)
                .amount(amount)
                .build());

        verify(accountService).findOne(senderId);
        verify(accountService).findOne(receiverId);
        verify(transactionRepository).save(any());
        verify(accountService, times(2)).update(any());
    }

}