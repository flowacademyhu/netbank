package hu.flowacademy.netbank.controller;

import hu.flowacademy.netbank.dto.LoanRequestDTO;
import hu.flowacademy.netbank.model.Currency;
import hu.flowacademy.netbank.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PatchMapping("/{currency}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestLoan(@PathVariable Currency currency,
                            @RequestBody LoanRequestDTO loanRequest) {
        log.debug("Loan requested with currency and params: {} {}", currency, loanRequest);
        loanService.request(currency, loanRequest);
    }

}
