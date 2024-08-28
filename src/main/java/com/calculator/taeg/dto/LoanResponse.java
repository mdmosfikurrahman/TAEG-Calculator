package com.calculator.taeg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoanResponse {

    private Long loanId;
    private double taeg;
    private LocalDateTime calculationTime;

}
