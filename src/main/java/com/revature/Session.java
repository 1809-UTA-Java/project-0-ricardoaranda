package com.revature;

import java.util.ArrayList;
import java.util.Scanner;

public class Session {
	boolean isLoggedIn;
	boolean isAdmin;
	Scanner sc;
	final String fileName = "accounts";
	
	public Session(ArrayList<Account> objList, Scanner scanner) {
		sc = scanner;
		isLoggedIn = false;
		isAdmin = false;
	}
	
	void startProgram() {
        System.out.println("Welcome!");
        System.out.println("Choose one of the options from the menu: ");
        System.out.println("1. Log in.");
        System.out.println("2. Create a new account.");
        System.out.println("3. Exit.");
        
        while (!sc.hasNext("[123]")) {
            System.out.println("Invalid input, try again.");
            sc.next();
        }
        System.out.println();

        String response = sc.next();
        switch(response) {
        	case("1"):
        		login();
        		break;
        	case ("2"):
        		createAccount();
        		break;
        	default:
        		break;
        }
    }
	
	private void login() {
		boolean validInput = false;
		
		System.out.println("Log In: ");
		System.out.println("Enter your username: ");
		
		String username = sc.next();
		validInput = validateUsername(username);
		while (!validInput) {
			System.out.println("This username does not exist, try again: ");
			username = sc.next();
			validInput = validateUsername(username);
		}
		
		System.out.println("Enter your password: ");
		
		String password = sc.next();
		validInput = false;
		validInput = validatePassword(username, password);
		while (!validInput) {
			System.out.println("Password does not match, try again: ");
			password = sc.next();
			validInput = validatePassword(username, password);
		}
		System.out.println();
		
		UserSession userSession = new UserSession(sc, username);
		userSession.startUserSession();
	}
	
	private void createAccount() {
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
		
		Database.writeObject("pending-transactions", newAccount);
		
		System.out.println("Your request for a new account has been submitted.");
	}
	
	public boolean validateUsername(String input) {
		ArrayList<Account> list = Database.readAllObjects(fileName);
		boolean validUsername = false;
		
		for (Account a : list) {
			if (a.getUsername() != null && a.getUsername().contains(input)) {
				validUsername = true;
			}
		}
		
		return validUsername;
	}
	
	public boolean validatePassword(String username, String password) {
		ArrayList<Account> list = Database.readAllObjects(fileName);
		int index = Database.getObjectIndex(fileName, username);
		if (list.get(index).getPassword().equals(password))
			return true;
		
		return false;
	}
}