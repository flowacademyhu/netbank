package hu.flowacademy.netbank.bootstrap;

import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import hu.flowacademy.netbank.model.Role;
import hu.flowacademy.netbank.model.User;
import hu.flowacademy.netbank.repository.AccountRepository;
import hu.flowacademy.netbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InitDataLoader implements CommandLineRunner {

    public static final String BANK_USER_EMAIL = "bank@example.com";

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Value("${app.rootPassword}")
    private String rootPassword;

    @Override
    public void run(String... args) throws Exception {
        User owner = userRepository.findFirstByEmail(BANK_USER_EMAIL)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .fullName("bank")
                                .password(rootPassword)
                                .role(Role.ADMIN)
                                .email(BANK_USER_EMAIL)
                                .build()
                ));
        Stream.of(Currency.values())
                .forEach(currency ->
                        accountRepository.findFirstByOwner_IdAndCurrency(owner.getId(), currency)
                                .orElseGet(() -> accountRepository.save(
                                        Account.builder()
                                                .amount(BigDecimal.valueOf(Integer.MAX_VALUE))
                                                .owner(owner)
                                                .currency(currency)
                                                .accountNumber(UUID.randomUUID().toString())
                                                .build()
                                ))
                );
    }
}
