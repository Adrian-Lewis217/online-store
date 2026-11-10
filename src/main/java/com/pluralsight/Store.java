package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Learners will complete the TODO sections to make the program work.
 * <p>
 * What is already done for you: the two lists, the Home Screen loop and
 * the method headers. Each TODO says what that method has to do.
 * Product.java is complete; products.csv sits at the project root.
 * You will need extra imports (for example java.io.BufferedReader,
 * java.io.FileReader, java.io.IOException) as you fill the TODOs in.
 */
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

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    /**
     * Reads product data from a file and populates the inventory list.
     * File format (pipe-delimited), the first line is a header row:
     * SKU|Product Name|Price|Department
     * <p>
     * Example line:
     * AV1051|JBL Bluetooth Speaker|89.95|Audio Video
     */
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        // TODO: skip the header line, then read each line, split on "|",
        //       create a Product object, and add it to the inventory list

        // The store's inventory display
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String line;

            // skip the header line
            br.readLine();

            while ((line = br.readLine()) != null){

                String[] sections = line.split("\\|");

                if (sections.length != 4){
                    System.out.println("Invalid record: " + line);
                    continue;
                }

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

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        // TODO: show each product (id, name, price),
        //       prompt for an id, find that product, add to cart
        // Display the inventory, prompt for an id to find product and add it to cart

        // Display Inventory
        for (int i = 0; i < inventory.size(); i++) {
            Product product = inventory.get(i);

            System.out.println(product);
        }

        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();

        Product selectedProduct = findProductById(id, inventory);

        if (selectedProduct != null){
            cart.add(selectedProduct);
            System.out.println(selectedProduct.getProductName() + " added to cart.");
        } else {
            System.out.println("Product not found.");
        }

    }

    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)

        if (cart.isEmpty()){
            System.out.println("Your cart is empty.");
            return;
        }

        double totalCost = 0.0;

        for (Product product : cart) {
            System.out.println(product);

            totalCost += product.getProductPrice();
        }
        System.out.println("Total Cost: $" + totalCost);

        System.out.println("Enter C to checkout or X to return: ");
        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("C")){
            checkOut(cart, totalCost, scanner);
        }
    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        // TODO: implement steps listed above
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids

        // Find the product
        for (Product product : inventory) {
            if (product.getProductId().equalsIgnoreCase(id)){
                return product;
            }
        }
        return null;
    }
}
