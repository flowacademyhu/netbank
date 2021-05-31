package hu.flowacademy.netbank.controller;

import hu.flowacademy.netbank.dto.AddMoneyDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Account account) {
        log.debug("Creating account with params: {}", account);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMoney(@PathVariable String id,
                         @RequestBody AddMoneyDTO addMoneyDTO) {
        log.debug("Add money to account with params: {}", addMoneyDTO);
    }

    @GetMapping("/{id}")
    public Optional<Account> findOne(@PathVariable String id) {
        log.debug("Get account with id: {}", id);
        return Optional.of(
                Account.builder()
                        .id(id)
                        .accountNumber(UUID.randomUUID().toString())
                        .amount(BigDecimal.valueOf(9999))
                        .currency(Currency.EUR)
                        .build()
        );
    }

    @GetMapping
    public List<Account> findAll() {
        log.debug("Get all accounts");
        return List.of(
                Account.builder()
                        .id(UUID.randomUUID().toString())
                        .accountNumber(UUID.randomUUID().toString())
                        .amount(BigDecimal.valueOf(9999))
                        .currency(Currency.EUR)
                        .build(),
                Account.builder()
                        .id(UUID.randomUUID().toString())
                        .accountNumber(UUID.randomUUID().toString())
                        .amount(BigDecimal.valueOf(9999888))
                        .currency(Currency.HUF)
                        .build()
        );
    }

}
