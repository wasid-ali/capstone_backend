package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoanTest {

    private Loan loan;
    private List<Repayment> repayments;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        repayments = new ArrayList<>();
        transactions = new ArrayList<>();

        loan = new Loan();
        loan.setId(1L);
        loan.setCustomerId(1L);
        loan.setLoanManagerId(1L);
        loan.setLoanApplication(1L);
        loan.setStatus(Loan.LoanStatus.active);
        loan.setAmount(BigDecimal.valueOf(10000.00));
        loan.setInterestRate(BigDecimal.valueOf(5.00));
        loan.setRepayableAmount(BigDecimal.valueOf(10500.00));
        loan.setEMIAmount(BigDecimal.valueOf(875.00));
        loan.setLateFee(BigDecimal.valueOf(50.00));
        loan.setPrePaymentFee(BigDecimal.valueOf(100.00));
        loan.setStartDate(LocalDate.now().plusDays(1));
        loan.setEndDate(LocalDate.now().plusYears(1));
        loan.setRepaymentFrequency(Loan.RepaymentFrequency.MONTHLY);
        loan.setRepayments(repayments);
        loan.setTransactions(transactions);
        loan.setCreatedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testDefaultConstructor() {
        Loan defaultLoan = new Loan();
        assertNotNull(defaultLoan);
    }

    @Test
    void testParameterizedConstructor() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusYears(1);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Loan parameterizedLoan = new Loan(1L, 1L, 1L, 1L, Loan.LoanStatus.active, BigDecimal.valueOf(10000.00),
                BigDecimal.valueOf(5.00), BigDecimal.valueOf(10500.00), BigDecimal.valueOf(875.00),
                BigDecimal.valueOf(50.00), BigDecimal.valueOf(100.00), startDate, endDate,
                Loan.RepaymentFrequency.MONTHLY, repayments, transactions, createdAt, updatedAt);

        assertEquals(1L, parameterizedLoan.getId());
        assertEquals(1L, parameterizedLoan.getCustomerId());
        assertEquals(1L, parameterizedLoan.getLoanManagerId());
        assertEquals(1L, parameterizedLoan.getLoanApplication());
        assertEquals(Loan.LoanStatus.active, parameterizedLoan.getStatus());
        assertEquals(BigDecimal.valueOf(10000.00), parameterizedLoan.getAmount());
        assertEquals(BigDecimal.valueOf(5.00), parameterizedLoan.getInterestRate());
        assertEquals(BigDecimal.valueOf(10500.00), parameterizedLoan.getRepayableAmount());
        assertEquals(BigDecimal.valueOf(875.00), parameterizedLoan.getEMIAmount());
        assertEquals(BigDecimal.valueOf(50.00), parameterizedLoan.getLateFee());
        assertEquals(BigDecimal.valueOf(100.00), parameterizedLoan.getPrePaymentFee());
        assertEquals(startDate, parameterizedLoan.getStartDate());
        assertEquals(endDate, parameterizedLoan.getEndDate());
        assertEquals(Loan.RepaymentFrequency.MONTHLY, parameterizedLoan.getRepaymentFrequency());
        assertEquals(repayments, parameterizedLoan.getRepayments());
        assertEquals(transactions, parameterizedLoan.getTransactions());
        assertEquals(createdAt, parameterizedLoan.getCreatedAt());
        assertEquals(updatedAt, parameterizedLoan.getUpdatedAt());
    }

    @Test
    void testGetters() {
        assertEquals(1L, loan.getId());
        assertEquals(1L, loan.getCustomerId());
        assertEquals(1L, loan.getLoanManagerId());
        assertEquals(1L, loan.getLoanApplication());
        assertEquals(Loan.LoanStatus.active, loan.getStatus());
        assertEquals(BigDecimal.valueOf(10000.00), loan.getAmount());
        assertEquals(BigDecimal.valueOf(5.00), loan.getInterestRate());
        assertEquals(BigDecimal.valueOf(10500.00), loan.getRepayableAmount());
        assertEquals(BigDecimal.valueOf(875.00), loan.getEMIAmount());
        assertEquals(BigDecimal.valueOf(50.00), loan.getLateFee());
        assertEquals(BigDecimal.valueOf(100.00), loan.getPrePaymentFee());
        assertEquals(LocalDate.now().plusDays(1), loan.getStartDate());
        assertEquals(LocalDate.now().plusYears(1), loan.getEndDate());
        assertEquals(Loan.RepaymentFrequency.MONTHLY, loan.getRepaymentFrequency());
        assertEquals(repayments, loan.getRepayments());
        assertEquals(transactions, loan.getTransactions());
        assertNotNull(loan.getCreatedAt());
        assertNotNull(loan.getUpdatedAt());
    }

    @Test
    void testSetters() {
        List<Repayment> newRepayments = new ArrayList<>();
        List<Transaction> newTransactions = new ArrayList<>();
        LocalDate newStartDate = LocalDate.now().plusMonths(1);
        LocalDate newEndDate = LocalDate.now().plusYears(2);
        LocalDateTime newCreatedAt = LocalDateTime.now().minusDays(1);
        LocalDateTime newUpdatedAt = LocalDateTime.now().minusDays(1);

        loan.setId(2L);
        loan.setCustomerId(2L);
        loan.setLoanManagerId(2L);
        loan.setLoanApplication(2L);
        loan.setStatus(Loan.LoanStatus.paid_off);
        loan.setAmount(BigDecimal.valueOf(20000.00));
        loan.setInterestRate(BigDecimal.valueOf(6.00));
        loan.setRepayableAmount(BigDecimal.valueOf(21200.00));
        loan.setEMIAmount(BigDecimal.valueOf(1060.00));
        loan.setLateFee(BigDecimal.valueOf(75.00));
        loan.setPrePaymentFee(BigDecimal.valueOf(150.00));
        loan.setStartDate(newStartDate);
        loan.setEndDate(newEndDate);
        loan.setRepaymentFrequency(Loan.RepaymentFrequency.QUARTERLY);
        loan.setRepayments(newRepayments);
        loan.setTransactions(newTransactions);
        loan.setCreatedAt(newCreatedAt);
        loan.setUpdatedAt(newUpdatedAt);

        assertEquals(2L, loan.getId());
        assertEquals(2L, loan.getCustomerId());
        assertEquals(2L, loan.getLoanManagerId());
        assertEquals(2L, loan.getLoanApplication());
        assertEquals(Loan.LoanStatus.paid_off, loan.getStatus());
        assertEquals(BigDecimal.valueOf(20000.00), loan.getAmount());
        assertEquals(BigDecimal.valueOf(6.00), loan.getInterestRate());
        assertEquals(BigDecimal.valueOf(21200.00), loan.getRepayableAmount());
        assertEquals(BigDecimal.valueOf(1060.00), loan.getEMIAmount());
        assertEquals(BigDecimal.valueOf(75.00), loan.getLateFee());
        assertEquals(BigDecimal.valueOf(150.00), loan.getPrePaymentFee());
        assertEquals(newStartDate, loan.getStartDate());
        assertEquals(newEndDate, loan.getEndDate());
        assertEquals(Loan.RepaymentFrequency.QUARTERLY, loan.getRepaymentFrequency());
        assertEquals(newRepayments, loan.getRepayments());
        assertEquals(newTransactions, loan.getTransactions());
        assertEquals(newCreatedAt, loan.getCreatedAt());
        assertEquals(newUpdatedAt, loan.getUpdatedAt());
    }
}
