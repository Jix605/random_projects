package com.techelevator;

import java.math.BigDecimal;

public interface Sellable {
    String getName();
    String getType();
    BigDecimal getPrice();
    String getMessage();
}