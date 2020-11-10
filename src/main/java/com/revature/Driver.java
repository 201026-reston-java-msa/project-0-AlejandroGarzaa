package com.revature;

import com.revature.repositories.MenusDAO;
public class Driver {

    public static void main(String[] args) {

        MenusDAO menu = new MenusDAO();
        
        menu.mainMenu();
       

    }
}
