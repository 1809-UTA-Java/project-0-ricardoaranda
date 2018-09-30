package com.revature;

import java.util.Scanner;

public class Session  {
	boolean isLoggedIn;
	boolean isAdmin;
	final String dataFileName = "accounts.txt";
	
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
        		createAccount();
        		break;
        	case ("C"):
        		createAccount();
        		break;
        	default:
        		break;
        }

        sc.close();
    }
	
	private void login() {
		System.out.println("logging in...");
	}
	
	private void createAccount() {
		Scanner sc = new Scanner(System.in);
		
		Account newAccount = new UserAccount();
		
		System.out.println("Enter a username: ");
		newAccount.setUsername(sc.next());
		
		System.out.println("Enter a password: ");
		newAccount.setPassword(sc.next());
		
		System.out.println("Enter your first name: ");
		newAccount.setFirstName(sc.next());
		
		System.out.println("Enter your last name: ");
		newAccount.setLastName(sc.next());
		
		System.out.println("Enter would you like to create a checkings or savings? (type your answer)");
		while (!newAccount.setAccountType(sc.next())) {
			System.out.println("Not avalid response. Try again:");
		}
		
		newAccount.setAdmin(false);
		
		Database.writeObject(dataFileName, newAccount);
		
		sc.close();
	}
}
