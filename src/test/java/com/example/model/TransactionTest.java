package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    private Transaction transaction;
    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan();
        loan.setId(1L);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setLoan(loan);
        transaction.setAmount(BigDecimal.valueOf(1000.00));
        transaction.setType(TransactionType.REPAYMENT);
        transaction.setTransactionDate(LocalDateTime.now());
    }

    @Test
    void testDefaultConstructor() {
        Transaction defaultTransaction = new Transaction();
        assertNotNull(defaultTransaction);
    }

    @Test
    void testParameterizedConstructor() {
        Transaction parameterizedTransaction = new Transaction(1L, loan, BigDecimal.valueOf(1000.00), TransactionType.REPAYMENT, LocalDateTime.now());
        assertEquals(1L, parameterizedTransaction.getId());
        assertEquals(loan, parameterizedTransaction.getLoan());
        assertEquals(BigDecimal.valueOf(1000.00), parameterizedTransaction.getAmount());
        assertEquals(TransactionType.REPAYMENT, parameterizedTransaction.getType());
        assertNotNull(parameterizedTransaction.getTransactionDate());
    }

    @Test
    void testGetters() {
        assertEquals(1L, transaction.getId());
        assertEquals(loan, transaction.getLoan());
        assertEquals(BigDecimal.valueOf(1000.00), transaction.getAmount());
        assertEquals(TransactionType.REPAYMENT, transaction.getType());
        assertNotNull(transaction.getTransactionDate());
    }

    @Test
    void testSetters() {
        Loan newLoan = new Loan();
        newLoan.setId(2L);

        transaction.setId(2L);
        transaction.setLoan(newLoan);
        transaction.setAmount(BigDecimal.valueOf(2000.00));
        transaction.setType(TransactionType.REPAYMENT);
        transaction.setTransactionDate(LocalDateTime.now().minusDays(1));

        assertEquals(2L, transaction.getId());
        assertEquals(newLoan, transaction.getLoan());
        assertEquals(BigDecimal.valueOf(2000.00), transaction.getAmount());
        assertEquals(TransactionType.REPAYMENT, transaction.getType());
        assertNotNull(transaction.getTransactionDate());
    }
}
