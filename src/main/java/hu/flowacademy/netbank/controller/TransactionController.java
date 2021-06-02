package hu.flowacademy.netbank.controller;

import hu.flowacademy.netbank.dto.TransactionCreateDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody TransactionCreateDTO transaction) {
        log.debug("Create transaction with params: {}", transaction);
    }

    @PostMapping("/{id}/revert")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revert(@PathVariable String id) {
        log.debug("Reverting transaction with id: {}", id);
    }

    @GetMapping
    public List<Transaction> findAll() {
        log.debug("Getting all the transactions");
        return List.of(
                Transaction.builder()
                        .id(UUID.randomUUID().toString())
                        .amount(BigDecimal.valueOf(200))
                        .sender(Account.builder().id(UUID.randomUUID().toString()).build())
                        .receiver(Account.builder().id(UUID.randomUUID().toString()).build())
                        .currentExchangeRate(BigDecimal.valueOf(1))
                        .createdAt(LocalDateTime.now())
                        .build(),
                Transaction.builder()
                        .id(UUID.randomUUID().toString())
                        .amount(BigDecimal.valueOf(300))
                        .sender(Account.builder().id(UUID.randomUUID().toString()).build())
                        .receiver(Account.builder().id(UUID.randomUUID().toString()).build())
                        .currentExchangeRate(BigDecimal.valueOf(290.13))
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public Optional<Transaction> findOne(@PathVariable String id) {
        log.debug("Getting all the transactions");
        return Optional.of(
                Transaction.builder()
                        .id(id)
                        .amount(BigDecimal.valueOf(200))
                        .sender(Account.builder().id(UUID.randomUUID().toString()).build())
                        .receiver(Account.builder().id(UUID.randomUUID().toString()).build())
                        .currentExchangeRate(BigDecimal.valueOf(1))
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

}
