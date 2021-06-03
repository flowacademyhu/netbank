package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.dto.AddMoneyDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import hu.flowacademy.netbank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account save(Account account) {
        // TODO validate
        return accountRepository.save(account);
    }

    public void addMoney(String id, AddMoneyDTO addMoneyDTO) {
        findOne(id).ifPresent(account ->
                    // TODO validate currency
                    accountRepository.save(
                            account.toBuilder()
                                    .amount(account.getAmount().add(addMoneyDTO.getAmount()))
                                    .build()
                    )
                );
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

}
