package com.revature;

import com.revature.repositories.MenusDAO;


import org.postgresql.util.PSQLException;

public class Driver {
    public static void main(String[] args) throws PSQLException {
    MenusDAO menu = new MenusDAO();
       menu.mainMenu();

      


    }

       
}
