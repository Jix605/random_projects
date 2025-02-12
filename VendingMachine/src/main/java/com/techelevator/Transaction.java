package com.techelevator;

import java.math.BigDecimal;

public class Transaction {
    private Sellable selectedItem;  // The selected item
    private BigDecimal amountPaid; // Amount paid by the customer
    private BigDecimal change; // Change to be returned to the customer

    // Constructor for Transaction
    public Transaction(Sellable selectedItem, BigDecimal amountPaid) {
        this.selectedItem = selectedItem;
        this.amountPaid = amountPaid;
        this.change = BigDecimal.ZERO; // Returns the 0 after transaction
    }

    public Transaction(BigDecimal change){
        this.change = change;
    }

    public Sellable getSelectedItem() {
        return selectedItem;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public BigDecimal getChange() {
        return change;
    }

    // Method to calculate and dispense change (Quarters, dimes, and nickels)
    public BigDecimal dispenseChange(BigDecimal change) {
        BigDecimal changeToReturn = change;
        // Dispense change according to coins from the largest value to smallest
        if (change.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("Dispensing change: $" + change);
            int quarters = change.divide(new BigDecimal("0.25")).intValue();
            change = change.remainder(new BigDecimal("0.25"));
            int dimes = change.divide(new BigDecimal("0.10")).intValue();
            change = change.remainder(new BigDecimal("0.10"));
            int nickels = change.divide(new BigDecimal("0.05")).intValue();
            change = change.remainder(new BigDecimal("0.01"));

            // Print coin breakdown;
            System.out.println("Quarters: " + quarters);
            System.out.println("Dimes: " + dimes);
            System.out.println("Nickels: " + nickels);
        }

        change = BigDecimal.ZERO; // Reset change after dispensing
        return changeToReturn;
    }

    public boolean makeTransaction(Sellable item, BigDecimal amountPaid, Customer customer) {
        // Invalid item or item price
        if (item == null || item.getPrice() == null) {
            System.out.println("Invalid item or item price is null.");
            return false;
        }
        // Null payment
        if (amountPaid == null) {
            System.out.println("Amount paid is null.");
            return false;
        }
        // Insufficient Funds
        if (amountPaid.compareTo(item.getPrice()) < 0) {
            System.out.println("Insufficient funds. Please add more money.");
            amountPaid = BigDecimal.ZERO;
            selectedItem = null;
            return false;
        }

        // Set the selected item and amount paid
        this.selectedItem = item;
        this.amountPaid = amountPaid;

        // Calculate change
        this.change = amountPaid.subtract(item.getPrice());

        // Dispense item
        dispenseItem(item, change);

        return true;
    }

    private void dispenseItem(Sellable item, BigDecimal balance) {
        System.out.println("Dispensing: " + item.getName() + " $" + item.getPrice() + " " + balance);
        System.out.println(item.getMessage()); // Print the item's message
    }
}
