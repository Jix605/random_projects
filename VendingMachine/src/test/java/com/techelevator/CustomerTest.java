package com.techelevator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CustomerTest {
    private Customer customer;

    @Test
    public void Customer_constructor_works_propertly_and_bank_account_exists(){
        // Arrange/Act
        customer = new Customer(new BigDecimal("20.00"));

        // Assert
        Assertions.assertNotNull(customer);
        Assertions.assertNotNull(customer.getBankAccount());
    }
}