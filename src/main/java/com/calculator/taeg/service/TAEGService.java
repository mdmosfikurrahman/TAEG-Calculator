package com.calculator.taeg.service;

import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.LoanResponse;

public interface TAEGService {

    LoanResponse calculateTAEG(LoanRequest loanRequestDTO);

}
