package com.example.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class LoanApplicationStatusTest {

    @Test
    void testEnumValues() {
        LoanApplicationStatus[] statuses = LoanApplicationStatus.values();
        assertNotNull(statuses);
        assertEquals(7, statuses.length);
        assertEquals(LoanApplicationStatus.APPLIED, statuses[0]);
        assertEquals(LoanApplicationStatus.REJECTED, statuses[1]);
        assertEquals(LoanApplicationStatus.ACCEPTED, statuses[2]);
        assertEquals(LoanApplicationStatus.PENDING, statuses[3]);
        assertEquals(LoanApplicationStatus.APPROVED, statuses[4]);
        assertEquals(LoanApplicationStatus.ACTIVE, statuses[5]);
        assertEquals(LoanApplicationStatus.PAID_OFF, statuses[6]);
    }

    @Test
    void testValueOf() {
        assertEquals(LoanApplicationStatus.APPLIED, LoanApplicationStatus.valueOf("APPLIED"));
        assertEquals(LoanApplicationStatus.REJECTED, LoanApplicationStatus.valueOf("REJECTED"));
        assertEquals(LoanApplicationStatus.ACCEPTED, LoanApplicationStatus.valueOf("ACCEPTED"));
        assertEquals(LoanApplicationStatus.PENDING, LoanApplicationStatus.valueOf("PENDING"));
        assertEquals(LoanApplicationStatus.APPROVED, LoanApplicationStatus.valueOf("APPROVED"));
        assertEquals(LoanApplicationStatus.ACTIVE, LoanApplicationStatus.valueOf("ACTIVE"));
        assertEquals(LoanApplicationStatus.PAID_OFF, LoanApplicationStatus.valueOf("PAID_OFF"));
    }
}
