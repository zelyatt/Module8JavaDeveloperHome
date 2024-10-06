package org.example;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class Demo {
    public static void main(String[] args) throws SQLException {
        CustomerService customerService = new CustomerService();

        long newCustomerId = customerService.create("John", "Doe", "john.doe@example.com", "123456789", "10000");
        System.out.println(newCustomerId);

        Customer customer = customerService.getById(newCustomerId);
        System.out.println(newCustomerId + ": " + customer);

        customerService.updateCustomer(newCustomerId, "Jonathan", "Doe", "jonathan.doe@example.com", "987654321", "20000");
        System.out.println("Клиент с ID " + newCustomerId + " обновлен");

        List<Customer> allCustomers = customerService.listAll();
        System.out.println("Все клиенты: " + allCustomers);

        customerService.deleteById(newCustomerId);
        System.out.println(newCustomerId);
    }
}
