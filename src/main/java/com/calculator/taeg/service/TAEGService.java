package com.calculator.taeg.service;

import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.LoanResponse;

import java.util.List;

public interface TAEGService {

    LoanResponse calculateTAEG(LoanRequest loanRequest);

    List<LoanResponse> getLoansByAmountRange(double minAmount, double maxAmount);

}
