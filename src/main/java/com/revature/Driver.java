package com.revature;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.revature.repositories.CustomerDAO;
import com.revature.repositories.TransactionsDAO;
import com.revature.util.ConnectionUtil;

//import jdk.internal.module.SystemModuleFinders;

public class Driver {

    public static void main(String[] args) {

        CustomerDAO cd = new CustomerDAO();
        TransactionsDAO td = new TransactionsDAO();
//td.empmenu("tom@gmail.com", "tompassword");
    }}

        // cd.usercheck("alejandro@gmail.com");

        // cd.usercheck();
        // System.out.println(cd.status("tom@gmail.com", "tompassword"));

        // td.balance("alejandro@gmail.com","alejandropassword");
        // td.transfer(1, 2, 100);
        // td.withdraw(4, 10000);
        // cd.approveacct(4, "yes");
        // cd.register();
        // cd.apply("tom@gmail.com", "tompassword");

        /////////////////////////////////////////////////////

        int initial;
        String email;
        String password;
        Scanner scan1 = new Scanner(System.in);
        do {

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
                                if(cd.accesslevel(email, password)==1){
                                    System.out.println("Customer");
                                    if(cd.status(email, password)== true){
                                        System.out.println("account active");
                                        td.empmenu(email, password);

                                    }else{
                                        System.out.println("Account pending approval, check back later.");
                                    }

                                }
                                if(cd.accesslevel(email, password)==2){
                                    System.out.println("Employee");

                                } if(cd.accesslevel(email, password)==3){
                                    System.out.println("Admin");

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
                    break;

                case 3:
                    System.out.println("Thank you for banking with us!");
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }

        } while (initial != 3);
        scan1.close();
    }

}
