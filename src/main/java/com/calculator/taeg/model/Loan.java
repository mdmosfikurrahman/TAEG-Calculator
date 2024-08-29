package com.calculator.taeg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "loans")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "rate", nullable = false)
    private double rate;

    @Column(name = "payments", nullable = false)
    private int payments;

    @Column(name = "insurance", nullable = false)
    private double insurance;

    @Column(name = "taeg", nullable = false)
    private double taeg;

    @Column(name = "calculation_time", nullable = false)
    private LocalDateTime calculationTime;

}
