package com.example.service;

import com.example.dto.UserDTO;
import com.example.model.EmailDetails;
import com.example.model.Loan;
import com.example.model.Repayment;
import com.example.model.Transaction;
import com.example.model.TransactionType;
import com.example.repository.LoanRepository;
import com.example.repository.RepaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepaymentServiceTest {

    @Mock
    private RepaymentRepository repaymentRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RepaymentService repaymentService;

    private Loan loan;
    private Repayment repayment;
    private List<Repayment> repayments;

    @BeforeEach
    void setUp() {
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
        loan.setCreatedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());

        repayment = new Repayment();
        repayment.setId(1L);
        repayment.setLoan(loan);
        repayment.setAmount(BigDecimal.valueOf(875.00));
        repayment.setRepaymentDate(LocalDateTime.now());
        repayment.setStatus(Repayment.RepaymentStatus.COMPLETED);

        repayments = new ArrayList<>();
        repayments.add(repayment);
    }

    @Test
    void testGetRepaymentsByLoanId() {
        when(repaymentRepository.findByLoanId(1L)).thenReturn(repayments);
        
        List<Repayment> foundRepayments = repaymentService.getRepaymentsByLoanId(1L);
        
        assertNotNull(foundRepayments);
        assertEquals(1, foundRepayments.size());
        assertEquals(repayment, foundRepayments.get(0));
    }

    @Test
    void testFindRepaymentById() {
        when(repaymentRepository.findById(1L)).thenReturn(Optional.of(repayment));
        
        Repayment foundRepayment = repaymentService.findRepaymentById(1L);
        
        assertNotNull(foundRepayment);
        assertEquals(repayment, foundRepayment);
    }

   

    
    @Test
    void testUpdateRepayment() {
        Repayment updatedRepaymentDetails = new Repayment();
        updatedRepaymentDetails.setRepaymentDate(LocalDateTime.now().plusDays(1));
        updatedRepaymentDetails.setAmount(BigDecimal.valueOf(900.00));
        updatedRepaymentDetails.setStatus(Repayment.RepaymentStatus.PENDING);

        when(repaymentRepository.findById(1L)).thenReturn(Optional.of(repayment));
        when(repaymentRepository.save(any(Repayment.class))).thenReturn(updatedRepaymentDetails);

        Repayment updatedRepayment = repaymentService.updateRepayment(1L, updatedRepaymentDetails);

        assertNotNull(updatedRepayment);
        assertEquals(updatedRepaymentDetails.getRepaymentDate(), updatedRepayment.getRepaymentDate());
        assertEquals(updatedRepaymentDetails.getAmount(), updatedRepayment.getAmount());
        assertEquals(updatedRepaymentDetails.getStatus(), updatedRepayment.getStatus());
    }

    @Test
    void testDeleteRepayment() {
        when(repaymentRepository.findById(1L)).thenReturn(Optional.of(repayment));

        repaymentService.deleteRepayment(1L);

        verify(repaymentRepository, times(1)).delete(repayment);
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionService).recordTransaction(transactionCaptor.capture());
        Transaction failedTransaction = transactionCaptor.getValue();
        assertEquals(repayment.getLoan(), failedTransaction.getLoan());
        assertEquals(TransactionType.FAILED, failedTransaction.getType());
        assertEquals(repayment.getAmount(), failedTransaction.getAmount());
    }

    @Test
    void testCalculateRepaymentAmount() {
        when(loanRepository.findByLoanId(1L)).thenReturn(loan);
        when(repaymentRepository.findByLoanId(1L)).thenReturn(repayments);

        BigDecimal repaymentAmount = repaymentService.calculateRepaymentAmount(1L);

        assertNotNull(repaymentAmount);
        assertTrue(repaymentAmount.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testCalculateEMI() {
        BigDecimal amount = BigDecimal.valueOf(10000);
        BigDecimal annualInterestRate = BigDecimal.valueOf(5.0);
        int tenureInYears = 1;

        BigDecimal emi = repaymentService.calculateEMI(amount, annualInterestRate, tenureInYears);

        assertNotNull(emi);
        assertTrue(emi.compareTo(BigDecimal.ZERO) > 0);
    }

    

    
}
