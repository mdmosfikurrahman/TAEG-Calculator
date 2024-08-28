package com.calculator.taeg.controller;

import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.LoanResponse;
import com.calculator.taeg.service.TAEGService;
import com.calculator.taeg.validator.LoanRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/taeg")
public class TAEGController {

    private final TAEGService taegService;
    private final LoanRequestValidator loanRequestValidator;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateTAEG(@RequestBody LoanRequest loanRequest) {
        BindingResult result = new BeanPropertyBindingResult(loanRequest, "loanRequest");
        loanRequestValidator.validate(loanRequest, result);

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        LoanResponse response = taegService.calculateTAEG(loanRequest);
        return ResponseEntity.ok(response);
    }
}
