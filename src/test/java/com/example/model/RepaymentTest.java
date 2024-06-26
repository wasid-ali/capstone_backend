package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepaymentTest {

    private Repayment repayment;
    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan();
        loan.setId(1L);

        repayment = new Repayment();
        repayment.setId(1L);
        repayment.setLoan(loan);
        repayment.setAmount(BigDecimal.valueOf(500.00));
        repayment.setRepaymentDate(LocalDateTime.now());
        repayment.setStatus(Repayment.RepaymentStatus.COMPLETED);
    }

    @Test
    void testDefaultConstructor() {
        Repayment defaultRepayment = new Repayment();
        assertNotNull(defaultRepayment);
    }

    @Test
    void testParameterizedConstructor() {
        Repayment parameterizedRepayment = new Repayment(1L, loan, BigDecimal.valueOf(500.00), LocalDateTime.now(), Repayment.RepaymentStatus.COMPLETED);
        assertEquals(1L, parameterizedRepayment.getId());
        assertEquals(loan, parameterizedRepayment.getLoan());
        assertEquals(BigDecimal.valueOf(500.00), parameterizedRepayment.getAmount());
        assertEquals(Repayment.RepaymentStatus.COMPLETED, parameterizedRepayment.getStatus());
    }

    @Test
    void testGetters() {
        assertEquals(1L, repayment.getId());
        assertEquals(loan, repayment.getLoan());
        assertEquals(BigDecimal.valueOf(500.00), repayment.getAmount());
        assertEquals(Repayment.RepaymentStatus.COMPLETED, repayment.getStatus());
        assertNotNull(repayment.getRepaymentDate());
    }

    @Test
    void testSetters() {
        Loan newLoan = new Loan();
        newLoan.setId(2L);

        repayment.setId(2L);
        repayment.setLoan(newLoan);
        repayment.setAmount(BigDecimal.valueOf(1000.00));
        repayment.setRepaymentDate(LocalDateTime.now().minusDays(1));
        repayment.setStatus(Repayment.RepaymentStatus.PENDING);

        assertEquals(2L, repayment.getId());
        assertEquals(newLoan, repayment.getLoan());
        assertEquals(BigDecimal.valueOf(1000.00), repayment.getAmount());
        assertEquals(Repayment.RepaymentStatus.PENDING, repayment.getStatus());
        assertNotNull(repayment.getRepaymentDate());
    }
}
