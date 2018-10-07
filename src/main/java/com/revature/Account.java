package com.revature;

import java.io.Serializable;
import java.util.UUID;
import java.util.ArrayList;

public abstract class Account implements Serializable {
	public enum AccountType {
		CHECKINGS, SAVINGS
	}
	
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected boolean isAdmin;
	protected boolean isSuperAdmin;
	protected long balance;
	protected UUID accountId;
	protected AccountType accountType;
	private static final long serialVersionUID = 1;

	public Account() {
		this.username = "";
		this.password = "";
		this.firstName = "";
		this.lastName = "";
		this.isAdmin = false;
		this.isSuperAdmin = false;
		this.balance = 0;
		this.accountId = UUID.randomUUID();
		this.accountType = AccountType.CHECKINGS;
		
	}
	
	public Account(String firstName, String lastName, String username, String password, boolean isAdmin, boolean isSuperAdmin, long balance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.accountId = UUID.randomUUID();
		this.accountType = AccountType.CHECKINGS;
		this.isAdmin = isAdmin;
		this.isSuperAdmin = isSuperAdmin;
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public boolean setAccountType(String type) {
		type.toLowerCase();
		if (type.equals("checkings") || type.equals("checking")) {
			accountType = AccountType.CHECKINGS;
			return true;
		}
		else if (type.equals("savings") || type.equals("saving")) {
			accountType = AccountType.SAVINGS;
			return true;
		}
		else 
			return false;
	}

	public UUID getAccountId() {
		return accountId;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [username = " + username + ", password = " + password + ", firstName = " + firstName + ", lastName = "
				+ lastName + ", balance = $" + balance + ", accountType = " + accountType + ", accountId = " + accountId + "]\n";
	}
}