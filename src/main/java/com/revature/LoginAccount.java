package com.revature;

import java.io.FileOutputStream;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class LoginAccount  {
	boolean isLoggedIn;
	boolean isAdmin;
	String dataFileName = "accounts.txt";
	
	void startProgram() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Welcome!");
        System.out.println("Enter 'L' to log in or 'C' to create an account: ");

        while (!sc.hasNext("[LlCc]")) {
            System.out.println("Invalid input.\n");
            System.out.println("Please enter 'L' to log in or 'C' to create an account: ");
            sc.next();
        }

        String response = sc.next();
        switch(response) {
        	case("l"):
        		login();
        		break;
        	case("L"):
        		login();
        		break;
        	case ("c"):
        		createAccount(new UserAccount());
        		break;
        	case ("C"):
        		createAccount(new UserAccount());
        		break;
        	default:
        		break;
        }

        sc.close();
    }
	
	private void login() {
		System.out.println("logging in...");
	}
	
	private void createAccount(Object obj) {
		Scanner sc = new Scanner(System.in);
		
		try (ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (dataFileName))) {
			UserAccount newAccount = (UserAccount) obj;
						
			System.out.println("Enter a username: ");
			newAccount.setUsername(sc.next().toString());
	                    
			System.out.println("Enter a password: ");
			newAccount.setPassword(sc.next().toString());
			
			oos.writeObject(newAccount);
	        			
			System.out.println("Username: " + newAccount.getUsername() + ". Password: " + newAccount.getPassword());
	        			
			sc.close();
	        			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
