package com.example.controller;

import com.example.model.Repayment;
import com.example.service.RepaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RepaymentControllerTest {

    @Mock
    private RepaymentService repaymentService;

    @InjectMocks
    private RepaymentController repaymentController;

    private MockMvc mockMvc;

    private Repayment repayment;
    private List<Repayment> repaymentList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repaymentController).build();

        repayment = new Repayment();
        repayment.setId(1L);
        repayment.setAmount(BigDecimal.valueOf(1000.00));

        repaymentList = new ArrayList<>();
        repaymentList.add(repayment);
    }

    @Test
    void testGetRepaymentsByLoanId() throws Exception {
        when(repaymentService.getRepaymentsByLoanId(anyLong())).thenReturn(repaymentList);

        mockMvc.perform(get("/api/repayments/loan/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].amount").value(1000.00));

        verify(repaymentService, times(1)).getRepaymentsByLoanId(anyLong());
    }

    @Test
    void testCalculateRepaymentAmount() throws Exception {
        when(repaymentService.calculateRepaymentAmount(anyLong())).thenReturn(BigDecimal.valueOf(1000.00));

        mockMvc.perform(get("/api/repayments/calculate-repayment-amount/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1000.00));

        verify(repaymentService, times(1)).calculateRepaymentAmount(anyLong());
    }

    @Test
    void testCalculateRepaymentAmount_Exception() throws Exception {
        when(repaymentService.calculateRepaymentAmount(anyLong())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/repayments/calculate-repayment-amount/1"))
                .andExpect(status().isBadRequest());

        verify(repaymentService, times(1)).calculateRepaymentAmount(anyLong());
    }

    @Test
    void testCreateRepayment() throws Exception {
        when(repaymentService.createRepayment(anyLong())).thenReturn(repayment);

        mockMvc.perform(post("/api/repayments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(1000.00));

        verify(repaymentService, times(1)).createRepayment(anyLong());
    }

    @Test
    void testUpdateRepayment() throws Exception {
        when(repaymentService.updateRepayment(anyLong(), any(Repayment.class))).thenReturn(repayment);

        mockMvc.perform(put("/api/repayments/1")
                .contentType("application/json")
                .content("{\"amount\": 2000.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(1000.00));

        verify(repaymentService, times(1)).updateRepayment(anyLong(), any(Repayment.class));
    }

    @Test
    void testDeleteRepayment() throws Exception {
        doNothing().when(repaymentService).deleteRepayment(anyLong());

        mockMvc.perform(delete("/api/repayments/1"))
                .andExpect(status().isNoContent());

        verify(repaymentService, times(1)).deleteRepayment(anyLong());
    }
}
