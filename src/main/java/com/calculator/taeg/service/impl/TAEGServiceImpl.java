package com.calculator.taeg.service.impl;

import com.calculator.taeg.dto.LoanRequest;
import com.calculator.taeg.dto.LoanResponse;
import com.calculator.taeg.model.Loan;
import com.calculator.taeg.repository.LoanRepository;
import com.calculator.taeg.service.TAEGService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TAEGServiceImpl implements TAEGService {

    private final LoanRepository loanRepository;

    public LoanResponse calculateTAEG(LoanRequest loanRequestDTO) {
        Loan loan = new Loan();
        loan.setLoanAmount(loanRequestDTO.getLoanAmount());
        loan.setInterestRate(loanRequestDTO.getInterestRate());
        loan.setNumberOfPayments(loanRequestDTO.getNumberOfPayments());
        loan.setInsuranceCost(loanRequestDTO.getInsuranceCost());

        double taeg = (loan.getInterestRate() * loan.getNumberOfPayments()) / (loan.getLoanAmount() + loan.getInsuranceCost()) * 100;
        loan.setTaeg(taeg);
        loan.setCalculationTime(LocalDateTime.now());

        Loan savedLoan = loanRepository.save(loan);

        return new LoanResponse(savedLoan.getId(), savedLoan.getTaeg(), savedLoan.getCalculationTime());
    }
}
