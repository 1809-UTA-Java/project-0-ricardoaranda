package com.revature;

import java.util.Scanner;

import com.revature.repository.AccountDao;

public class AdminSession {
	String username;
	Scanner sc;
	Account account;
	AccountDao adao;
	
	public AdminSession(Scanner scanner, String usernameArg) {
		username = usernameArg;
		sc = scanner;
		adao = new AccountDao();
		account = adao.getAccountByUserName(username);
	}
	
	public void startAdminSession() {
		System.out.println("Choose one of the options from the menu: ");
		System.out.println("1. View pending transactions. ");
		System.out.println("2. Approve open account.");
		System.out.println("3. (debug, will change to cancel) Delete account.");
		System.out.println("4. Log out.");
		System.out.println("5. View all users."); 		
		System.out.println("6. View all employees.");
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
			adao.viewPendingTransactions();
			startAdminSession();
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
			adao.viewAllUsers(0);
			startAdminSession();
			break;
		case("6"):
			adao.viewAllUsers(1);
			startAdminSession();
			break;
		case("7"):
			editAccounts();
			break;
		case("8"):
			/* viewJoinAccounts(); */
			break;
		default:
			break;
		}
	}
	
	public void approveOpenAccount() {
		Account a = new UserAccount();
		
		System.out.println("Type username to approve:");
		String username = sc.next();
		a = adao.getAccountByUserName(username);
		while(a == null) {
			System.out.println("This username does not exist, try again.");
			username = sc.next();
			a = adao.getAccountByUserName(username);
		}
		
		a.setActive(true);
		adao.saveAccountState(a);
		
		System.out.println("Approved " + a.getUsername() + ".\n");
		
		startAdminSession();
	}
	
	// TEST THIS
	public void deleteAccount() {
		Account a = new UserAccount();
		
		System.out.println("Type account username to delete: ");
		String username = sc.next();
		a = adao.getAccountByUserName(username);
		while (a == null) {
			System.out.println("This username does not exist, try again: ");
			username = sc.next();
			a = adao.getAccountByUserName(username);
		}
		
		adao.deleteAccount(a);
		
		startAdminSession();
	}
	
	public void editAccounts() {
		Account a = new UserAccount();
		
		System.out.println("Enter username of the account to edit: ");
		String username = sc.next();
		a = adao.getAccountByUserName(username);
		while (a == null) {
			System.out.println("This username does not exist, try again: ");
			username = sc.next();
			a = adao.getAccountByUserName(username);
		}
		
		if (a.getUsername().equals("superadmin")) {
			System.out.println("You cannot modify this account. \n");
		}
		else {
			System.out.println("Choose a field to edit: ");
			System.out.println("1. Username");
			System.out.println("2. Password");
			System.out.println("3. First name");
			System.out.println("4. Last name");
			System.out.println("5. Balance");
			System.out.println("6. Permissions");
			
			String response = "";
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
				a.setUsername(newUsername);
				break;
			case("2"):
				System.out.println("Enter new password: ");
				String newPassword = sc.next();
				a.setPassword(newPassword);
				break;
			case("3"):
				System.out.println("Enter new name: ");
				String newName = sc.next();
				a.setFirstName(newName);
				break;
			case("4"):
				System.out.println("Enter new last name:");
				String newLastName = sc.next();
				a.setLastName(newLastName);
				break;
			case("5"):
				System.out.println("Enter new balance:");
				long newBalance = sc.nextLong();
				a.setBalance(newBalance); 
				break;
			case("6"):
				a.setAdmin(!account.isAdmin());
				break;
			default:
				break;
			}
			adao.saveAccountState(a);
		}
		startAdminSession();
	}
}
