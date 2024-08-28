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
        Loan loan = new Loan();
        loan.setLoanAmount(loanRequest.getLoanAmount());
        loan.setInterestRate(loanRequest.getInterestRate());
        loan.setNumberOfPayments(loanRequest.getNumberOfPayments());
        loan.setInsuranceCost(loanRequest.getInsuranceCost());
        loan.setTaeg(calculateTAEG(loan));
        loan.setCalculationTime(LocalDateTime.now());
        Loan savedLoan = loanRepository.save(loan);
        CalculationTimeResponse calculationTimeResponse = new CalculationTimeResponse(
                savedLoan.getCalculationTime().format(DATE_FORMATTER),
                savedLoan.getCalculationTime().format(TIME_FORMATTER)
        );

        return new LoanResponse(
                savedLoan.getId(),
                savedLoan.getLoanAmount(),
                savedLoan.getInterestRate(),
                savedLoan.getNumberOfPayments(),
                savedLoan.getInsuranceCost(),
                savedLoan.getTaeg(),
                calculationTimeResponse
        );
    }

    @Override
    public List<LoanResponse> getLoansByAmountRange(double minAmount, double maxAmount) {
        List<Loan> loans = loanRepository.findByLoanAmountBetween(minAmount, maxAmount);

        return loans.stream().map(loan -> {
            CalculationTimeResponse calculationTimeResponse = new CalculationTimeResponse(
                    loan.getCalculationTime().format(DATE_FORMATTER),
                    loan.getCalculationTime().format(TIME_FORMATTER)
            );

            return new LoanResponse(
                    loan.getId(),
                    loan.getLoanAmount(),
                    loan.getInterestRate(),
                    loan.getNumberOfPayments(),
                    loan.getInsuranceCost(),
                    loan.getTaeg(),
                    calculationTimeResponse
            );
        }).toList();
    }
    
    private double calculateTAEG(Loan loan) {
        double denominator = loan.getLoanAmount() + loan.getInsuranceCost();
        return denominator > 0
                ? (loan.getInterestRate() * loan.getNumberOfPayments()) / denominator * 100
                : 0;
    }
}
