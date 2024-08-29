package com.calculator.taeg.service.impl;

import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.ValidationError;
import com.calculator.taeg.service.LoanRequestValidationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanRequestValidationServiceImpl implements LoanRequestValidationService {

    @Override
    public List<ValidationError> validateLoanRequest(LoanRequest loanRequest) {
        List<ValidationError> errors = new ArrayList<>();

        if (loanRequest.getLoanAmount() <= 0) {
            errors.add(new ValidationError(
                    "LoanRequest",
                    "loanAmount",
                    "LoanAmount must be greater than zero",
                    loanRequest.getLoanAmount()
            ));
        }

        if (loanRequest.getInterestRate() <= 0) {
            errors.add(new ValidationError(
                    "LoanRequest",
                    "interestRate",
                    "InterestRate must be greater than zero",
                    loanRequest.getInterestRate()
            ));
        }

        if (loanRequest.getNumberOfPayments() <= 0) {
            errors.add(new ValidationError(
                    "LoanRequest",
                    "numberOfPayments",
                    "NumberOfPayments must be greater than zero",
                    loanRequest.getNumberOfPayments()
            ));
        }

        if (loanRequest.getInsuranceCost() < 0) {
            errors.add(new ValidationError(
                    "LoanRequest",
                    "insuranceCost",
                    "InsuranceCost cannot be negative",
                    loanRequest.getInsuranceCost()
            ));
        }

        return errors;
    }

    @Override
    public List<ValidationError> validateAmountRange(double minAmount, double maxAmount) {
        List<ValidationError> errors = new ArrayList<>();

        if (minAmount < 0) {
            errors.add(new ValidationError(
                    "LoanRange",
                    "minAmount",
                    "Minimum amount cannot be negative",
                    minAmount
            ));
        }
        if (maxAmount < 0) {
            errors.add(new ValidationError(
                    "LoanRange",
                    "maxAmount",
                    "Maximum amount cannot be negative",
                    maxAmount
            ));
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        if (minAmount > maxAmount) {
            errors.add(new ValidationError(
                    "LoanRange",
                    "minAmount",
                    "Minimum amount cannot be greater than maximum amount",
                    minAmount
            ));
            errors.add(new ValidationError(
                    "LoanRange",
                    "maxAmount",
                    "Minimum amount cannot be greater than maximum amount",
                    maxAmount
            ));
        }

        return errors;
    }

}
