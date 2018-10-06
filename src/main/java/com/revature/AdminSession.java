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
		fileName = "employees";
		username = usernameArg;
		accountsList = Database.readAllObjects("employees");
		sc = scanner;
		index = Database.getObjectIndex("employees", username);
		account = accountsList.get(index);
	}
	
	public void startAdminSession() {
		System.out.println("Choose one of the options from the menu: ");
		System.out.println("1. View pending transactions. ");
		System.out.println("2. Approve open account.");
		System.out.println("3. (debug, will change to cancel) Delete account.");
		System.out.println("4. Log out.");
		System.out.println("5. View all users.");
		System.out.println("6. (debug) View all employees.");
		System.out.println("7. Edit accounts.");
		System.out.println("8. View join accounts.");
		
		String response = "";
		while (!sc.hasNext("[12345678]")) {
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
			editAccounts();
			break;
		case("8"):
			/* viewJoinAccounts(); */
			Database.printJoinAccounts();
			break;
		default:
			break;
		}
	}
	
	public void viewPendingTransactions() {
		ArrayList<Account> transactionsList = Database.readAllObjects("pending-transactions");
		Database.printAllObjects(transactionsList);
		startAdminSession();
	}
	
	public void approveOpenAccount() {
		String fileName;
		boolean validInput = false;
		ArrayList<Account> list = Database.readAllObjects("pending-transactions");
		
		System.out.println("Type username to approve:");
		String username = sc.next();
		validInput = validateUsername(username, "pending-transactions");
		while(!validInput) {
			System.out.println("This username does not exist, try again.");
			username = sc.next();
			validInput = validateUsername(username, "pending-transactions");
		}
		
		int index = Database.getObjectIndex("pending-transactions", username);
		Account newAccount = list.get(index);
		list.remove(index);
		Database.writeFromArrayList("pending-transactions", list);
		
		Database.writeObject("accounts", newAccount);
		
		startAdminSession();
	}
	
	public void deleteAccount() {
		String fileName;
		System.out.println("Choose database to access: ");
		System.out.println("1. Accounts");
		System.out.println("2. Pending Transactions");
		
		String response = "";
		while (!sc.hasNext("[12]")) {
			System.out.println("Invalid input, try again");
			sc.next();
		}
		response = sc.next();
		System.out.println();
		
		switch(response) {
		case("1"):
			fileName = "accounts";
			break;
		case("2"):
			fileName = "pending-transactions";
			break;
		default:
			fileName = "pending-transactions";
			break;
		}
		
		ArrayList<Account> list = Database.readAllObjects(fileName);
		
		boolean validInput = false;
		System.out.println("Type account name to delete: ");
		String username = sc.next();
		validInput = validateUsername(username, fileName);
		while (!validInput) {
			System.out.println("This username does not exist, try again: ");
			username = sc.next();
			validInput = validateUsername(username, fileName);
		}
		
		int index = Database.getObjectIndex(fileName, username);
		Account account = list.get(index);
		list.remove(index);
		
		Database.writeFromArrayList(fileName, list);
		
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
	
	public void editAccounts() {
		String fileName;
		System.out.println("Choose database to access: ");
		System.out.println("1. Accounts");
		System.out.println("2. Employees");
		
		String response = "";
		while (!sc.hasNext("[12]")) {
			System.out.println("Invalid input, try again");
			sc.next();
		}
		response = sc.next();
		System.out.println();
		
		switch(response) {
		case("1"):
			fileName = "accounts";
			break;
		case("2"):
			fileName = "employees";
			break;
		default:
			fileName = "";
			break;
		}
		ArrayList<Account> list = Database.readAllObjects(fileName);
		
		boolean validInput = false;
		System.out.println("Enter username of the account to edit: ");
		String username = sc.next();
		validInput = validateUsername(username, fileName);
		while (!validInput) {
			System.out.println("This username does not exist, try again: ");
			username = sc.next();
			validInput = validateUsername(username, fileName);
		}
		
		int index = Database.getObjectIndex(fileName, username);
		Account account = list.get(index);
		
		if (account.getUsername().equals("superadmin")) {
			System.out.println("You cannot modify this account. \n");
		}
		else {
			System.out.println("Choose a field to edit: ");
			System.out.println("1. Username");
			System.out.println("2. Password");
			System.out.println("3. First name");
			System.out.println("4. Last name");
			System.out.println("5. Balance");
			System.out.println("6. Permission");
			
			while(!sc.hasNext("[12345]")) {
				System.out.println("Invalid input, try again");
				sc.next();
			}
			response = sc.next();
			System.out.println();
			
			switch (response) {
			case("1"):
				System.out.println("Enter new username: ");
				String newUsername = sc.next();
				account.setUsername(newUsername);
				break;
			case("2"):
				System.out.println("Enter new password: ");
				String newPassword = sc.next();
				account.setPassword(newPassword);
				break;
			case("3"):
				System.out.println("Enter new name: ");
				String newName = sc.next();
				account.setFirstName(newName);
				break;
			case("4"):
				System.out.println("Enter new las name:");
				String newLastName = sc.next();
				account.setLastName(newLastName);
				break;
			case("5"):
				System.out.println("Enter new balance:");
				long newBalance = sc.nextLong();
				account.setBalance(newBalance); 
				break;
			case("6"):
				account.setAdmin(!account.isAdmin());
				break;
			default:
				break;
			}
			list.set(index, account);
			Database.writeFromArrayList(fileName, list);
		}
		startAdminSession();
	}
}
