package com.revature.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

import com.revature.util.ConnectionUtil;

public class TransactionsDAO {
    private static Logger log = Logger.getLogger(TransactionsDAO.class);

    // check balance method
    public boolean balance(String email, String password)  {

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
                System.out.println("------------------------");
                System.out.println("Id: " + id + "\nAccount Number: " + accountnum + "\nBalance: $" + accountbalance);
            }
            stmt.close();
            log.info("Balance accessed successfully");
            return true;
           

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to get balance");
            log.warn("Unable to get balance");
            
            return false;
        }
    }

    // withdraw method

    public boolean withdraw(int accountnum, int amount) {

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
                System.out.println("------------------------");
                System.out.println("Account Number: " + accountnum + "\nPrevious Balance: " + accountbalance);

                System.out.println("------------------------");
                System.out.println("Account Number: " + accountnum + "\nNew Balance: " + newaccountbalance);
                log.info("Withdraw completed successfully");
            } else {
                System.out.println("------------------------");
                System.out.println("unable to perform transaction");
                log.warn("Unable to withdraw");
            }
            
            return true;

        } catch (SQLException e) {
            System.out.println("Unable to access account");
            log.warn("Unable to withdraw");
            return false;
        }
    }

    // deposit method
    public boolean deposit(int daccountnum, int damount) {

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
                System.out.println("------------------------");
                System.out.println("Account Number: " + daccountnum + "\nPrevious Balance: " + accountbalance);
                System.out.println("------------------------");
                System.out.println("Account Number: " + daccountnum + "\nNew Balance: " + newaccountbalance);
                log.info("Deposit executed successfully");
            } else {
                System.out.println("------------------------");
                System.out.println("unable to perform transaction");
                log.warn("Unable to make deposit");
            }
            
            return true;

        } catch (SQLException e) {
            System.out.println("Unable to access account");
            log.warn("Unable to make deposit");
            return false;
        }
    }

    // transfer method
    public boolean transfer(int fromacct, int toacct, int tamount) {

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
                System.out.println("------------------------");
                System.out.println("Account Number: " + fromacct + "\nBalance: " + fromnewbalance);

                String sql3 = "select balance from accounts where account_number = '" + toacct
                        + "' and account_status = 'yes'";
                ResultSet rs3 = stmt.executeQuery(sql3);
                rs3.next();
                int tobalance = rs3.getInt("balance");
                int newtobalance = tobalance + tamount;
                String sql4 = "update accounts set balance = '" + newtobalance + "' where account_number = '" + toacct
                        + "' and account_status = 'yes'";
                stmt.execute(sql4);
                log.info("Transfer executed successfully");

            } else {
                System.out.println("------------------------");
                System.out.println("unable to perform transaction");
                log.warn("Unable to make transfer");
            }
            return true;

        } catch (SQLException e) {

            System.out.println("Unable to access account");
            log.warn("Unable to make transfer");
            return false;
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
            System.out.println("------------------------");
            System.out.println("Your account is pending approval.");
            log.info("new account created, pending approval");

        } catch (SQLException e) {
            System.out.println("Unable to create customer account");
            log.warn("Unable execute apply method");
        }
    }

    // method to approve account (transaction)
    public void approveacct(int acctnum, String activate) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "update accounts set account_status = '" + activate + "' where account_number = '" + acctnum
                    + "'";

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("------------------------");
            System.out.println("Account number " + acctnum + " has been activated.");
            log.info("account approval successful");

        } catch (SQLException e) {
            System.out.println("Unable to approve account");
            log.warn("Unable to execute approve method");

        }
    }

    // cancel account (transaction)
    public void cancelacct(int acctnumb) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "delete from accounts where account_number = '" + acctnumb + "'";

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("------------------------");
            System.out.println("Account number " + acctnumb + " has been closed.");
            log.info("Account close executed");
            

        } catch (SQLException e) {
            System.out.println("Unable to close account.");
            log.warn("Unable to close account");
           

        }

    }

}
