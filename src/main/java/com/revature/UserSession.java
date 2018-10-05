package com.revature;

import java.util.ArrayList;
import java.util.Scanner;

// TODO: implement linkAccounts()
public class UserSession {
	ArrayList <Account> accountsList;
	String fileName;
	String username;
	Scanner sc;
	int index;
	Account account;
	
	public UserSession(Scanner scanner, String usernameArg) {
		fileName = "accounts";
		username = usernameArg;
		accountsList = Database.readAllObjects(fileName);
		sc = scanner;
		index = Database.getObjectIndex(fileName, username);
		account = accountsList.get(index);
	}
	
	public void startUserSession() {
		System.out.println("Would you like to:");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Transfer");
		System.out.println("4. Link accounts");
		System.out.println("5. View account information");
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
		Database.writeFromArrayList(fileName, accountsList);
		
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
		Database.writeFromArrayList(fileName, accountsList);
		
		startUserSession();
	}
	
	public void transfer() {
		System.out.println("Type the username of the account you would like to transfer funds to: ");
		
		String recipientUsername = sc.next();
		boolean validInput = validateUsername(recipientUsername, "accounts");
		while(!validInput) {
			System.out.println("That account does not exist. Try again: ");
			recipientUsername = sc.next();
			validInput = validateUsername(recipientUsername, "accounts");
		}
		
		int recipientIndex = Database.getObjectIndex("accounts", recipientUsername);
		Account recipientAccount = accountsList.get(recipientIndex);
		
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
				
		System.out.println("Your new balance: " + account.getBalance());
		System.out.println();
		
		accountsList.set(recipientIndex, recipientAccount);
		accountsList.set(index, account);
		Database.writeFromArrayList("accounts", accountsList);
		
		startUserSession();
	}
	
	public void linkAccounts() {
		
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
