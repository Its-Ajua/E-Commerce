package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name_p"));
            product.setPrice(rs.getDouble("price"));
            product.setDescription(rs.getString("description"));
            products.add(product);
        }
        rs.close();
        stmt.close();
        return products;
    }

    public Product getProductById(long id) throws SQLException {
        Product product = null;
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE id = ?");
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name_p"));
            product.setPrice(rs.getDouble("price"));
            product.setDescription(rs.getString("description"));
        }

        rs.close();
        stmt.close();

        return product;
    }

    public void addProduct(Product product) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO products (name, price, description) VALUES (?, ?, ?)");
        stmt.setString(1, product.getName());
        stmt.setDouble(2, product.getPrice());
        stmt.setString(3, product.getDescription());
        stmt.executeUpdate();

        stmt.close();
    }
}