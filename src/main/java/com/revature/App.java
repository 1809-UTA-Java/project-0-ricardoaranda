package com.revature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {    	
    	initialize();
    	Scanner scanner = new Scanner(System.in);
    	
		Session session = new Session(scanner);
		session.startProgram();
		
		scanner.close();
    }
    
    public static void initialize() {
    	File usersPath = new File("accounts");
    	File employeesPath = new File("employees");
    	File transactionsPath = new File ("pending-transactions");
    	
    	if (!usersPath.exists()) {
    		try {
				usersPath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	if (!employeesPath.exists()) {
    		try {
    			employeesPath.createNewFile();
    			
    			/* create super admin */
    	    	ArrayList<Account> accountsList = new ArrayList<>();
    	    	AdminAccount superAdmin = new AdminAccount("superadmin", "asdf", true, true);
    	    	accountsList.add(superAdmin);
    	    	Database.writeObject("employees", superAdmin);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	if (!transactionsPath.exists()) {
    		try {
    			transactionsPath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
