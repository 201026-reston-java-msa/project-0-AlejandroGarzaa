package com.revature;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.revature.repositories.CustomerDAO;
import com.revature.util.ConnectionUtil;

//import jdk.internal.module.SystemModuleFinders;

public class Driver {

    public static void main(String[] args) {
        
        CustomerDAO cd = new CustomerDAO();
       // cd.usercheck();
       System.out.println(cd.passcheck("alejandro@gmail.com", "alejandropasswor"));
       //System.out.println(cd.myMethod(3));
       
    
    }
}
        //method to login to account

//         Scanner scan = new Scanner(System.in);
//        // String result;
//         String email;
//         System.out.println("***** Sign In *****");
//         do{
//         System.out.println("Enter username(email): ");
//         email = scan.nextLine();

//         try (Connection conn = ConnectionUtil.getConnection()) {
//             String sql = "select email from customer where email = '" + email + "'";
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql);
//             rs.next();
//            String result = rs.getString("email");

//         } catch (SQLException e) {
//             System.out.println("Unable to Login");
//             e.printStackTrace();
//         }
//         if(!email.equals(result)){
//             System.out.println("Username invalid, enter valid username");
//         }
//     }while(!email.equals(result));

//         scan.close();
        
//     }
// }

// Scanner log = new Scanner(System.in);
// //String pass;
// String user;
// String result;

// try (Connection conn = ConnectionUtil.getConnection()) {

// System.out.println("***** Sign In *****");

// do {
// System.out.println("Enter username(email): ");
// user = log.nextLine();

// String sql = "select email from customer where email = '" + user + "'";
// //String sqlpass = "select passcode from customer where email = '" + user +
// "'";
// Statement stmt = conn.createStatement();
// ResultSet rs = stmt.executeQuery(sql);
// rs.next();
// result = rs.getString("email");
// rs.close();
// //ResultSet rsp = stmt.executeQuery(sqlpass);
// //rsp.next();
// // String passdb = rsp.getString("passcode");

// if (!result.equals(user)) {

// System.out.println("Username does not exist, enter valid username: ");
// }
// // do {
// // System.out.println("Eneter password: ");
// // pass = log.nextLine();
// // if (!pass.equals(passdb)) {
// // System.out.println("Wrong password, re-enter password: ");
// // }
// // } while (!pass.equals(passdb));
// // }

// } while (!result.equals(user));

// System.out.println("user successful");

// } catch (SQLException e) {
// System.out.println("Unable to Login");
// e.printStackTrace();
// }

// log.close();
// }
