package com.revature.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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
            String sql2 = "select account_number, balance from accounts where cust_id = '" + id + "' and account_status = 'yes'";
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

            String sql2 = "select balance from accounts where account_number = '" + accountnum + "' and account_status = 'yes'";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();

            int accountbalance = rs2.getInt("balance");

            if (accountbalance > amount && amount > 0) {
                int newaccountbalance = accountbalance - amount;
                String sql = "update accounts set balance = '" + newaccountbalance + "' where account_number = '"
                        + accountnum + "' and account_status = 'yes'";
                stmt.execute(sql);
                System.out.println("*****"+ "\nAccount Number: "+ accountnum + "\nPrevious Balance: "+ accountbalance);
                System.out.println("*****"+ "\nAccount Number: "+ accountnum + "\nNew Balance: "+ newaccountbalance);
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

            String sql2 = "select balance from accounts where account_number = '" + daccountnum + "' and account_status = 'yes'";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();

            int accountbalance = rs2.getInt("balance");

            if (damount > 0) {
                int newaccountbalance = accountbalance + damount;
                String sql = "update accounts set balance = '" + newaccountbalance + "' where account_number = '"
                        + daccountnum + "' and account_status = 'yes'";
                stmt.execute(sql);
                System.out.println("*****"+ "\nAccount Number: "+ daccountnum + "\nPrevious Balance: "+ accountbalance);
                System.out.println("*****"+ "\nAccount Number: "+ daccountnum + "\nNew Balance: "+ newaccountbalance);
            } else {
                System.out.println("unable to perform transaction");
            }

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Unable to access account");
        }
    }

    // transfer method 
    public void transfer(int fromacct, int toacct, int tamount){

            try (Connection conn = ConnectionUtil.getConnection()) {
    
                String sql2 = "select balance from accounts where account_number = '" + fromacct + "' and account_status = 'yes'";
                Statement stmt = conn.createStatement();
                ResultSet rs2 = stmt.executeQuery(sql2);
                rs2.next();
    
                int accountbalance = rs2.getInt("balance");
    
                if (accountbalance > tamount && tamount > 0) {
                    int fromnewbalance = accountbalance - tamount;
                    String sql = "update accounts set balance = '" + fromnewbalance + "' where account_number = '"
                            + fromacct + "' and account_status = 'yes'";
                    stmt.execute(sql);
                    System.out.println("*****"+ "\nAccount Number: "+ fromacct + "\nBalance: "+ fromnewbalance);

                    String sql3 = "select balance from accounts where account_number = '" + toacct + "' and account_status = 'yes'";
                    ResultSet rs3 = stmt.executeQuery(sql3);
                    rs3.next();
                    int tobalance = rs3.getInt("balance");
                    int newtobalance = tobalance + tamount;
                    String sql4 = "update accounts set balance = '" + newtobalance + "' where account_number = '"
                            + toacct + "' and account_status = 'yes'";
                            stmt.execute(sql4);

                } else {
                    System.out.println("unable to perform transaction");
                }

            } catch (SQLException e) {
                //e.printStackTrace();
                System.out.println("Unable to access account");
            }
        }

            // apply for account
    public void apply(String email, String password){

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

        // customer menu
        public void custMenu(String email, String password){
            int select;

            do{
                System.out.println("Choose an option: ");
                System.out.println("1. Check balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Apply for another account");
                System.out.println("6. Exit");
    
                Scanner menuscan = new Scanner(System.in);
                select = menuscan.nextInt();
            

            switch(select){
                case 1:
                balance(email, password);
                break;

                case 2:
                System.out.println("Withdraw from account #: ");
                int accountnum = menuscan.nextInt();
                System.out.println("Withdraw Amount: ");
                menuscan.nextLine();
                int amount = menuscan.nextInt();
                withdraw(accountnum, amount);
                break;

                case 3:
                System.out.println("Deposit to account #: ");
                int daccountnum = menuscan.nextInt();
                System.out.println("Deposit amount: ");
                int damount = menuscan.nextInt();
                deposit(daccountnum, damount);
                break;

                case 4:
                System.out.println("From account #: ");
                int fromacct = menuscan.nextInt();

                System.out.println("To account #: ");
                int toacct = menuscan.nextInt();

                System.out.println("Amount to transfer: ");
                int tamount = menuscan.nextInt();

                transfer(fromacct, toacct, tamount);
                break;

                case 5:
                apply(email, password);
                break;

                case 6:
                menuscan.close();
                break;
                //System.out.println("Bye");

            }
        }while(select != 6);
    }

    // employee menu

            // employee menu
            public void empMenu(){
                CustomerDAO cd2 = new CustomerDAO();

                int select;
    
                do{
                    System.out.println("Choose an option: ");
                    System.out.println("1. View all accounts");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Deposit");
                    System.out.println("4. Transfer");
                    System.out.println("5. Approve account");
                    System.out.println("6. Exit");
        
                    Scanner menuscan = new Scanner(System.in);
                    select = menuscan.nextInt();
                
    
                switch(select){
                    case 1:
                    cd2.findAll();
                    break;
    
                    case 2:
                    System.out.println("Withdraw from account #: ");
                    int accountnum = menuscan.nextInt();
                    System.out.println("Withdraw Amount: ");
                    menuscan.nextLine();
                    int amount = menuscan.nextInt();
                    withdraw(accountnum, amount);
                    break;
    
                    case 3:
                    System.out.println("Deposit to account #: ");
                    int daccountnum = menuscan.nextInt();
                    System.out.println("Deposit amount: ");
                    int damount = menuscan.nextInt();
                    deposit(daccountnum, damount);
                    break;
    
                    case 4:
                    System.out.println("From account #: ");
                    int fromacct = menuscan.nextInt();
    
                    System.out.println("To account #: ");
                    int toacct = menuscan.nextInt();
    
                    System.out.println("Amount to transfer: ");
                    int tamount = menuscan.nextInt();
    
                    transfer(fromacct, toacct, tamount);
                    break;
    
                    case 5:
                    System.out.println("Approve account #: ");
                    int acctnum = menuscan.nextInt();
                    String activate = "yes";
                    cd2.approveacct(acctnum, activate);
                    System.out.println("You have approved account #: "+ acctnum);
                    break;
    
                    case 6:
                    menuscan.close();
                    break;
                    //System.out.println("Bye");
    
                }
            }while(select != 6);
        }

        // admin menu
        public void adminMenu(){
            CustomerDAO cd2 = new CustomerDAO();

            int select;

            do{
                System.out.println("Choose an option: ");
                System.out.println("1. View all accounts");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Approve account");
                System.out.println("6. Close account");
                System.out.println("7. Exit");
    
                Scanner menuscan = new Scanner(System.in);
                select = menuscan.nextInt();
            

            switch(select){
                case 1:
                cd2.findAll();
                break;

                case 2:
                System.out.println("Withdraw from account #: ");
                int accountnum = menuscan.nextInt();
                System.out.println("Withdraw Amount: ");
                menuscan.nextLine();
                int amount = menuscan.nextInt();
                withdraw(accountnum, amount);
                break;

                case 3:
                System.out.println("Deposit to account #: ");
                int daccountnum = menuscan.nextInt();
                System.out.println("Deposit amount: ");
                int damount = menuscan.nextInt();
                deposit(daccountnum, damount);
                break;

                case 4:
                System.out.println("From account #: ");
                int fromacct = menuscan.nextInt();

                System.out.println("To account #: ");
                int toacct = menuscan.nextInt();

                System.out.println("Amount to transfer: ");
                int tamount = menuscan.nextInt();

                transfer(fromacct, toacct, tamount);
                break;

                case 5:
                System.out.println("Approve account #: ");
                int acctnum = menuscan.nextInt();
                String activate = "yes";
                cd2.approveacct(acctnum, activate);
                System.out.println("You have approved account #: "+ acctnum);
                break;

                case 6:
                System.out.println("Close account #: ");
                int acctnumb = menuscan.nextInt();
                cd2.cancelacct(acctnumb);
                break;

                case 7:
                menuscan.close();
                break;
                //System.out.println("Bye");

            }
        }while(select != 7);
    }

}

