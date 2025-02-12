package com.techelevator;

import java.math.BigDecimal;

public class Beverage implements Sellable {
    private String name;
    private String type = "Drink";
    private BigDecimal price;
    private String message = "Glug Glug, Yum!";

    /**
     * constructs an object with name, and a price
     * @param name name of the item
     * @param price price of the item
     */
    public Beverage(String name, BigDecimal price){
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String getMessage() {
        return message;
    }
}