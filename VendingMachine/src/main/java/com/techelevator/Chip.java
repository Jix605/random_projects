package com.techelevator;

import java.math.BigDecimal;

public class Chip implements Sellable {
    private String name;
    private String type = "Chip";
    private BigDecimal price;
    private String message = "Crunch Crunch, Yum!";

    /**
     * constructs an object with name, and a price
     * @param name name of the item
     * @param price price of the item
     */
    public Chip(String name, BigDecimal price){
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