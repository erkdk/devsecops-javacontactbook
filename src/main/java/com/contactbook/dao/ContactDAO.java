package com.contactbook.dao;

import com.contactbook.model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/contactdb";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password123"; // Change to your password

    private static final String INSERT_CONTACT_SQL = "INSERT INTO contacts (name, email, phone) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_CONTACTS = "SELECT * FROM contacts";
    private static final String SELECT_CONTACT_BY_ID = "SELECT * FROM contacts WHERE id = ?";
    private static final String UPDATE_CONTACT_SQL = "UPDATE contacts SET name = ?, email = ?, phone = ? WHERE id = ?";
    private static final String DELETE_CONTACT_SQL = "DELETE FROM contacts WHERE id = ?";


public void insertContact(Contact contact) {
    System.out.println("DEBUG: inserting contact: " + contact.getName() + ", " + contact.getEmail() + ", " + contact.getPhone());
    try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
         PreparedStatement statement = connection.prepareStatement(INSERT_CONTACT_SQL)) {

        statement.setString(1, contact.getName());
        statement.setString(2, contact.getEmail());
        statement.setString(3, contact.getPhone());
        int rows = statement.executeUpdate();

        System.out.println("DEBUG: Insert affected rows: " + rows);
        if (rows == 0) {
            System.err.println("WARNING: Insert executed but 0 rows affected. Possible constraint violation.");
        }

    } catch (SQLException e) {
        System.err.println("ERROR inserting contact!");
        System.err.println("JDBC URL: " + jdbcURL);
        e.printStackTrace();
    }
}



    public List<Contact> selectAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CONTACTS);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                contacts.add(new Contact(id, name, email, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public Contact selectContactById(int id) {
        Contact contact = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(SELECT_CONTACT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                contact = new Contact(id, name, email, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }

    public boolean updateContact(Contact contact) {
        boolean rowUpdated = false;
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_SQL)) {
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getEmail());
            statement.setString(3, contact.getPhone());
            statement.setInt(4, contact.getId());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteContact(int id) {
        boolean rowDeleted = false;
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}

