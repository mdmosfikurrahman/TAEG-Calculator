package com.calculator.taeg.validator;

import com.calculator.taeg.dto.LoanRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoanRequestValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return LoanRequest.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target,@NonNull Errors errors) {
        LoanRequest loanRequest = (LoanRequest) target;

        if (loanRequest.getLoanAmount() <= 0) {
            errors.rejectValue("loanAmount", "LoanAmount must be greater than zero");
        }

        if (loanRequest.getInterestRate() <= 0) {
            errors.rejectValue("interestRate", "InterestRate must be greater than zero");
        }

        if (loanRequest.getNumberOfPayments() <= 0) {
            errors.rejectValue("numberOfPayments", "NumberOfPayments must be greater than zero");
        }

        if (loanRequest.getInsuranceCost() < 0) {
            errors.rejectValue("insuranceCost", "InsuranceCost cannot be negative");
        }
    }
}
