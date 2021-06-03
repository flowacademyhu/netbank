package hu.flowacademy.netbank.controller;

import hu.flowacademy.netbank.dto.LoanRequestDTO;
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

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestLoan(@RequestBody LoanRequestDTO loanRequest) {
        log.debug("Loan requested with params: {}", loanRequest);
        loanService.request(loanRequest);
    }

}
