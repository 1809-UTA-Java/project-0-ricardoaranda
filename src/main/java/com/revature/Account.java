package com.revature;

import java.io.Serializable;

public abstract class Account implements Serializable {
	enum AccountType {
		CHECKINGS, SAVINGS
	}
	
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected boolean isAdmin;
	protected boolean isSuperAdmin;
	protected AccountType accountType;
	protected int accountId;
	private static final long serialVersionUID = 1;

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

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

}
