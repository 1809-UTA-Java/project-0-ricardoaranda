package com.revature;

import java.util.Scanner;

import com.revature.repository.AccountDao;

public class Session {
	boolean isLoggedIn;
	boolean isAdmin;
	Scanner sc;
	final String userFileName = "accounts";
	final String employeeFileName = "employees";
	final String pendingTransactionsFileName = "pending-transactions";
	
	public Session(Scanner scanner) {
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
		Account a = null;
		AccountDao adao = new AccountDao();
		
		System.out.println("Log In: ");
		System.out.println("Enter your username: ");
		
		String username = sc.next();
		a = adao.getAccountByUserName(username);
		while (a == null) {
			System.out.println("This username does not exist, try again: ");
			username = sc.next();
			a = adao.getAccountByUserName(username);
		}
		
		if (!a.isActive) {
			System.out.println("This account has not been activated by an "
					+ "administrator. Try again later.\n");
			startProgram();
		}
		else {
			System.out.println("Enter your password: ");
			
			String password = sc.next();
			while (!a.getPassword().equals(password)) {
				System.out.println("Password does not match, try again: ");
				password = sc.next();
			}
			System.out.println();
			
			if (a.isAdmin) {
				AdminSession newSession = new AdminSession(sc, username);
				newSession.startAdminSession();
			}
			else {
				UserSession userSession = new UserSession(sc, username);
				userSession.startUserSession();
			}
		}
		
		
	}
	
	
	private void createAccount() {
		Account newAccount = null;
		AccountDao adao = new AccountDao();
		String username;

		System.out.println("Enter a username: ");
		username = sc.next();
		newAccount = adao.getAccountByUserName(username);
		while (newAccount != null) {
			System.out.println("That username already exists. Try another:");
			username = sc.next();
			newAccount = adao.getAccountByUserName(username);
		}
		newAccount = new UserAccount();
		
		newAccount.setUsername(username);

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
		newAccount.setActive(false);
		
		adao.createAccount(newAccount);
		
		System.out.println("Your request for a new account has been submitted.");
		System.out.println();
		
		startProgram();
	}
}