package com.techelevator;

import java.math.BigDecimal;

public class Application {

	public static void main(String[] args) {
		VendingMachine vendingMachine = new VendingMachine();
		vendingMachine.stockMachine();

		Customer customer = new Customer(new BigDecimal("2000.00"));
        vendingMachine.mainMenu(customer);
	}
}
