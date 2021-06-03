package hu.flowacademy.netbank.controller;

import hu.flowacademy.netbank.dto.TransactionCreateDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Transaction;
import hu.flowacademy.netbank.service.TransactionService;
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

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody TransactionCreateDTO transaction) {
        log.debug("Create transaction with params: {}", transaction);
        transactionService.save(transaction);
    }

    @PostMapping("/{id}/revert")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revert(@PathVariable String id) {
        log.debug("Reverting transaction with id: {}", id);
        transactionService.revert(id);
    }

    @GetMapping
    public List<Transaction> findAll() {
        log.debug("Getting all the transactions");
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Transaction> findOne(@PathVariable String id) {
        log.debug("Getting all the transactions");
        return transactionService.findOne(id);
    }

}
