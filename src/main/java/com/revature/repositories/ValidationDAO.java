package com.revature.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.revature.util.ConnectionUtil;

import org.apache.log4j.Logger;

// implementation for customer methods 
public class ValidationDAO {

    private static Logger log = Logger.getLogger(ValidationDAO.class);

    // method to find all customers in database (transaction)
    public boolean findAll() {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select customer.cust_id, customer.first_name, customer.last_name, customer.email, customer.phone, accounts.account_number, accounts.account_status, accounts.balance from customer right join accounts on customer.cust_id = accounts.cust_id order by customer.cust_id;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("cust_id");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String email = rs.getString("email");
                int phone = rs.getInt("phone");
                // int access = rs.getInt("access_level");
                int acctnum = rs.getInt("account_number");
                String status = rs.getString("account_status");
                int balance = rs.getInt("balance");

                // Customer c = new Customer(id, first_name, last_name, email, phone);
                System.out.println("------------------------");
                System.out.println("Id: " + id + "\nFirst Name: " + firstname + "\nLast Name: " + lastname + "\nEmail: "
                        + email + "\nPhone: " + phone + "\nAccount Number: " + acctnum + "\nAccount active: " + status
                        + "\nBalance: $" + balance);
            }
            rs.close();
            return true;

        } catch (SQLException e) {
            log.warn("Unable to retrieve customers from the db");
            return false;
        }

    }

    // mthod to regiter customer (validation)
    public void register() {

        String pass;
        String conpass;
        int access = 1;

        Scanner reg = new Scanner(System.in);

        System.out.println("------------------------");
        System.out.println("Enter First Name: ");
        String fname = reg.nextLine();
        System.out.println("------------------------");
        System.out.println("Enter Last Name: ");
        String lname = reg.nextLine();
        System.out.println("------------------------");
        System.out.println("Enetr Email: ");
        String email = reg.nextLine();
        System.out.println("------------------------");
        System.out.println("Enter Phone: ");
        int phone = reg.nextInt();
        reg.nextLine();

        do {
            System.out.println("------------------------");
            System.out.println("Create Password: ");
            pass = reg.nextLine();
            System.out.println("------------------------");
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
            System.out.println("------------------------");
            System.out.println("You are registered");
            log.info("new user registered");

        } catch (SQLException e) {
            System.out.println("Unable to create customer account");
            log.warn("Unable to create customer account");

        }

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select cust_id from customer where email = '" + email + "'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int id = rs.getInt("cust_id");
            String sql2 = "insert into accounts (cust_id,account_status,balance) values ('" + id + "', 'no', '0')";
            stmt.execute(sql2);
            stmt.close();
            System.out.println("Your Account has been created. Go to Login.");
            log.info("new user account created");

        } catch (SQLException e) {
            System.out.println("Unable to create customer account");
            log.warn("Unable to create customer account");

        }

    }

    // method to check username (validation)
    public boolean usercheck(String email) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select email from customer where email = '" + email + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            rs.getString("email");
            log.info("user validation successful");

            return true;

        } catch (SQLException e) {
            System.out.println("Unable to Login");
            log.warn("Unable to verify user login");

            return false;
        }

    }

    // method to validate password (validation)
    public boolean passcheck(String email, String password) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select passcode from customer where email = '" + email + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            String result = rs.getString("passcode");
            if (result.equals(password)) {
                log.info("password has been verified");
                return true;
            } else {
                log.info("wrong password was enterd");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Unable to Login");
            log.warn("Unable to verify password");

            return false;
        }

    }

    // method to determine access level (validation)
    public int accesslevel(String email, String password) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select access_level from customer where email = '" + email + "' and passcode = '" + password
                    + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int result = rs.getInt("access_level");
            log.info("access has been determined");
            return result;

        } catch (SQLException e) {
            System.out.println("Unable to Login");
            log.warn("unable to determine access");
            // e.printStackTrace();
            return 0;
        }
    }

    // method to check account status (validation)
    public boolean status(String email, String password) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select cust_id from customer where email = '" + email + "' and passcode = '" + password + "'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int id = rs.getInt("cust_id");
            String sql2 = "select account_status from accounts where cust_id = '" + id + "' and account_status = 'yes'";
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();
            rs2.getString("account_status");
            log.info("account status retrieved");
            return true;

        } catch (SQLException e) {
            System.out.println("Account approval pending");
            log.warn("status of account not active ");

            return false;
        }

    }

}
