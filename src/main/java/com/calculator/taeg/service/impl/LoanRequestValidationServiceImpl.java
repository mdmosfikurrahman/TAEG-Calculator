package com.calculator.taeg.service.impl;

import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.ValidationError;
import com.calculator.taeg.service.LoanRequestValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanRequestValidationServiceImpl implements LoanRequestValidationService {

    private static final Logger logger = LoggerFactory.getLogger(LoanRequestValidationServiceImpl.class);

    private static final String LOAN_REQUEST_TYPE = "LoanRequest";
    private static final String LOAN_RANGE_TYPE = "LoanRange";

    @Override
    public List<ValidationError> validateLoanRequest(LoanRequest loanRequest) {
        List<ValidationError> errors = new ArrayList<>();

        if (loanRequest == null) {
            errors.add(new ValidationError(
                    LOAN_REQUEST_TYPE,
                    "loanRequest",
                    "LoanRequest cannot be null",
                    null
            ));
            logger.error("Validation error: LoanRequest object is null.");
            return errors;
        }

        if (loanRequest.getLoanAmount() <= 0) {
            errors.add(new ValidationError(
                    LOAN_REQUEST_TYPE,
                    "loanAmount",
                    "LoanAmount must be greater than zero",
                    loanRequest.getLoanAmount()
            ));
            logger.warn("Validation error: LoanAmount {} is not greater than zero.", loanRequest.getLoanAmount());
        }

        if (loanRequest.getInterestRate() <= 0) {
            errors.add(new ValidationError(
                    LOAN_REQUEST_TYPE,
                    "interestRate",
                    "InterestRate must be greater than zero",
                    loanRequest.getInterestRate()
            ));
            logger.warn("Validation error: InterestRate {} is not greater than zero.", loanRequest.getInterestRate());
        }

        if (loanRequest.getNumberOfPayments() <= 0) {
            errors.add(new ValidationError(
                    LOAN_REQUEST_TYPE,
                    "numberOfPayments",
                    "NumberOfPayments must be greater than zero",
                    loanRequest.getNumberOfPayments()
            ));
            logger.warn("Validation error: NumberOfPayments {} is not greater than zero.", loanRequest.getNumberOfPayments());
        }

        if (loanRequest.getInsuranceCost() < 0) {
            errors.add(new ValidationError(
                    LOAN_REQUEST_TYPE,
                    "insuranceCost",
                    "InsuranceCost cannot be negative",
                    loanRequest.getInsuranceCost()
            ));
            logger.warn("Validation error: InsuranceCost {} is negative.", loanRequest.getInsuranceCost());
        }

        if (!errors.isEmpty()) {
            logger.warn("Validation errors found for LoanRequest: {}", errors);
        }

        return errors;
    }

    @Override
    public List<ValidationError> validateAmountRange(double minAmount, double maxAmount) {
        List<ValidationError> errors = new ArrayList<>();

        if (minAmount < 0) {
            errors.add(new ValidationError(
                    LOAN_RANGE_TYPE,
                    "minAmount",
                    "Minimum amount cannot be negative",
                    minAmount
            ));
            logger.warn("Validation error: Minimum amount {} is negative.", minAmount);
        }
        if (maxAmount < 0) {
            errors.add(new ValidationError(
                    LOAN_RANGE_TYPE,
                    "maxAmount",
                    "Maximum amount cannot be negative",
                    maxAmount
            ));
            logger.warn("Validation error: Maximum amount {} is negative.", maxAmount);
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        if (minAmount > maxAmount) {
            errors.add(new ValidationError(
                    LOAN_RANGE_TYPE,
                    "minAmount",
                    "Minimum amount cannot be greater than maximum amount",
                    minAmount
            ));
            errors.add(new ValidationError(
                    LOAN_RANGE_TYPE,
                    "maxAmount",
                    "Maximum amount cannot be less than minimum amount",
                    maxAmount
            ));
            logger.warn("Validation error: Minimum amount {} is greater than maximum amount {}.", minAmount, maxAmount);
        }

        if (!errors.isEmpty()) {
            logger.warn("Validation errors found for LoanRange: {}", errors);
        }

        return errors;
    }
}
