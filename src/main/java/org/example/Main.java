package org.example;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the E-Commerce Platform!");
        System.out.println("1. Login | 2. Signup | 3. Exit");

        while (true) {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    signup(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Login: Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                System.out.println("Login successful!");
                displayProductMenu(scanner, user);
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }
    }

    private static void signup(Scanner scanner) {
        System.out.print("Signup: Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        try {
            UserDAO userDAO = new UserDAO();
            User user = new User(username, password);
            userDAO.addUser(user);
            System.out.println("Signup successful!");
        } catch (SQLException e) {
            System.out.println("Error signing up: " + e.getMessage());
        }
    }

    private static void displayProductMenu(Scanner scanner, User user) {
        ShoppingCart<Product> cart = user.getCart();

        while (true) {
            System.out.println("Product Menu:");
            System.out.println("1. Add Product | 2. Get All Products | 3. Get Product by ID | 4. View Cart | 5. Checkout | 6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProduct(scanner, cart);
                    break;
                case 2:
                    getAllProducts();
                    break;
                case 3:
                    getProductById(scanner);
                    break;
                case 4:
                    viewCart(cart);
                    break;
                case 5:
                    checkout(scanner, user, cart);
                    break;
                case 6:
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProduct(Scanner scanner, ShoppingCart<Product> cart) {
        System.out.print("Enter product ID: ");
        long id = scanner.nextLong();

        try {
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductById(id);
            if (product != null) {
                cart.addItem(product);
                System.out.println("Product added to cart successfully!");
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
        }
    }

    private static void getAllProducts() {
        try {
            ProductDAO productDAO = new ProductDAO();
            List<Product> products = productDAO.getAllProducts();

            System.out.println("All Products:");
            for (Product product : products) {
                System.out.println("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Description: " + product.getDescription());
            }
        } catch (SQLException e) {
            System.out.println("Error getting all products: " + e.getMessage());
        }
    }

    private static void getProductById(Scanner scanner) {
        System.out.print("Enter product ID: ");
        long id = scanner.nextLong();

        try {
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductById(id);

            if (product != null) {
                System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Description: " + product.getDescription());
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error getting product by ID: " + e.getMessage());
        }
    }

    private static void viewCart(ShoppingCart<Product> cart) {
        System.out.println("Cart:");
        if (cart.getItems().size() == 0) {
            System.out.println("Cart Empty :(");
            System.out.println("Please Add Items To Your Cart");
        } else {
            for (Product product : cart.getItems()) {
                System.out.println("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice());
            }
        }
    }

    private static void checkout(Scanner scanner, User user, ShoppingCart<Product> cart) {
        if (cart.getItems().size() == 0) {
            System.out.println("Cart is empty. Please add items to your cart before checking out.");
            return;
        }
        System.out.print("Enter total: ");
        double total = scanner.nextDouble();

        Order order = new Order();
        order.setUserId(user.getId());
        order.setTotal(total);
        order.setTimestamp(LocalDateTime.now()); // set timestamp to current time
        order.setEstimatedDeliveryTime(Duration.ofHours(2)); // set estimated delivery time to 2 hours


        try {
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.addOrder(order);
            System.out.println("Order placed successfully!");
            System.out.println("Order details:");
            System.out.println("Products ordered:");
            for (Product product : cart.getItems()) {
                System.out.println("- " + product.getName());
            }
            Timestamp currentTimestamp = Timestamp.valueOf(order.getTimestamp());
            System.out.println("Timestamp: " + currentTimestamp);

            long days = java.time.Duration.between(currentTimestamp.toLocalDateTime(), currentTimestamp.toLocalDateTime().plusDays(5)).toDays();
            System.out.println("Estimated delivery time: " + days + " days");
        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
        cart.clear();
    }
}
