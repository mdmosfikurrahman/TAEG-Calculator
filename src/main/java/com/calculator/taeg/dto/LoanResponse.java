package com.calculator.taeg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanResponse {

    private Long loanId;
    private double loanAmount;
    private double interestRate;
    private int numberOfPayments;
    private double insuranceCost;
    private double taeg;
    private CalculationTimeResponse calculationTimeResponse;

}