package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {

    public static void main(String[] args) {

        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport",
                "postgres", "password");

        try {
            // get connection to database
            Connection connection = dcm.getConnection();

            // lets create a new customer by setting all the fields
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();
            customer.setFirstName("Steve");
            customer.setLastName("Jobs");
            customer.setEmail("Steve@gmail.com");
            customer.setPhone("(555) 555-6543");
            customer.setAddress("123 Main Street");
            customer.setCity("Mount Vernon");
            customer.setState("VA");
            customer.setZipCode("22121");

            customerDAO.create(customer);

            //test finding customer by id
            Customer customer1 = customerDAO.findById(10000);
            System.out.println(customer1.getFirstName() + " " + customer1.getLastName() + " " +
                    customer1.getEmail());

            //test to see if email updates
            customer1.setEmail("Steve@apple.com");
            customer1 = customerDAO.update(customer1);
            System.out.println(customer1.getFirstName() + " " + customer1.getLastName() + " " +
                    customer1.getEmail());

            //create new customer so we can test if delete works
            Customer dbCustomer = customerDAO.create(customer);
            System.out.println(dbCustomer);
            dbCustomer = customerDAO.findById(dbCustomer.getId());
            System.out.println(dbCustomer);
            dbCustomer.setEmail("sjobs@apple.com");
            dbCustomer = customerDAO.update(dbCustomer);
            System.out.println(dbCustomer);

            customerDAO.delete(dbCustomer.getId());

            //test orderDAO to see if that is working
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            System.out.println(order);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}