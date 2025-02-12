package com.techelevator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {
    private VendingMachine vendingMachine;
    private Customer customer;
    private File testLogFile;

    @BeforeEach
    public void setUp() {
        vendingMachine = new VendingMachine();
        customer = new Customer(new BigDecimal("20.00"));
        vendingMachine.stockMachine();
        // Create a test log file in the current directory
        testLogFile = new File("test_log.txt");
    }

    @AfterEach
    public void tearDown() {
        // Delete the test log file after each test if it exists
        if (testLogFile.exists()) {
            testLogFile.delete();
        }
    }

    // Test Attempting to Purchase with Insufficient Funds
    @Test
    public void purchaseItem_with_insufficient_funds_returns_false() {
        // Arrange
        String location = "A1"; // Assuming "A1" exists in the CSV
        BigDecimal insufficientAmount = new BigDecimal("1.00"); // Less than item price
        // Act
        boolean result = vendingMachine.purchaseItem(location, insufficientAmount, customer);
        // Assert
        assertFalse(result, "Purchase should fail when the balance is insufficient");
    }

    // Test Writing to a File
    @Test
    public void writeToFile_creates_and_writes_file() throws IOException {
        // Arrange
        String content = "Test log entry";
        // Act
        vendingMachine.writeToFile(testLogFile.getAbsolutePath(), content);
        // Assert
        assertTrue(testLogFile.exists(), "File should be created");
        try (Scanner scanner = new Scanner(testLogFile)) {
            assertEquals(content, scanner.nextLine(), "File content should match the input text");
        }
    }

    // Test Purchasing a Valid Item
    @Test
    public void purchaseItem_valid_item_reduces_stock() {
        // Arrange
        String location = "A1"; // Assuming "A1" exists in the CSV file
        int initialStock = vendingMachine.getItemStock(location);
        // Act
        boolean result = vendingMachine.purchaseItem(location, new BigDecimal("5.00"), customer);
        // Assert
        assertTrue(result, "Purchase should be successful");
        assertEquals(initialStock - 1, vendingMachine.getItemStock(location), "Stock should be reduced by 1");
    }

    // Test Purchasing an Out-of-Stock Item
    @Test
    public void purchaseItem_out_of_stock_returns_false() {
        // Arrange
        String location = "A1"; // Assuming "A1" exists
        for (int i = 0; i < VendingMachine.ITEM_DEFAULT_QUANTITY; i++) {
            vendingMachine.purchaseItem(location, new BigDecimal("5.00"), customer);
        }
        // Act
        boolean result = vendingMachine.purchaseItem(location, new BigDecimal("5.00"), customer);
        // Assert
        assertFalse(result, "Purchase should fail when the item is out of stock");
    }

    // Test Getting Item Stock for Non-Existent Item
    @Test
    public void getItemStock_non_existent_item_returns_zero() {
        // Act
        int stock = vendingMachine.getItemStock("Z99"); // Assuming "Z99" doesn't exist
        // Assert
        assertEquals(0, stock, "Stock for non-existent item should be zero");
    }

    // Test User Input for Menu Selection
    @Test
    public void getSelectionInt_valid_input_returns_correct_selection() {
        // Arrange
        String input = "2\n";
        Scanner scanner = new Scanner(input);
        // Act
        int selection = vendingMachine.getSelectionInt(scanner);
        // Assert
        assertEquals(2, selection, "Selection should be 2 for valid input '2'");
    }

    // Test Getting Item Stock for a Valid Location
    @Test
    public void getItemStock_valid_location_returns_correct_stock() {
        // Arrange
        String location = "A1"; // Assuming "A1" exists
        int expectedStock = VendingMachine.ITEM_DEFAULT_QUANTITY;
        // Act
        int stock = vendingMachine.getItemStock(location);
        // Assert
        assertEquals(expectedStock, stock, "Stock should match the default quantity for a valid location");
    }
}
