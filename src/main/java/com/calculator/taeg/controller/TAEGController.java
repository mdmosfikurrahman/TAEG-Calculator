package com.calculator.taeg.controller;

import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.LoanResponse;
import com.calculator.taeg.dto.RestResponse;
import com.calculator.taeg.dto.ValidationError;
import com.calculator.taeg.service.TAEGService;
import com.calculator.taeg.service.TAEGValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/taeg")
public class TAEGController {

    private final TAEGService taegService;
    private final TAEGValidationService validationService;

    @PostMapping("/calculate")
    public RestResponse<?> calculateTAEG(@RequestBody LoanRequest loanRequest) {
        List<ValidationError> errors = validationService.validateLoanRequest(loanRequest);

        if (!errors.isEmpty()) {
            return new RestResponse<>(400, "Validation Failed", errors);
        }

        LoanResponse response = taegService.calculateTAEG(loanRequest);
        return new RestResponse<>(200, "Successful", response);
    }

    @GetMapping("/loans")
    public RestResponse<?> getLoansByAmountRange(
            @RequestParam double minAmount,
            @RequestParam double maxAmount) {

        List<ValidationError> errors = validationService.validateAmountRange(minAmount, maxAmount);

        if (!errors.isEmpty()) {
            return new RestResponse<>(400, "Invalid range parameters", errors);
        }

        List<LoanResponse> loans = taegService.getLoansByAmountRange(minAmount, maxAmount);
        return new RestResponse<>(200, "Successful", loans);
    }

}