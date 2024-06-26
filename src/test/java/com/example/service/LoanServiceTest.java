package com.example.service;

import com.example.dto.LoanApplicationDTO;
import com.example.dto.LoanProductDetailsDTO;
import com.example.dto.UserDTO;
import com.example.dto.VendorDTO;
import com.example.exception.LoanNotFoundException;
import com.example.model.Loan;
import com.example.model.Loan.LoanStatus;
import com.example.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LoanService loanService;

    private Loan loan;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        loan = new Loan();
        loan.setId(1L);
        loan.setCustomerId(1L);
        loan.setAmount(new BigDecimal("10000.00"));
        loan.setInterestRate(new BigDecimal("5.00"));
        loan.setPrePaymentFee(new BigDecimal("1.00"));
        loan.setLateFee(new BigDecimal("50.00"));
        loan.setRepayableAmount(new BigDecimal("10500.00"));
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusYears(1));
        loan.setStatus(LoanStatus.active);
        loan.setRepaymentFrequency(Loan.RepaymentFrequency.MONTHLY);
        loan.setEMIAmount(new BigDecimal("875.00"));
        loan.setLoanManagerId(1L);
    }

    @Test
    public void testUpdateLoan() {
        Loan existingLoan = createLoan();
        Loan updatedLoanDetails = createUpdatedLoan();

        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(existingLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(updatedLoanDetails);

        Loan updatedLoan = loanService.updateLoan(1L, updatedLoanDetails);

        assertNotNull(updatedLoan);
        assertEquals(updatedLoanDetails.getAmount(), updatedLoan.getAmount());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    public void testUpdateLoan_LoanNotFoundException() {
        when(loanRepository.findById(anyLong())).thenReturn(Optional.empty());

        Loan updatedLoanDetails = createUpdatedLoan();

        assertThrows(LoanNotFoundException.class, () -> loanService.updateLoan(1L, updatedLoanDetails));
    }

    @Test
    public void testGetLoanById() {
        Loan loan = createLoan();
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));

        Loan fetchedLoan = loanService.getLoanById(1L);

        assertNotNull(fetchedLoan);
        assertEquals(loan.getLoanApplication(), fetchedLoan.getLoanApplication());
        verify(loanRepository, times(1)).findById(anyLong());
    }
    

    @Test
    public void testGetLoanById_LoanNotFoundException() {
        when(loanRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class, () -> loanService.getLoanById(1L));
    }

    @Test
    public void testGetLoansByCustomerId() {
        List<Loan> loans = Arrays.asList(loan);
        when(loanRepository.findByCustomerId(anyLong())).thenReturn(loans);

        List<Loan> fetchedLoans = loanService.getLoansByCustomerId(1L);

        assertNotNull(fetchedLoans);
        assertFalse(fetchedLoans.isEmpty());
        verify(loanRepository, times(1)).findByCustomerId(anyLong());
    }

    @Test
    public void testGetAllLoans() {
        List<Loan> loans = Arrays.asList(loan);
        when(loanRepository.findAll()).thenReturn(loans);

        List<Loan> fetchedLoans = loanService.getAllLoans();

        assertNotNull(fetchedLoans);
        assertFalse(fetchedLoans.isEmpty());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteLoanById() {
        when(loanRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(loanRepository).deleteById(anyLong());

        loanService.deleteLoanById(1L);

        verify(loanRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteLoanById_LoanNotFoundException() {
        when(loanRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(LoanNotFoundException.class, () -> loanService.deleteLoanById(1L));
    }

    private Loan createLoan() {
        Loan loan = new Loan();
        loan.setLoanApplication(1L);
        loan.setCustomerId(1L);
        loan.setAmount(BigDecimal.valueOf(1000));
        loan.setInterestRate(BigDecimal.valueOf(5));
        loan.setRepayableAmount(BigDecimal.valueOf(1050));
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusYears(1));
        loan.setEMIAmount(BigDecimal.valueOf(88));
        loan.setLoanManagerId(1L);
        loan.setStatus(Loan.LoanStatus.active);
        loan.setRepaymentFrequency(Loan.RepaymentFrequency.MONTHLY);
        return loan;
    }

    private Loan createUpdatedLoan() {
        Loan loan = new Loan();
        loan.setAmount(BigDecimal.valueOf(2000));
        loan.setInterestRate(BigDecimal.valueOf(6));
        loan.setRepayableAmount(BigDecimal.valueOf(2120));
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusYears(2));
        loan.setEMIAmount(BigDecimal.valueOf(88));
        loan.setLoanManagerId(1L);
        loan.setStatus(Loan.LoanStatus.active);
        loan.setRepaymentFrequency(Loan.RepaymentFrequency.MONTHLY);
        return loan;
    }

    @Test
    public void testGetActiveLoans() {
        when(loanRepository.findByCustomerIdAndStatus(1L, LoanStatus.active)).thenReturn(Arrays.asList(loan));

        List<Loan> activeLoans = loanService.getActiveLoans(1L);

        assertNotNull(activeLoans);
        assertFalse(activeLoans.isEmpty());
        verify(loanRepository, times(1)).findByCustomerIdAndStatus(1L, LoanStatus.active);
    }

    @Test
    public void testGetClosedLoans() {
        loan.setStatus(LoanStatus.paid_off);
        when(loanRepository.findByCustomerIdAndStatus(1L, LoanStatus.paid_off)).thenReturn(Arrays.asList(loan));

        List<Loan> closedLoans = loanService.getClosedLoans(1L);

        assertNotNull(closedLoans);
        assertFalse(closedLoans.isEmpty());
        verify(loanRepository, times(1)).findByCustomerIdAndStatus(1L, LoanStatus.paid_off);
    }
}
