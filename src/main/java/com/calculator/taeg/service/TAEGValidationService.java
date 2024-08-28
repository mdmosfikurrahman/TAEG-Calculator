package com.calculator.taeg.service;

import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.ValidationError;

import java.util.List;

public interface TAEGValidationService {


    List<ValidationError> validateLoanRequest(LoanRequest loanRequestDTO);

    List<ValidationError> validateAmountRange(double minAmount, double maxAmount);

}
