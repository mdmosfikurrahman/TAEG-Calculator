package com.calculator.taeg.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanDetails {

    private double amount;
    private double rate;
    private int payments;
    private double insurance;
    private double taeg;
    private TimeOfCalculation timeOfCalculation;

}
