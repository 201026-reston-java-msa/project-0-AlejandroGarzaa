package com.revature;

import java.util.Scanner;

import com.revature.repositories.CustomerDAO;
import com.revature.repositories.TransactionsDAO;

public class Driver {

    public static void main(String[] args) {

        CustomerDAO cd = new CustomerDAO();
        TransactionsDAO td = new TransactionsDAO();

        int initial;
        String email;
        String password;
        Scanner scan1 = new Scanner(System.in);

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
                System.out.println("Enter Username(email): ");
                scan1.nextLine();
                email = scan1.nextLine();
                if (cd.usercheck(email) == true) {
                    do {
                        System.out.println("Enter password: ");
                        password = scan1.nextLine();
                        if (cd.passcheck(email, password) == true) {
                            System.out.println("Access Granted");
                            /// return access level
                            if (cd.accesslevel(email, password) == 1) {
                                System.out.println("Customer");
                                if (cd.status(email, password) == true) {
                                    System.out.println("account active");
                                    td.custMenu(email, password);

                                } else {
                                    System.out.println("Account pending approval, check back later.");
                                }

                            }
                            if (cd.accesslevel(email, password) == 2) {
                                System.out.println("Employee");
                                td.empMenu();

                            }
                            if (cd.accesslevel(email, password) == 3) {
                                System.out.println("Admin");
                                td.adminMenu();

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
                System.out.println("Create account");
                cd.register();
                break;

            case 3:
                System.out.println("Thank you for banking with us!");
                scan1.close();
                break;

            default:
                System.out.println("Invalid option");
                break;
        }

    }

}
