package com.calculator.taeg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "loan")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double loanAmount;
    private double interestRate;
    private int numberOfPayments;
    private double insuranceCost;
    private double taeg;
    private LocalDateTime calculationTime;

}
