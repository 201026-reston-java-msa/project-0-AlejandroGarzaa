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
       // cd.usercheck();
      // System.out.println(cd.status("tom@gmail.com", "tompassword"));
       
       //td.balance("alejandro@gmail.com","alejandropassword");
       //td.transfer(1, 2, 100);
       //td.withdraw(4, 10000);
       //cd.approveacct(4, "yes");
       //cd.register();
      // cd.apply("tom@gmail.com", "tompassword");
    
    }
}
