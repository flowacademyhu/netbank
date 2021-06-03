package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.dto.TransactionCreateDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Transaction;
import hu.flowacademy.netbank.model.TransactionStatus;
import hu.flowacademy.netbank.repository.TransactionRepository;
import hu.flowacademy.netbank.service.exchange.ExchangeStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final ExchangeStrategy exchangeStrategy;

    public Transaction save(TransactionCreateDTO transaction) {
        Account sender = accountService.findOne(transaction.getSenderAccountId()).orElseThrow();
        Account receiver = accountService.findOne(transaction.getReceiverAccountId()).orElseThrow();

        Transaction savedTransaction = transactionRepository.save(
                Transaction.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .amount(transaction.getAmount())
                        .createdAt(LocalDateTime.now())
                        .status(TransactionStatus.SUCCESS)
                        .currentExchangeRate(calculateExchangeRate(sender, receiver))
                        .build()
        );

        updateAccount(savedTransaction, sender, receiver);

        return savedTransaction;
    }

    public void revert(String id) {
        Transaction transaction = findOne(id).orElseThrow();
        transactionRepository.save(
                Transaction.builder()
                        .amount(transaction.getAmount())
                        .sender(transaction.getReceiver())
                        .receiver(transaction.getSender())
                        .currentExchangeRate(calculateExchangeRate(transaction.getReceiver(), transaction.getSender()))
                        .status(TransactionStatus.REVERTED)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        updateAccount(transaction, transaction.getReceiver(), transaction.getSender());
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findOne(String id) {
        return transactionRepository.findById(id);
    }

    private BigDecimal calculateExchangeRate(Account sender, Account receiver) {
        if (sender.getCurrency().equals(receiver.getCurrency())) {
            return BigDecimal.ONE;
        }

        return exchangeStrategy.exchange(sender.getCurrency(), receiver.getCurrency());
    }

    private void updateAccount(Transaction transaction, Account sender, Account receiver) {
        accountService.update(
                sender.toBuilder()
                        .amount(sender.getAmount().subtract(transaction.getAmount()))
                        .build()
        );

        accountService.update(
                receiver.toBuilder()
                        .amount(receiver.getAmount().add(
                                transaction.getAmount().multiply(calculateExchangeRate(sender, receiver)
                                ))
                        )
                        .build()
        );
    }
}
