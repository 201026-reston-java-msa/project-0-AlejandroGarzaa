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
            String sql2 = "select account_number, balance from accounts where cust_id = '" + id
                    + "' and account_status = 'yes'";
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

            String sql2 = "select balance from accounts where account_number = '" + accountnum
                    + "' and account_status = 'yes'";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();

            int accountbalance = rs2.getInt("balance");

            if (accountbalance > amount && amount > 0) {
                int newaccountbalance = accountbalance - amount;
                String sql = "update accounts set balance = '" + newaccountbalance + "' where account_number = '"
                        + accountnum + "' and account_status = 'yes'";
                stmt.execute(sql);
                System.out
                        .println("*****" + "\nAccount Number: " + accountnum + "\nPrevious Balance: " + accountbalance);
                System.out.println("*****" + "\nAccount Number: " + accountnum + "\nNew Balance: " + newaccountbalance);
            } else {
                System.out.println("unable to perform transaction");
            }

        } catch (SQLException e) {

            // e.printStackTrace();
            System.out.println("Unable to access account");
        }
    }

    // deposit method
    public void deposit(int daccountnum, int damount) {

        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql2 = "select balance from accounts where account_number = '" + daccountnum
                    + "' and account_status = 'yes'";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();

            int accountbalance = rs2.getInt("balance");

            if (damount > 0) {
                int newaccountbalance = accountbalance + damount;
                String sql = "update accounts set balance = '" + newaccountbalance + "' where account_number = '"
                        + daccountnum + "' and account_status = 'yes'";
                stmt.execute(sql);
                System.out.println(
                        "*****" + "\nAccount Number: " + daccountnum + "\nPrevious Balance: " + accountbalance);
                System.out
                        .println("*****" + "\nAccount Number: " + daccountnum + "\nNew Balance: " + newaccountbalance);
            } else {
                System.out.println("unable to perform transaction");
            }

        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("Unable to access account");
        }
    }

    // transfer method
    public void transfer(int fromacct, int toacct, int tamount) {

        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql2 = "select balance from accounts where account_number = '" + fromacct
                    + "' and account_status = 'yes'";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();

            int accountbalance = rs2.getInt("balance");

            if (accountbalance > tamount && tamount > 0) {
                int fromnewbalance = accountbalance - tamount;
                String sql = "update accounts set balance = '" + fromnewbalance + "' where account_number = '"
                        + fromacct + "' and account_status = 'yes'";
                stmt.execute(sql);
                System.out.println("*****" + "\nAccount Number: " + fromacct + "\nBalance: " + fromnewbalance);

                String sql3 = "select balance from accounts where account_number = '" + toacct
                        + "' and account_status = 'yes'";
                ResultSet rs3 = stmt.executeQuery(sql3);
                rs3.next();
                int tobalance = rs3.getInt("balance");
                int newtobalance = tobalance + tamount;
                String sql4 = "update accounts set balance = '" + newtobalance + "' where account_number = '" + toacct
                        + "' and account_status = 'yes'";
                stmt.execute(sql4);

            } else {
                System.out.println("unable to perform transaction");
            }

        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("Unable to access account");
        }
    }

    // apply for account
    public void apply(String email, String password) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "select cust_id from customer where email = '" + email + "' and passcode = '" + password + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int id = rs.getInt("cust_id");
            String sql2 = "insert into accounts (cust_id,account_status,balance) values ('" + id + "', 'no', '0')";
            stmt.execute(sql2);
            stmt.close();
            System.out.println("Your account is pending approval.");

        } catch (SQLException e) {
            System.out.println("Unable to create customer account");
            e.printStackTrace();
        }
    }

    // method to approve account (transaction)
    public void approveacct(int acctnum, String activate) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "update accounts set account_status = '" + activate + "' where account_number = '" + acctnum
                    + "'";

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Account number " + acctnum + " has been activated.");

        } catch (SQLException e) {
            System.out.println("Unable to get access level");
            e.printStackTrace();

        }
    }

    // cancel account (transaction)
    public void cancelacct(int acctnumb) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "delete from accounts where account_number = '" + acctnumb + "'";

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Account number " + acctnumb + " has been closed.");

        } catch (SQLException e) {
            System.out.println("Unable to close account.");
            e.printStackTrace();

        }

    }

}
