package com.calculator.taeg.dto;

import lombok.Data;

@Data
public class LoanRequest {

    private double loanAmount;
    private double interestRate;
    private int numberOfPayments;
    private double insuranceCost;

}