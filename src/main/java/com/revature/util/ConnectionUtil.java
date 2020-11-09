package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {
	
	private static Logger log = Logger.getLogger(ConnectionUtil.class);
	
	public static Connection getConnection() {
		

	
		
		Connection conn = null;
		Properties prop = new Properties();
		
		try {
			// this is more secure as you don't expose all your credentials
			prop.load(new FileReader("C:\\Users\\alejg\\Documents\\RevatureStack\\project-0-AlejandroGarzaa\\src\\main\\resources\\application.properties"));
			conn = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("username"),
					prop.getProperty("password")
                    );
                    log.info("successfull connection");
		} catch (SQLException e) {
			log.warn("Unable to obtain connection to database", e);
		} catch (FileNotFoundException e) {
			log.warn("Cannot locate properties file");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	

}