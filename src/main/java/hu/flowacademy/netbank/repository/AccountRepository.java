package hu.flowacademy.netbank.repository;

import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findFirstByOwner_IdAndCurrency(String id, Currency currency);

}
