package com.calculator.taeg.service.impl;

import com.calculator.taeg.dto.CalculationTimeResponse;
import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.LoanResponse;
import com.calculator.taeg.model.Loan;
import com.calculator.taeg.repository.LoanRepository;
import com.calculator.taeg.service.TAEGService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TAEGServiceImpl implements TAEGService {

    private final LoanRepository loanRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");

    @Override
    public LoanResponse calculateTAEG(LoanRequest loanRequest) {
        Loan loan = mapToLoanEntity(loanRequest);
        Loan savedLoan = loanRepository.save(loan);
        return mapToLoanResponse(savedLoan);
    }

    @Override
    public List<LoanResponse> getLoansByAmountRange(double minAmount, double maxAmount) {
        List<Loan> loans = loanRepository.findByLoanAmountBetween(minAmount, maxAmount);
        return loans.stream().map(this::mapToLoanResponse).toList();
    }

    @Override
    public List<LoanResponse> getAllLoanInformation() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(this::mapToLoanResponse).toList();
    }

    private Loan mapToLoanEntity(LoanRequest loanRequest) {
        return Loan.builder()
                .loanAmount(loanRequest.getLoanAmount())
                .interestRate(loanRequest.getInterestRate())
                .numberOfPayments(loanRequest.getNumberOfPayments())
                .insuranceCost(loanRequest.getInsuranceCost())
                .taeg(
                        calculateTAEG(
                                loanRequest.getLoanAmount(),
                                loanRequest.getInterestRate(),
                                loanRequest.getNumberOfPayments(),
                                loanRequest.getInsuranceCost()
                        ))
                .calculationTime(LocalDateTime.now())
                .build();
    }

    private LoanResponse mapToLoanResponse(Loan loan) {
        CalculationTimeResponse calculationTimeResponse = CalculationTimeResponse.builder()
                .date(loan.getCalculationTime().format(DATE_FORMATTER))
                .time(loan.getCalculationTime().format(TIME_FORMATTER))
                .build();

        return LoanResponse.builder()
                .loanId(loan.getId())
                .loanAmount(loan.getLoanAmount())
                .interestRate(loan.getInterestRate())
                .numberOfPayments(loan.getNumberOfPayments())
                .insuranceCost(loan.getInsuranceCost())
                .taeg(loan.getTaeg())
                .calculationTimeResponse(calculationTimeResponse)
                .build();
    }

    private double calculateTAEG(double loanAmount, double interestRate, int numberOfPayments, double insuranceCost) {
        double denominator = loanAmount + insuranceCost;
        return denominator > 0
                ? (interestRate * numberOfPayments) / denominator * 100
                : 0;
    }
}
