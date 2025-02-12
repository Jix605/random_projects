package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {
    public static final int ITEM_DEFAULT_QUANTITY = 5;
    private final Map<String, Sellable> inventory = new LinkedHashMap<>();
    private final Map<String, Integer> inventoryCount = new HashMap<>();
    private BigDecimal machineBalance = BigDecimal.ZERO;
    private final Transaction transaction = new Transaction(new BigDecimal(0));

    public void writeToFile(String destinationPath, String text) {
        File file = new File(destinationPath);

        try {
            file.createNewFile();
        } catch (IOException e) {}

        try (
                FileOutputStream outputStream = new FileOutputStream(file, true);
                PrintWriter writer = new PrintWriter(outputStream)
        ){
            writer.println(text);
            writer.flush();
        } catch (IOException e){
            System.out.println("An Error Occured with filewriting: " + e.getMessage());
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        String a = "";
        for (Map.Entry<String, Sellable> item : inventory.entrySet()){
            String position = item.getKey();
            Sellable sellable = item.getValue();

            string.append(position + ": " +
                    sellable.getName() + " " +
                    sellable.getType() + " $" +
                    sellable.getPrice() + " "
            );

            int stock = getItemStock(position);
            if (stock == 0){
                string.append("OUT OF STOCK");
            } else {
                string.append(stock);
            }

            string.append("\n");
        }

        return string.toString();
    }

    public int getItemStock(String location) {
        return inventoryCount.getOrDefault(location, 0);
    }

    private void reduceQuantity(String location){
        int quantity = inventoryCount.get(location);

        if (quantity > 0) {
            inventoryCount.put(location, quantity - 1);
        } else {
            System.out.println("Item is SOLD OUT!");
        }
    }

    private Sellable getItem(String location){
        return inventory.get(location);
    }

    public boolean purchaseItem(String location, BigDecimal balance, Customer customer) {
        Sellable item = getItem(location);
        // Check if item is null value
        if (item == null) {
            return false;
        }

        // Check if the item is out of stock
        if (getItemStock(location) <= 0) {
            System.out.println("The item is out of stock!");
            return false;
        }

        // Proceed with the transaction
        boolean success = transaction.makeTransaction(item, balance, customer);
        if (!success) {
            return false;
        }

        // Reduce the quantity after a successful transaction
        reduceQuantity(location);

        return true;
    }
    public void stockMachine(){
        try(
                Scanner fileReader = new Scanner(new File("./vendingmachine.csv"))
        ){
            while (fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                String[] words = line.split("\\|");

                String location = words[0];
                String name = words[1];
                BigDecimal price = new BigDecimal(words[2]);
                String type = words[3];

                // set up quantity
                inventoryCount.put(location, ITEM_DEFAULT_QUANTITY);

                // actual product inventory
                switch(type){
                    case "Drink":
                        inventory.put(location, new Beverage(name, price));
                        break;
                    case "Chip":
                        inventory.put(location, new Chip(name, price));
                        break;
                    case "Candy":
                        inventory.put(location, new Candy(name, price));
                        break;
                    case "Gum":
                        inventory.put(location, new Gum(name, price));
                        break;
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private String getTypedResponse(String message, Scanner keyboard){
        System.out.print(message);

        return keyboard.nextLine();
    }

    public int getSelectionInt(Scanner keyboard){
        // Get input
        int selection = 0;
        do {
            try{
                String response = getTypedResponse("Please input your selection between 1-3: ", keyboard);
                selection = Integer.parseInt(response);
            } catch(NumberFormatException e){
                System.out.println("Please input a number between 1 - 3 in numerical form");
            }
        } while(selection == 0 && selection > 4);

        System.out.println(selection);

        return selection;
    }

    private void purchase(Customer customer, Scanner keyboard){
        BigDecimal vendingMachineMoney = new BigDecimal("0.00");

        while (true){
            System.out.println("Current Money Provided: $" + vendingMachineMoney + "\n");
            System.out.println("(1) Feed Money\n(2) Select Product\n(3) Finish Transaction");

            int purchaseSelection = getSelectionInt(keyboard);

            if (purchaseSelection == 3){
                BigDecimal changeDispensed = transaction.dispenseChange(vendingMachineMoney);
                vendingMachineMoney = BigDecimal.ZERO;
                customer.getBankAccount().deposit(changeDispensed);

                System.out.println("Money in machine: " + vendingMachineMoney);
                break;
            } else if (purchaseSelection == 1){
                int selection = 0;
                do {
                    try{
                        System.out.print("Please input the money to insert as a positive whole number: ");
                        selection = Integer.parseInt(keyboard.nextLine());
                    } catch(NumberFormatException e){
                        System.out.println("Please input a valid number");
                    }
                } while(selection <= 0);

                BigDecimal amountToAdd = new BigDecimal(selection + ".00");

                boolean success = customer.getBankAccount().withdraw(amountToAdd);
                if (success){
                    vendingMachineMoney = vendingMachineMoney.add(amountToAdd);
                } else {
                    System.out.println("Insufficient funds! you only have: $" + customer.getBankAccount().getBalance() + " left!");
                }

                LocalDate date= LocalDate.now();
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String amOrPm = LocalTime.now().isBefore(LocalTime.NOON) ? "AM" : "PM";

                writeToFile("Log.txt", date + " " + time + " " + amOrPm + " "
                        + "FEED MONEY: "
                        + "$" + amountToAdd + " "
                        + "$" + vendingMachineMoney
                );
            } else if (purchaseSelection == 2){
                // To output stock
                System.out.println(this);

                String itemToPurchase;
                try {
                    System.out.println("Please input the item code (e.g., A3): ");
                    itemToPurchase = keyboard.nextLine().toUpperCase();

                    boolean itemPurchase = purchaseItem(itemToPurchase, vendingMachineMoney, customer);

                    if (inventory.get(itemToPurchase) == null){
                        System.out.println("The specified product code does not exist.");
                        continue;
                    }

                    if (inventoryCount.get(itemToPurchase) == 0){
                        System.out.println("The specified product is out of stock!");
                        continue;
                    }

                    if (itemPurchase){
                        BigDecimal itemCost = inventory.get(itemToPurchase).getPrice();
                        vendingMachineMoney = vendingMachineMoney.subtract(itemCost);
                        machineBalance = machineBalance.add(itemCost);

                        LocalDate date= LocalDate.now();
                        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                        String amOrPm = LocalTime.now().isBefore(LocalTime.NOON) ? "AM" : "PM";

                        writeToFile("Log.txt", date + " " + time + " " + amOrPm + " "
                                + inventory.get(itemToPurchase).getName() + " "
                                + itemToPurchase + " "
                                + "$" + itemCost + " "
                                + "$" + vendingMachineMoney
                        );
                    }
                } catch (Exception e){}
            }
        }
    }

    public void mainMenu(Customer customer){
        Scanner keyboard = new Scanner(System.in);

        while (true) {
            System.out.println("(1) Display Vending Machine Items\n(2) Purchase\n(3) Finish Transaction");

            int selection = this.getSelectionInt(keyboard);

            if (selection == 3){
                System.out.println("Thank you for shopping at the Vendo-Matic 800. Your new balance is: " + customer.getBankAccount().getBalance());
                keyboard.close();
                break;
            } else if (selection == 1){
                System.out.println(this);
            } else if (selection == 2){
                // send user to purchase section
                this.purchase(customer, keyboard);
            } else if (selection == 4){
                StringBuilder builder = new StringBuilder();

                for (Map.Entry<String, Sellable> item : inventory.entrySet()){
                    String location = item.getKey();
                    Sellable product = item.getValue();

                    builder.append(product.getName() + "|" + (ITEM_DEFAULT_QUANTITY - getItemStock(location)) + "\n");
                }

                builder.append("\n**TOTAL SALES** $" + machineBalance.toString());
                String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy'year'MM'month'dd'days'HH'hours'mm'minutes'ss'seconds'"));

                writeToFile("sales_report_" + dateTime  + ".txt", builder.toString());
            }
        }
    }
}