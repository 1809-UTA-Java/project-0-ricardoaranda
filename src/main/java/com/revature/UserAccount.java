package com.revature;

import java.util.UUID;

public class UserAccount extends Account implements UserActions {
	private static final long serialVersionUID = 1;

	public UserAccount() {
		super();
		this.username = "";
		this.password = "";
		this.isAdmin = false;
		this.isSuperAdmin = false;
		this.balance = 0;
	}

	public UserAccount(String firstName, String lastName, String username, String password, boolean isAdmin, boolean isSuperAdmin, long balance) {
		super(firstName, lastName, username, password, isAdmin, isSuperAdmin, balance);
		this.username = username;
		this.password = password;
		this.accountId = UUID.randomUUID();
		this.isAdmin = isAdmin;
		this.isSuperAdmin = isSuperAdmin;
		this.balance = balance;
	}
	
	/**
	 *  Methods overridden from UserActions interface
	 */
	@Override
	public boolean withdraw(long withdrawAmount) {
		long newBalance = balance - withdrawAmount;
		
		if (newBalance < 0) {
			System.out.println("Error. Cannot widthdraw without overdrafting.");
			return false;
		}
		else {
			balance = newBalance;
		}
		
		return true;
	}

	@Override
	public boolean deposit(long depositAmount) {
		balance += depositAmount;
		
		return true;
	}

	@Override
	public boolean transfer(long transferAmount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean joinAccounts() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}	
}