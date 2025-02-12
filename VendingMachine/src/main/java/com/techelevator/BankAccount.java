package com.techelevator;

import java.math.BigDecimal;

public class BankAccount {
    private final int BIG_DECIMAL_LESS_THAN = -1;
    private BigDecimal balance;

    public BankAccount() {
        this.balance = BigDecimal.ZERO; // Initialize with zero balance
    }

    /**
     * returns the users current balance
     * @return BigDecimal
     */
    public BigDecimal getBalance() {
        return balance;
    }

    private void setBalance(BigDecimal newBalance){
        balance = newBalance;
    }

    /**
     * returns success
     * @param toAdd amount of money to add
     * @return boolean
     */
    public boolean deposit(BigDecimal toAdd) {
        // if toAdd is null or balance is less than 0 return (do nothing)
        if (toAdd == null || balance.compareTo(BigDecimal.ZERO) == BIG_DECIMAL_LESS_THAN) {
            return false;
        }

        // make sure we do not deposit negative numbers
        if (toAdd.compareTo(BigDecimal.ZERO) == BIG_DECIMAL_LESS_THAN) {
            return false;
        }

        setBalance(balance.add(toAdd));
        return true;
    }

    /**
     * returns success
     * @param toRemove amount of money to remove
     * @return boolean
     */
    public boolean withdraw(BigDecimal toRemove) {
        // if balance is null or balance is less than 0 return (do nothing)
        if (toRemove == null || balance.compareTo(BigDecimal.ZERO) == BIG_DECIMAL_LESS_THAN) {
            return false;
        }
        BigDecimal newBalance = balance.subtract(toRemove);

        // make sure withdraw does not work if new balance is less than 0
        if (newBalance.compareTo(BigDecimal.ZERO) == BIG_DECIMAL_LESS_THAN) {
            return false;
        }

        setBalance(newBalance);
        return true;
    }
}