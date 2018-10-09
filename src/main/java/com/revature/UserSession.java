package com.revature;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.repository.AccountDao;
import com.revature.repository.JoinAccountDao;

public class UserSession {
	// TODO: link accounts
	String username;
	Scanner sc;
	Account account;
	AccountDao adao;
	
	public UserSession(Scanner scanner, String usernameArg) {
		username = usernameArg;
		sc = scanner;
		adao = new AccountDao();
		account = adao.getAccountByUserName(username);
	}
	
	public void startUserSession() {
		System.out.println("Would you like to:");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Transfer");
		System.out.println("4. Link accounts");
		System.out.println("5. View account information");	// view linked accounts' info
		System.out.println("6. Log out");
		
		String response = "";
		while (!sc.hasNext("[123456]")) {
			System.out.println("Invalid input, try again");
			sc.next();
		}
		response = sc.next();
		System.out.println();
		
		switch (response) {
		case("1"):
			deposit();
			break;
		case("2"):
			withdraw();
			break;
		case("3"):
			transfer();
			break;
		case("4"):
			linkAccounts();
			break;
		case("5"):
			printAccountInformation();
			break;
		case("6"):
			Session session = new Session(sc);
			session.startProgram();
			break;
		default:
			break;
		}
	}
	
	public void deposit() {
		long ammount;
		
		do {
			System.out.println("Type the ammount you would like deposit: ");
			while (!sc.hasNextLong()) {
				sc.nextLong();
			}
			ammount = sc.nextLong();
		} while (!evaluateDeposit(ammount));
		
		account.setBalance(account.getBalance() + ammount);
		
		System.out.println(account.getFirstName() + "'s new balance: $" + account.getBalance() + '\n');
		adao.saveAccountState(account);
		
		startUserSession();
	}
	
	public void withdraw() {
		long ammount;
		
		do {
			System.out.println("Type the ammount you would like to withdraw: ");
			while (!sc.hasNextLong()) {
				sc.nextLong();
			}
			ammount = sc.nextLong();
		} while (!evaluateWithdraw(ammount, account.getBalance()));
		
		account.setBalance((account.getBalance() - ammount));
		System.out.println(account.getFirstName() + "'s new balance: $" + account.getBalance() + '\n');
		adao.saveAccountState(account);
		
		startUserSession();
	}
	
	public void transfer() {
		Account recipientAccount = null;
		System.out.println("Type the username of the account you would like to transfer funds to: ");
		
		String recipientUsername = sc.next();
		recipientAccount = adao.getAccountByUserName(recipientUsername);
		while(recipientAccount == null) {
			System.out.println("That account does not exist. Try again: ");
			recipientUsername = sc.next();
			recipientAccount = adao.getAccountByUserName(recipientUsername);
		}
		
		long ammount;
		do {
			System.out.println("How much would you like to transfer?");
			while (!sc.hasNextLong()) {
				sc.nextLong();
			}
			ammount = sc.nextLong();
		} while (!evaluateTransfer(ammount, account.getBalance()));
		
		account.setBalance(account.getBalance() - ammount);
		recipientAccount.setBalance(recipientAccount.getBalance() + ammount);
				
		System.out.println("Your new balance: " + account.getBalance() + '\n');
		
		adao.saveAccountState(account);
		adao.saveAccountState(recipientAccount);

		startUserSession();
	}
	
	public void linkAccounts() {
//		System.out.println("Type the username you would like your account to link to: ");
//		
//		String otherUsername = sc.next();
//		while (!validateUsername(otherUsername, "accounts")) {
//			System.out.println("That account does not exist, try again.");
//			otherUsername = sc.next();
//		}
//		System.out.println();
//		
//		int otherIndex = Database.getObjectIndex("accounts", otherUsername);
//		Account otherAccount = accountsList.get(otherIndex);
//		
//		ArrayList<String> link = new ArrayList<>();
//		link.add(account.getUsername());
//		link.add(otherAccount.getUsername());
//		ArrayList<ArrayList<String>> listOfLinks = Database.readAllObjects("join-accounts");
//		listOfLinks.add(link);
//		Database.writeFromArrayList("join-accounts", listOfLinks);
//		
//		startUserSession();
	}
	
	public void printAccountInformation() {
		System.out.println(account.toString());
		startUserSession();
	}
	
	public boolean evaluateDeposit(long ammount) {
		if (ammount < 0) {
			System.out.println("Your answer cannot be a negative number.");
			return false;
		}
		else if (ammount == 0) {
			System.out.println("The ammount must be greater than zero.");
			return false;
		}
		else if (ammount > 2000) {
			System.out.println("Cannot deposit more than $2000 within a day.");
			return false;
		}
		
		return true;
	}
	
	public boolean evaluateWithdraw(long ammount, long balance) {
		if (ammount < 0) {
			System.out.println("Your answer cannot be a negative number.");
			return false;
		}
		else if (ammount == 0) {
			System.out.println("The ammount must be greater than zero.");
			return false;
		}
		else if (ammount > 1000) {
			System.out.println("Cannot withdraw more than $1000 within a day.");
			return false;
		}
		else if ((balance - ammount) < 0) {
			System.out.println("You cannot withdraw that ammount without overdrafting");
			return false;
		}
		
		return true;
	}
	
	public boolean evaluateTransfer(long ammount, long balance) {
		if (ammount < 0) {
			System.out.println("Your answer cannot be a negative number.");
			return false;
		}
		else if (ammount == 0) {
			System.out.println("The ammount must be greater than zero.");
			return false;
		}
		else if ((balance - ammount) < 0) {
			System.out.println("You cannot transfer that ammount without overdrafting");
			return false;
		}
		
		return true;
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
