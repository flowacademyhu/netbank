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

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InitDataLoader implements CommandLineRunner {

    public static final String BANK_USER_ID = "272ddd51-ab23-4584-93eb-152bb7baae53";
    public static final String BANK_ACCOUNT_ID = "51f79ede-00b4-4c22-be73-11e871ff492b";

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Value("${app.rootPassword}")
    private String rootPassword;

    @Override
    public void run(String... args) throws Exception {
        User owner = userRepository.findById(BANK_USER_ID)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .id(BANK_USER_ID)
                                .fullName("bank")
                                .password(rootPassword)
                                .role(Role.ADMIN)
                                .email("bank@example.com")
                                .build()
                ));
        accountRepository.findById(BANK_ACCOUNT_ID)
                .orElseGet(() -> accountRepository.save(
                        Account.builder()
                                .id(BANK_ACCOUNT_ID)
                                .amount(BigDecimal.valueOf(Long.MAX_VALUE))
                                .owner(owner)
                                .currency(Currency.HUF)
                                .accountNumber(UUID.randomUUID().toString())
                                .build()
                ));
    }
}
