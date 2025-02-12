package com.techelevator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class SellableTest {
    @Test
    public void Beverage_constructor_correctly_configures(){
        // Arrange
        Beverage beverage = new Beverage("Soda", new BigDecimal("2.99"));

        // Act
        String type = beverage.getType();
        String expectedType = "Drink";

        String name = beverage.getName();
        String expectedName = "Soda";

        BigDecimal price = beverage.getPrice();
        BigDecimal expectedPrice = new BigDecimal("2.99");

        String message = beverage.getMessage();
        String expectedMessage = "Glug Glug, Yum!";

        // Assert
        Assertions.assertEquals(expectedType, type);
        Assertions.assertEquals(expectedName, name);
        Assertions.assertEquals(expectedPrice, price);
        Assertions.assertEquals(expectedMessage, message);
    }

    @Test
    public void Gum_constructor_correctly_configures(){
        // Arrange
        Gum gum = new Gum("Gum", new BigDecimal("0.99"));

        // Act
        String type = gum.getType();
        String expectedType = "Gum";

        String name = gum.getName();
        String expectedName = "Gum";

        BigDecimal price = gum.getPrice();
        BigDecimal expectedPrice = new BigDecimal("0.99");

        String message = gum.getMessage();
        String expectedMessage = "Chew Chew, Yum!";

        // Assert
        Assertions.assertEquals(expectedType, type);
        Assertions.assertEquals(expectedName, name);
        Assertions.assertEquals(expectedPrice, price);
        Assertions.assertEquals(expectedMessage, message);
    }

    @Test
    public void Candy_constructor_correctly_configures(){
        // Arrange
        Candy candy = new Candy("Candy", new BigDecimal("1.99"));

        // Act
        String type = candy.getType();
        String expectedType = "Candy";

        String name = candy.getName();
        String expectedName = "Candy";

        BigDecimal price = candy.getPrice();
        BigDecimal expectedPrice = new BigDecimal("1.99");

        String message = candy.getMessage();
        String expectedMessage = "Munch Munch, Yum!";

        // Assert
        Assertions.assertEquals(expectedType, type);
        Assertions.assertEquals(expectedName, name);
        Assertions.assertEquals(expectedPrice, price);
        Assertions.assertEquals(expectedMessage, message);
    }

    @Test
    public void Chip_constructor_correctly_configures(){
        // Arrange
        Chip chip = new Chip("Chip", new BigDecimal("4.99"));

        // Act
        String type = chip.getType();
        String expectedType = "Chip";

        String name = chip.getName();
        String expectedName = "Chip";

        BigDecimal price = chip.getPrice();
        BigDecimal expectedPrice = new BigDecimal("4.99");

        String message = chip.getMessage();
        String expectedMessage = "Crunch Crunch, Yum!";

        // Assert
        Assertions.assertEquals(expectedType, type);
        Assertions.assertEquals(expectedName, name);
        Assertions.assertEquals(expectedPrice, price);
        Assertions.assertEquals(expectedMessage, message);
    }
}