package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.dto.AddMoneyDTO;
import hu.flowacademy.netbank.exception.ValidationException;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import hu.flowacademy.netbank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account save(Account account) {
        validate(account);
        return accountRepository.save(account.toBuilder()
                .accountNumber(UUID.randomUUID().toString())
                .build());
    }

    public void addMoney(String id, AddMoneyDTO addMoneyDTO) {
        findOne(id)
                .filter(account -> account.getCurrency().equals(addMoneyDTO.getCurrency()))
                .ifPresentOrElse(account -> accountRepository.save(
                        account.toBuilder()
                                .amount(account.getAmount().add(addMoneyDTO.getAmount()))
                                .build()
                        ), () -> {
                            throw new ValidationException("unable to add money to the account");
                        });
    }

    public Optional<Account> findOne(String id) {
        return accountRepository.findById(id);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    Account update(Account account) {
        return accountRepository.save(account);
    }

    Optional<Account> findByOwnerAndCurrency(String ownerId, Currency currency) {
        return accountRepository.findFirstByOwner_IdAndCurrency(ownerId, currency);
    }

    private void validate(Account account) {
        if (account.getCurrency() == null) {
            throw new ValidationException("missing currency");
        }
        if (account.getAmount() == null) {
            throw new ValidationException("missing amount");
        }
        if (account.getOwner() == null) {
            throw new ValidationException("missing account owner");
        }
    }

}
