package com.calculator.taeg.controller;

import com.calculator.taeg.dto.LoanDetails;
import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.RestResponse;
import com.calculator.taeg.dto.ValidationError;
import com.calculator.taeg.service.LoanService;
import com.calculator.taeg.service.LoanRequestValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/taeg")
public class TAEGRestController {

    private final LoanService loanService;
    private final LoanRequestValidationService validationService;

    @PostMapping("/calculate")
    public RestResponse<?> calculateTAEG(@RequestBody LoanRequest loanRequest) {
        List<ValidationError> errors = validationService.validateLoanRequest(loanRequest);

        if (!errors.isEmpty()) {
            return new RestResponse<>(400, "Validation Failed", errors);
        }

        LoanDetails response = loanService.calculateLoanDetails(loanRequest);
        return new RestResponse<>(200, "Successful", response);
    }

    @GetMapping("/loan")
    public RestResponse<?> getLoansByAmountRange(
            @RequestParam double minAmount,
            @RequestParam double maxAmount) {

        List<ValidationError> errors = validationService.validateAmountRange(minAmount, maxAmount);

        if (!errors.isEmpty()) {
            return new RestResponse<>(400, "Invalid range parameters", errors);
        }

        List<LoanDetails> loans = loanService.getLoansWithinRange(minAmount, maxAmount);
        return new RestResponse<>(200, "Successful", loans);
    }

    @GetMapping("/loans")
    public RestResponse<?> getAllLoanInformation() {
        List<LoanDetails> loans = loanService.getAllLoanDetails();
        return new RestResponse<>(200, "Successful", loans);
    }

}