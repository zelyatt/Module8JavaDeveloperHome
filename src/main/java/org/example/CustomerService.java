package org.example;

import java.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public long create(String firstName, String lastName, String email, String telNumber, String postCode) throws SQLException {
        validateName(firstName);
        validateName(lastName);
        String insertSql = "INSERT INTO customers (first_name, last_name, email, tel_number, post_code) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, telNumber);
            preparedStatement.setString(5, postCode);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Failed to create customer");
                }
            }
        }
    }


    public Customer getById(long id) throws SQLException {
        String selectSql = "SELECT * FROM customers WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Customer(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString("tel_number"),
                            resultSet.getString("post_code")
                    );
                } else {
                    throw new SQLException("Customer with id " + id + " not found");
                }
            }
        }
    }

    public void updateCustomer(long id, String firstName, String lastName, String email, String telNumber, String postCode) throws SQLException {
        validateName(firstName);
        validateName(lastName);
        String updateSql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, tel_number = ?, post_code = ? WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, telNumber);
            preparedStatement.setString(5, postCode);
            preparedStatement.setLong(6, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update customer with id " + id);
            }
        }
    }

    public void deleteById(long id) throws SQLException {
        String deleteSql = "DELETE FROM customers WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Customer with id " + id + " not found");
            }
        }
    }

    public List<Customer> listAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String selectSql = "SELECT id, first_name, last_name, email, tel_number, post_code FROM customers";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String telNumber = resultSet.getString("tel_number");
                String postCode = resultSet.getString("post_code");

                customers.add(new Customer(id, firstName, lastName, email, telNumber, postCode));
            }
        }
        return customers;
    }

    private void validateName(String name) {
        if (name == null || name.length() < 3 || name.length() > 50) {
            throw new IllegalArgumentException("Name must be between 3 and 50 characters");
        }
    }
}
