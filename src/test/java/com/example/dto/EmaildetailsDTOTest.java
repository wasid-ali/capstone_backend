package com.example.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmaildetailsDTOTest {

    private EmaildetailsDTO emailDetails;

    @BeforeEach
    void setUp() {
        emailDetails = new EmaildetailsDTO();
    }

    @Test
    void testDefaultConstructor() {
        EmaildetailsDTO defaultEmailDetails = new EmaildetailsDTO();
        assertNotNull(defaultEmailDetails);
    }

    @Test
    void testParameterizedConstructor() {
        EmaildetailsDTO parameterizedEmailDetails = new EmaildetailsDTO(1L, "test@example.com", "Test Subject", "Test Body");
        assertEquals(1L, parameterizedEmailDetails.getId());
        assertEquals("test@example.com", parameterizedEmailDetails.getRecipient());
        assertEquals("Test Subject", parameterizedEmailDetails.getSubject());
        assertEquals("Test Body", parameterizedEmailDetails.getMsgBody());
    }

    @Test
    void testGetters() {
        emailDetails.setId(1L);
        emailDetails.setRecipient("test@example.com");
        emailDetails.setSubject("Test Subject");
        emailDetails.setMsgBody("Test Body");

        assertEquals(1L, emailDetails.getId());
        assertEquals("test@example.com", emailDetails.getRecipient());
        assertEquals("Test Subject", emailDetails.getSubject());
        assertEquals("Test Body", emailDetails.getMsgBody());
    }

    @Test
    void testSetters() {
        emailDetails.setId(2L);
        emailDetails.setRecipient("new@example.com");
        emailDetails.setSubject("New Subject");
        emailDetails.setMsgBody("New Body");

        assertEquals(2L, emailDetails.getId());
        assertEquals("new@example.com", emailDetails.getRecipient());
        assertEquals("New Subject", emailDetails.getSubject());
        assertEquals("New Body", emailDetails.getMsgBody());
    }
}
