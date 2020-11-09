package com.revature.repositories;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.Scanner;


import com.revature.util.ConnectionUtil;

import org.apache.log4j.Logger;

// implementation for customer methods
public class CustomerDAO {
    private static Logger log = Logger.getLogger(CustomerDAO.class);

    // method to find all customers in database
    public void findAll() {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select cust_id,first_name,last_name,email,phone,access_level from customer";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("cust_id");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String email = rs.getString("email");
                int phone = rs.getInt("phone");
                int access = rs.getInt("access_level");

                // Customer c = new Customer(id, first_name, last_name, email, phone);
                System.out.println("*****" + "\nId: " + id + "\nFirst Name: " + firstname + "\nLast Name: " + lastname
                        + "\nEmail: " + email + "\nPhone: " + phone + "Access level: " + access);
            }
            rs.close();

        } catch (SQLException e) {
            log.warn("Unable to retrieve customers from the db");
        }

    }

    public void register() {

        String pass;
        String conpass;
        int access = 1;

        Scanner reg = new Scanner(System.in);

        System.out.println("Enter First Name: ");
        String fname = reg.nextLine();
        System.out.println("Enter Last Name: ");
        String lname = reg.nextLine();
        System.out.println("Enetr Email: ");
        String email = reg.nextLine();
        System.out.println("Enter Phone: ");
        int phone = reg.nextInt();
        reg.nextLine();

        do {

            System.out.println("Create Password: ");
            pass = reg.nextLine();
            System.out.println("Confirm Password: ");
            conpass = reg.nextLine();

            if (!pass.equals(conpass)) {
                System.out.println("Passwords did not match please redo");
            }
        } while (!pass.equals(conpass));

        reg.close();

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "insert into customer(first_name,last_name,email,phone,passcode,access_level) values ('"
                    + fname + "','" + lname + "','" + email + "','" + phone + "','" + pass + "','" + access + "')";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            // ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Your Account has been created. Go to Login.");

        } catch (SQLException e) {
            System.out.println("Unable to create customer account");
            e.printStackTrace();
        }

    }
}
