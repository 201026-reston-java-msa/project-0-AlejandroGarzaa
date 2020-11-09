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

    // mthod to regiter customer
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
            System.out.println("You are registered");

        } catch (SQLException e) {
            System.out.println("Unable to create customer account");
            e.printStackTrace();
        }

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select cust_id from customer where email = '" + email +"'";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int id = rs.getInt("cust_id");
            String sql2 = "insert into accounts (cust_id,account_status,balance) values ('" + id + "', 'no', '0')";
            stmt.execute(sql2);
            stmt.close();
            System.out.println("Your Account has been created. Go to Login.");

        } catch (SQLException e) {
            System.out.println("Unable to create customer account");
            e.printStackTrace();
        }

    }

    // method to check username
    public boolean usercheck(String email) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select email from customer where email = '" + email + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            String result = rs.getString("email");
            System.out.println(result);
            return true;

        } catch (SQLException e) {
            System.out.println("Unable to Login");
            // e.printStackTrace();
            return false;
        }

    }

    // method to validate password
    public boolean passcheck(String email, String password) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select passcode from customer where email = '" + email + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            String result = rs.getString("passcode");
            if (result.equals(password)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Unable to Login");
            // e.printStackTrace();
            return false;
        }

    }
    
    // method to determine access level
    public int accesslevel(String email, String password){
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select access_level from customer where email = '"+ email +"' and passcode = '"+ password +"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int result = rs.getInt("access_level");
            return result;

        } catch (SQLException e) {
            System.out.println("Unable to Login");
            // e.printStackTrace();
            return 0;
        }
    }

    // method to check account status
    public boolean status(String email, String password){
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select cust_id from customer where email = '" + email +"' and passcode = '" + password +"'";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int id = rs.getInt("cust_id");
            String sql2 = "select account_status from accounts where cust_id = '"+ id +"'";
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();
            String stat = rs2.getString("account_status");
            stmt.close();

            if(stat.equalsIgnoreCase("yes")){
                return true;
            }else{
                return false;
            }
           
        } catch (SQLException e) {
            System.out.println("Unable to get access level");
            //e.printStackTrace();
            return false;
        }
    }



    }


