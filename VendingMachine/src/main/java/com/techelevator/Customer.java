package com.techelevator;

import java.math.BigDecimal;

public class Customer{
    private BigDecimal balance;
    private BankAccount bankAccount;

    public Customer(BigDecimal balance) {
        this.balance = balance;
        this.bankAccount = new BankAccount();
        bankAccount.deposit(balance); // Initialize with the given balance
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

}