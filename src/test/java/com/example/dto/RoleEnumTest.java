package com.example.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class RoleEnumTest {

    @Test
    void testEnumValues() {
        RoleEnum[] roles = RoleEnum.values();
        assertNotNull(roles);
        assertEquals(3, roles.length);
        assertEquals(RoleEnum.CUSTOMER, roles[0]);
        assertEquals(RoleEnum.LOAN_OFFICER, roles[1]);
        assertEquals(RoleEnum.ADMIN, roles[2]);
    }

    @Test
    void testValueOf() {
        assertEquals(RoleEnum.CUSTOMER, RoleEnum.valueOf("CUSTOMER"));
        assertEquals(RoleEnum.LOAN_OFFICER, RoleEnum.valueOf("LOAN_OFFICER"));
        assertEquals(RoleEnum.ADMIN, RoleEnum.valueOf("ADMIN"));
    }
}
