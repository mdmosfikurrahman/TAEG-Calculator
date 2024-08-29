package com.calculator.taeg.service;

import com.calculator.taeg.dto.LoanDetails;
import com.calculator.taeg.dto.LoanRequest;

import java.util.List;

public interface LoanService {

    LoanDetails calculateLoanDetails(LoanRequest loanRequest);

    List<LoanDetails> getLoansWithinRange(double minAmount, double maxAmount);

    List<LoanDetails> getAllLoanDetails();
}
