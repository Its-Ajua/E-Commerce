package org.example;

import java.sql.SQLException;
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
        System.out.print("Enter username: ");
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
        System.out.print("Enter username: ");
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
        while (true) {
            System.out.println("Product Menu:");
            System.out.println("1. Add Product | 2. Get All Products | 3. Get Product by ID | 4. Add Order | 5. Get All Orders | 6. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    getAllProducts();
                    break;
                case 3:
                    getProductById(scanner);
                    break;
                case 4:
                    addOrder(scanner, user);
                    break;
                case 5:
                    getAllOrders();
                    break;
                case 6:
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.next();
        scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter product description: ");
        scanner.nextLine();
        String description = scanner.nextLine();

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        try {
            ProductDAO productDAO = new ProductDAO();
            productDAO.addProduct(product);
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
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

    private static void addOrder(Scanner scanner, User user) {
        System.out.print("Enter total: ");
        double total = scanner.nextDouble();

        Order order = new Order();
        order.setUserId(user.getId());
        order.setTotal(total);

        try {
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.addOrder(order);
            System.out.println("Order added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding order: " + e.getMessage());
        }
    }

    private static void getAllOrders() {
        try {
            OrderDAO orderDAO = new OrderDAO();
            List<Order> orders = orderDAO.getAllOrders();

            System.out.println("All Orders:");
            for (Order order : orders) {
                System.out.println("ID: " + order.getId() + ", User ID: " + order.getUserId() + ", Total: " + order.getTotal());
            }
        } catch (SQLException e) {
            System.out.println("Error getting all orders: " + e.getMessage());
        }
    }
}
