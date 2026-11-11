package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: SKU|Product Name|Price|Department)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            // If user enters wrong number
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }


    public static void loadInventory(String fileName, ArrayList<Product> inventory) {

        // The store's inventory display
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String line;

            // skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] sections = line.split("\\|");

                if (sections.length != 4) {
                    System.out.println("Invalid record: " + line);
                    continue;
                }

                // Split fields
                String productId = sections[0];
                String productName = sections[1];
                double productPrice = Double.parseDouble(sections[2]);
                String productLocation = sections[3];
                inventory.add(new Product(productId, productName, productPrice, productLocation));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {

        while (true) {

            // Display Inventory
            for (Product product : inventory) {
                System.out.println(product);
            }
            System.out.println("--- --- --- --- --- --- ---");
            System.out.println();

            System.out.print("Enter product ID (or X to return): ");
            String id = scanner.nextLine();

            System.out.println("--- --- --- --- --- --- ---");

            if (id.equalsIgnoreCase("X")) {
                break;
            }

            // find the selected product in the inventory
            Product selectedProduct = findProductById(id, inventory);

            if (selectedProduct != null) {
                cart.add(selectedProduct);
                System.out.println(selectedProduct.getProductName() + " added to cart.");
            } else {
                System.out.println("Product not found.");
            }

        }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        // Starter Value for Total Cost
        double totalCost = 0.0;

        for (Product product : cart) {
            System.out.println(product);

            totalCost += product.getProductPrice();
        }
        System.out.println("Total Cost: $" + totalCost);

        System.out.println("---------------------");

        System.out.println("Enter C to checkout or X to return: ");
        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("C")) {
            checkOut(cart, totalCost, scanner);
        } else if (option.equalsIgnoreCase("X")) {
            return;
        }
    }

    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {

        // check if user want to continue with check out
        System.out.print("Are you ready to checkout(Y/N): ");
        String choose = scanner.nextLine();

        if (choose.equalsIgnoreCase("Y")) {
            System.out.println("Total Amount: $" + totalAmount);

            System.out.println("Which Payment Method:");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit Card");
            System.out.println();


            System.out.print("Choose a Payment Method: ");
            String paymentChoice = scanner.nextLine();
            String paymentMethod;

            switch (paymentChoice) {
                case "1":
                    paymentMethod = "Credit Card";
                    break;
                case "2":
                    paymentMethod = "Debit Card";
                    break;
                default:
                    System.out.println("Invalid payment method.");
                    return;
            }

            System.out.print("Enter Payment Amount: $");
            double payment = scanner.nextDouble();
            scanner.nextLine();

            // if user enters an amount less than the total cost
            while (payment < totalAmount) {
                System.out.printf("Not enough money. Please enter at least $%.2f%n", totalAmount);

                // reprompt for payment
                System.out.print("Enter Payment Amount: $");
                payment = scanner.nextDouble();
            }

            scanner.nextLine();

            double change = payment - totalAmount;

            System.out.println("\n----- RECEIPT -----");
            for (Product product : cart) {
                System.out.println(product);
            }

            System.out.println("-------------------");
            System.out.println("Payment Method: " + paymentMethod);
            System.out.printf("Total: $%.2f%n", totalAmount);
            System.out.printf("Paid: $%.2f%n", payment);
            System.out.printf("Change: $%.2f%n", change);

            cart.clear();

            System.out.println("Thank you for your purchase!");
        } else {
            System.out.println("Checkout cancelled.");

            System.out.println("1. Return to Cart");
            System.out.println("2. Return to Home Screen");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            if (option.equals("1")) {
                displayCart(cart, scanner);
            }
        }
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {

        // Find the product
        for (Product product : inventory) {
            if (product.getProductId().equalsIgnoreCase(id)){
                return product;
            }
        }
        return null;
    }
}
