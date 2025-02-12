package com.techelevator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BankAccountTest {
    private BankAccount bank;
    @BeforeEach
    public void setup(){
        bank = new BankAccount();
        bank.deposit(new BigDecimal("30.00"));
    }

    @Test
    public void getBalance_returns_27_with_input_30_withdraw_3() {
        // Arrange
        BigDecimal expected = new BigDecimal("27.00");

        // Act
        boolean success = bank.withdraw(new BigDecimal("3.00"));
        BigDecimal balance = bank.getBalance();

        // Assert
        Assertions.assertTrue(success, "Withdrawal should be successful");
        Assertions.assertEquals(expected, balance, "Balance should be 27.00 after withdrawing 3.00 from 30.00");
    }

    @Test
    public void getBalance_returns_0_with_input_30_withdraw_30() {
        // Arrange
        BigDecimal expected = new BigDecimal("0.00");

        // Act
        boolean success = bank.withdraw(new BigDecimal("30.00"));
        BigDecimal balance = bank.getBalance().setScale(2); // Ensure scale matches expected value

        // Assert
        Assertions.assertTrue(success, "Withdrawal should be successful");
        Assertions.assertEquals(expected, balance, "Balance should be 0.00 after withdrawing 30.00");
    }

    @Test
    public void withdraw_returns_false_with_input_30_withdraw_33(){
        // Arrange

        // Act
        boolean Success = bank.withdraw(new BigDecimal("33.00"));

        // Assert
        Assertions.assertFalse(Success);
    }

    @Test
    public void deposit_returns_false_with_input_30_withdraw_negative_33(){
        // Arrange

        // Act
        boolean Success = bank.deposit(new BigDecimal("-33.00"));

        // Assert
        Assertions.assertFalse(Success);
    }

    @Test
    public void deposit_returns_false_and_does_not_change_balance() {
        // Arrange
        BigDecimal expected = new BigDecimal("30.00");

        // Act
        boolean success = bank.deposit(new BigDecimal("-33.00")); // Attempt to deposit a negative amount

        // Assert
        Assertions.assertFalse(success, "Deposit should fail when attempting to deposit a negative amount");
        Assertions.assertEquals(expected, bank.getBalance(), "Balance should remain unchanged after failed deposit");
    }

    @Test
    public void withdraw_returns_false_and_does_not_change_balance() {
        // Arrange
        BigDecimal expected = new BigDecimal("30.00");

        // Act
        boolean success = bank.withdraw(new BigDecimal("33.00")); // Attempt to withdraw more than the balance

        // Assert
        Assertions.assertFalse(success, "Withdrawal should fail when attempting to withdraw more than the balance");
        Assertions.assertEquals(expected, bank.getBalance(), "Balance should remain unchanged after failed withdrawal");
    }

    @Test
    public void withdraw_works_with_change() {
        // Arrange
        bank = new BankAccount();
        bank.deposit(new BigDecimal("30.25")); // Set initial balance to 30.25
        BigDecimal expected = new BigDecimal("0.00");

        // Act
        boolean success = bank.withdraw(new BigDecimal("30.25")); // Withdraw the exact balance

        // Assert
        Assertions.assertTrue(success, "Withdrawal should succeed when withdrawing the exact balance with change");
        Assertions.assertEquals(expected, bank.getBalance(), "Balance should be 0.00 after withdrawing the exact balance");
    }

    @Test
    public void deposit_works_with_change(){
        // Arrange
        bank = new BankAccount();
        BigDecimal expected = new BigDecimal("30.25");

        // Act
        boolean Success = bank.deposit(new BigDecimal("30.25"));

        // Assert
        Assertions.assertTrue(Success);
        Assertions.assertEquals(expected, bank.getBalance());
    }
}