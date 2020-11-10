package com.revature.repositories;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class MenusDAO {
    private static Logger log = Logger.getLogger(MenusDAO.class);
    TransactionsDAO td = new TransactionsDAO();

    // Main menu method
    public void mainMenu() {
        ValidationDAO cd = new ValidationDAO();

        int initial;
        String email;
        String password;
        Scanner scan1 = new Scanner(System.in);
        try {

            System.out.println("------------------------");
            System.out.println("Welcome to banking app");
            System.out.println("1. Login");
            System.out.println("2. Create account");
            System.out.println("3. Exit");
            System.out.println("------------------------");

            initial = scan1.nextInt();

            switch (initial) {
                case 1:
                    System.out.println("Login");
                    System.out.println("------------------------");
                    System.out.println("Enter Username(email): ");
                    scan1.nextLine();
                    email = scan1.nextLine();
                    if (cd.usercheck(email) == true) {
                        do {
                            System.out.println("------------------------");
                            System.out.println("Enter password: ");
                            password = scan1.nextLine();
                            if (cd.passcheck(email, password) == true) {
                                System.out.println("Access Granted");
                                /// return access level
                                if (cd.accesslevel(email, password) == 1) {
                                    System.out.println("Customer");
                                    if (cd.status(email, password) == true) {
                                        System.out.println("account active");
                                        custMenu(email, password);

                                    } else {
                                        System.out.println("Account pending approval, check back later.");
                                    }

                                }
                                if (cd.accesslevel(email, password) == 2) {
                                    System.out.println("Employee");
                                    empMenu();

                                }
                                if (cd.accesslevel(email, password) == 3) {
                                    System.out.println("Admin");
                                    adminMenu();

                                }

                            } else {
                                System.out.println("incorrect password");
                            }
                        } while (cd.passcheck(email, password) != true);
                    } else {
                        System.out.println("No username found");
                    }
                    break;

                case 2:
                    System.out.println("------------------------");
                    System.out.println("Create account");
                    cd.register();
                    break;

                case 3:
                    System.out.println("------------------------");
                    System.out.println("Thank you for banking with us!");
                    scan1.close();
                    break;

            }

        } catch (InputMismatchException e) {
            log.error("InputMismatch Stack Trace: ", e);
        }

    }

    // customer menu
    public void custMenu(String email, String password) {
        int select;

        do {
            System.out.println("------------------------");
            System.out.println("Choose an option: ");
            System.out.println("1. Check balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Apply for another account");
            System.out.println("6. Exit");

            Scanner menuscan = new Scanner(System.in);
            select = menuscan.nextInt();

            switch (select) {
                case 1:
                    td.balance(email, password);
                    break;

                case 2:
                    System.out.println("------------------------");
                    System.out.println("Withdraw from account #: ");
                    int accountnum = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Withdraw Amount: ");
                    menuscan.nextLine();
                    int amount = menuscan.nextInt();
                    td.withdraw(accountnum, amount);
                    break;

                case 3:
                    System.out.println("------------------------");
                    System.out.println("Deposit to account #: ");
                    int daccountnum = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Deposit amount: ");
                    int damount = menuscan.nextInt();
                    td.deposit(daccountnum, damount);
                    break;

                case 4:
                    System.out.println("------------------------");
                    System.out.println("From account #: ");
                    int fromacct = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("To account #: ");
                    int toacct = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Amount to transfer: ");
                    int tamount = menuscan.nextInt();

                    td.transfer(fromacct, toacct, tamount);
                    break;

                case 5:
                    td.apply(email, password);
                    break;

                case 6:
                    menuscan.close();
                    System.out.println("Thank you for banking with us!");
                    break;

            }
        } while (select != 6);
    }

    // employee menu
    public void empMenu() {
        ValidationDAO cd2 = new ValidationDAO();

        int select;

        do {
            System.out.println("------------------------");
            System.out.println("Choose an option: ");
            System.out.println("1. View all accounts");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Approve account");
            System.out.println("6. Exit");

            Scanner menuscan = new Scanner(System.in);
            select = menuscan.nextInt();

            switch (select) {
                case 1:
                    cd2.findAll();
                    break;

                case 2:
                    System.out.println("------------------------");
                    System.out.println("Withdraw from account #: ");
                    int accountnum = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Withdraw Amount: ");
                    menuscan.nextLine();
                    int amount = menuscan.nextInt();
                    td.withdraw(accountnum, amount);
                    break;

                case 3:
                    System.out.println("------------------------");
                    System.out.println("Deposit to account #: ");
                    int daccountnum = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Deposit amount: ");
                    int damount = menuscan.nextInt();
                    td.deposit(daccountnum, damount);
                    break;

                case 4:
                    System.out.println("------------------------");
                    System.out.println("From account #: ");
                    int fromacct = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("To account #: ");
                    int toacct = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Amount to transfer: ");
                    int tamount = menuscan.nextInt();

                    td.transfer(fromacct, toacct, tamount);
                    break;

                case 5:
                    System.out.println("------------------------");
                    System.out.println("Approve account #: ");
                    int acctnum = menuscan.nextInt();
                    String activate = "yes";
                    td.approveacct(acctnum, activate);
                    System.out.println("------------------------");
                    System.out.println("You have approved account #: " + acctnum);
                    break;

                case 6:
                    menuscan.close();
                    System.out.println("Thank you for banking with us!");
                    break;

            }
        } while (select != 6);
    }

    // admin menu
    public void adminMenu() {
        ValidationDAO cd2 = new ValidationDAO();

        int select;

        do {
            System.out.println("------------------------");
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

            switch (select) {
                case 1:
                    cd2.findAll();
                    break;

                case 2:
                    System.out.println("------------------------");
                    System.out.println("Withdraw from account #: ");
                    int accountnum = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Withdraw Amount: ");
                    menuscan.nextLine();
                    int amount = menuscan.nextInt();
                    td.withdraw(accountnum, amount);
                    break;

                case 3:
                    System.out.println("------------------------");
                    System.out.println("Deposit to account #: ");
                    int daccountnum = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Deposit amount: ");
                    int damount = menuscan.nextInt();
                    td.deposit(daccountnum, damount);
                    break;

                case 4:
                    System.out.println("------------------------");
                    System.out.println("From account #: ");
                    int fromacct = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("To account #: ");
                    int toacct = menuscan.nextInt();
                    System.out.println("------------------------");
                    System.out.println("Amount to transfer: ");
                    int tamount = menuscan.nextInt();

                    td.transfer(fromacct, toacct, tamount);
                    break;

                case 5:
                    System.out.println("------------------------");
                    System.out.println("Approve account #: ");
                    int acctnum = menuscan.nextInt();
                    String activate = "yes";
                    td.approveacct(acctnum, activate);
                    System.out.println("------------------------");
                    System.out.println("You have approved account #: " + acctnum);
                    break;

                case 6:
                    System.out.println("------------------------");
                    System.out.println("Close account #: ");
                    int acctnumb = menuscan.nextInt();
                    td.cancelacct(acctnumb);
                    break;

                case 7:
                    menuscan.close();
                    System.out.println("Thank you for banking with us!");
                    break;

            }
        } while (select != 7);
    }

}
