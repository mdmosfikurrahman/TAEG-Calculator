package com.calculator.taeg.service.impl;

import com.calculator.taeg.dto.LoanDetails;
import com.calculator.taeg.dto.TimeOfCalculation;
import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.model.Loan;
import com.calculator.taeg.repository.LoanRepository;
import com.calculator.taeg.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH);

    private Loan createLoanFromRequest(LoanRequest request) {
        return Loan.builder()
                .amount(request.getLoanAmount())
                .rate(request.getInterestRate())
                .payments(request.getNumberOfPayments())
                .insurance(request.getInsuranceCost())
                .taeg(calculateTAEG(
                        request.getLoanAmount(),
                        request.getInterestRate(),
                        request.getNumberOfPayments(),
                        request.getInsuranceCost()))
                .calculationTime(LocalDateTime.now())
                .build();
    }

    private LoanDetails convertToLoanDetails(Loan loan) {
        TimeOfCalculation timeOfCalculation = TimeOfCalculation.builder()
                .date(loan.getCalculationTime().format(DATE_FORMATTER))
                .time(loan.getCalculationTime().format(TIME_FORMATTER))
                .build();

        return LoanDetails.builder()
                .amount(loan.getAmount())
                .rate(loan.getRate())
                .payments(loan.getPayments())
                .insurance(loan.getInsurance())
                .taeg(loan.getTaeg())
                .timeOfCalculation(timeOfCalculation)
                .build();
    }

    private double calculateTAEG(double amount, double rate, int payments, double insurance) {
        double totalAmount = amount + insurance;
        return totalAmount > 0
                ? (rate * payments) / totalAmount * 100
                : 0;
    }

    @Override
    public LoanDetails calculateLoanDetails(LoanRequest request) {
        Loan loan = createLoanFromRequest(request);
        Loan savedLoan = loanRepository.save(loan);
        return convertToLoanDetails(savedLoan);
    }

    @Override
    public List<LoanDetails> getLoansWithinRange(double minAmount, double maxAmount) {
        List<Loan> loans = loanRepository.findByAmountBetween(minAmount, maxAmount);
        return loans.stream().map(this::convertToLoanDetails).toList();
    }

    @Override
    public List<LoanDetails> getAllLoanDetails() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(this::convertToLoanDetails).toList();
    }
}
