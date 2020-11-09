package com.revature.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.util.ConnectionUtil;

public class TransactionsDAO {

    // check balance method
    public void balance(String email, String password) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select cust_id from customer where email = '" + email + "' and passcode = '" + password + "'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int id = rs.getInt("cust_id");
            String sql2 = "select account_number, balance from accounts where cust_id = '" + id + "'";
            ResultSet rs2 = stmt.executeQuery(sql2);
            while (rs2.next()) {

                int accountnum = rs2.getInt("account_number");
                int accountbalance = rs2.getInt("balance");

                System.out.println(
                        "*****" + "\nId: " + id + "\nAccount Number: " + accountnum + "\nBalance: $" + accountbalance);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Unable to get balance");
        }
    }

    // withdraw method

    public void withdraw(int accountnum, int amount) {

        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql2 = "select balance from accounts where account_number = '" + accountnum + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();

            int accountbalance = rs2.getInt("balance");

            if (accountbalance > amount && amount > 0) {
                accountbalance = accountbalance - amount;
                String sql = "update accounts set balance = '" + accountbalance + "' where account_number = '"
                        + accountnum + "'";
                stmt.execute(sql);
                System.out.println("*****"+ "\nAccount Number: "+ accountnum + "\nBalance: "+ accountbalance);
            } else {
                System.out.println("unable to perform transaction");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // System.out.println("Unable to get balance");
        }
    }

    // deposit method
    public void deposit(int accountnum, int amount) {

        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql2 = "select balance from accounts where account_number = '" + accountnum + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();

            int accountbalance = rs2.getInt("balance");

            if (amount > 0) {
                accountbalance = accountbalance + amount;
                String sql = "update accounts set balance = '" + accountbalance + "' where account_number = '"
                        + accountnum + "'";
                stmt.execute(sql);
                System.out.println("*****"+ "\nAccount Number: "+ accountnum + "\nBalance: "+ accountbalance);
            } else {
                System.out.println("unable to perform transaction");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // System.out.println("Unable to get balance");
        }
    }
}
