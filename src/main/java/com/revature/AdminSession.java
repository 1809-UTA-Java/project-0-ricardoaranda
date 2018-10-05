package com.revature;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminSession {
	ArrayList <Account> accountsList;
	String fileName;
	String username;
	Scanner sc;
	int index;
	Account account;
	
	public AdminSession(Scanner scanner, String usernameArg) {
		fileName = "accounts";
		username = usernameArg;
		accountsList = Database.readAllObjects(fileName);
		sc = scanner;
		index = Database.getObjectIndex(fileName, username);
		account = accountsList.get(index);
	}
	
	public void startAdminSession() {
		System.out.println("Choose one of the options from the menu: ");
		System.out.println("1. View pending transactions. ");
		System.out.println("2. Approve open account.");
		System.out.println("3. (debug, will change to cancel) Delete account.");
		System.out.println("4. Log out.");
		System.out.println("5. (debug) Print all users.");
		System.out.println("6 . (debug) Print all employees.");
		System.out.println("7. (debug) Print all pending transactions.");
		
		String response = "";
		while (!sc.hasNext("[1234567]")) {
			System.out.println("Invalid input, try again");
			sc.next();
		}
		response = sc.next();
		System.out.println();
		
		switch (response) {
		case("1"):
			viewPendingTransactions();
			break;
		case("2"):
			approveOpenAccount();
			break;
		case("3"):
			deleteAccount();
			break;
		case("4"):
			Session session = new Session(sc);
			session.startProgram();
			break;
		case("5"):
			ArrayList<Account> accountsList = Database.readAllObjects("accounts");
			Database.printAllObjects(accountsList);
			startAdminSession();
			break;
		case("6"):
			ArrayList<Account> employeesList = Database.readAllObjects("employees");
			Database.printAllObjects(employeesList);
			startAdminSession();
			break;
		case("7"):
			ArrayList<Account> transactionsList = Database.readAllObjects("pending-transactions");
			Database.printAllObjects(transactionsList);
			startAdminSession();
			break;
		default:
			break;
		}
	}
	
	public void viewPendingTransactions() {
//		ArrayList
	}
	
	public void approveOpenAccount() {
		
	}
	
	public void deleteAccount() {
		ArrayList<Account> accountsList = Database.readAllObjects("accounts");
		boolean validInput = false;
		
		System.out.println("Type account name to delete: ");
		String username = sc.next();
		validInput = validateUsername(username, "accounts");
		while (!validInput) {
			System.out.println("This username does not exist, try again: ");
			username = sc.next();
			validInput = validateUsername(username, "accounts");
		}
		
		int index = Database.getObjectIndex("accounts", username);
		Account account = accountsList.get(index);
		accountsList.remove(index);
		
		Database.writeFromArrayList("accounts", accountsList);
		
		startAdminSession();
	}
	
	public boolean validateUsername(String input, String fileName) {
		ArrayList<Account> list = Database.readAllObjects(fileName);
		boolean validUsername = false;
		
		for (Account a : list) {
			if (a.getUsername() != null && a.getUsername().contains(input)) {
				validUsername = true;
			}
		}
		
		return validUsername;
	}
}
