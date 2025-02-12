package com.techelevator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TransactionTest {
    private Chip chips;
    private Beverage drink;
    private Gum gum;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        chips = new Chip("Potato Crisps", new BigDecimal("3.05"));
        drink = new Beverage("Cola", new BigDecimal("1.25"));
        gum = new Gum("Gum", new BigDecimal("0.95"));
        BankAccount bankAccount = new BankAccount();
        bankAccount.deposit(new BigDecimal("10.00")); // Sufficient funds for tests
        transaction = new Transaction(null, BigDecimal.ZERO);

    }

    // Valid Transaction Test
    @Test
    public void valid_transaction_test() {
        // Arrange
        Customer customer = new Customer(new BigDecimal("10.00"));

        // Act
        transaction.makeTransaction(chips, new BigDecimal("5.00"), customer);

        // Assert
        Assertions.assertEquals(chips, transaction.getSelectedItem());
        Assertions.assertEquals(new BigDecimal("5.00"), transaction.getAmountPaid());
        Assertions.assertEquals(new BigDecimal("1.95"), transaction.getChange());
    }

    // Exact Amount Payment Test
    @Test
    public void exact_amount_payment_test() {
        // Arrange
        Customer customer = new Customer(new BigDecimal("10.00"));

        // Act
        transaction.makeTransaction(drink, new BigDecimal("1.25"), customer);

        // Assert
        Assertions.assertEquals(drink, transaction.getSelectedItem());
        Assertions.assertEquals(new BigDecimal("1.25"), transaction.getAmountPaid());
        Assertions.assertTrue(transaction.getChange().compareTo(BigDecimal.ZERO) == 0, "Change should be zero.");
    }

    // Insufficient Funds Test
    @Test
    public void insufficient_funds_test() {
        // Arrange
        Customer customer = new Customer(new BigDecimal("0.50"));
        // Act
        transaction.makeTransaction(gum, new BigDecimal("0.50"), customer);
        // Assert
        Assertions.assertNull(transaction.getSelectedItem(), "Item cannot be selected due to insufficient funds.");
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getAmountPaid());
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getChange());
    }

    // Negative Payment Amount Test
    @Test
    public void negative_payment_amount_test() {
        // Arrange
        Customer customer = new Customer(new BigDecimal("10.00"));
        // Act
        transaction.makeTransaction(gum, new BigDecimal("-1.00"), customer);
        // Assert
        Assertions.assertNull(transaction.getSelectedItem(), "Item cannot be selected for negative payment.");
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getAmountPaid());
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getChange());
    }

    // Zero Payment Amount Test
    @Test
    public void zero_payment_amount_test() {
        // Arrange
        Customer customer = new Customer(new BigDecimal("10.00"));
        // Act
        transaction.makeTransaction(gum, BigDecimal.ZERO, customer);
        // Assert
        Assertions.assertNull(transaction.getSelectedItem(), "Item cannot be selected for zero payment.");
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getAmountPaid());
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getChange());
    }

    // Item with Null Price Test
    @Test
    public void item_with_null_price_test() {
        // Arrange
        Customer customer = new Customer(new BigDecimal("10.00"));
        Sellable item = new Chip("Faulty Item", null);
        // Act
        transaction.makeTransaction(item, new BigDecimal("5.00"), customer);
        // Assert
        Assertions.assertNull(transaction.getSelectedItem(), "Item cannot be selected if its price is null.");
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getAmountPaid());
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getChange());
    }

    // Null Payment Amount Test
    @Test
    public void null_payment_amount_test() {
        // Arrange
        Customer customer = new Customer(new BigDecimal("10.00"));

        // Act
        transaction.makeTransaction(chips, null, customer);

        // Assert
        Assertions.assertNull(transaction.getSelectedItem(), "Item cannot be selected for null payment.");
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getAmountPaid());
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getChange());
    }

    // Withdrawal Failure Due to Insufficient Balance Test
    @Test
    public void withdrawal_failure_due_to_insufficient_balance_test() {
        // Arrange
        Customer customer = new Customer(new BigDecimal("2.00"));
        // Act
        // this seems counterintuitive but makeTransaction is based on
        // the machines deposited money (the second parameter) not the customer balance
        transaction.makeTransaction(chips, new BigDecimal("2.00"), customer);
        // Assert
        Assertions.assertNull(transaction.getSelectedItem(), "Item cannot be selected if withdrawal fails due to insufficient balance.");
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getAmountPaid());
        Assertions.assertEquals(BigDecimal.ZERO, transaction.getChange());
    }
}

