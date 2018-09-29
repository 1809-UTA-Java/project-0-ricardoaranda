package com.revature;

import java.io.Serializable;

public class UserAccount implements UserActions, Serializable {
	private String firstName, lastName, username, password;
	private boolean isAdmin;
	private long funds;
	private static final long serialVersionUID = 1;

	public UserAccount() {
		this.firstName = "";
		this.lastName = "";
		this.username = "";
		this.password = "";
		this.isAdmin = false;
		this.funds = 0;
	}

	public UserAccount(String firstName, String lastName, String username, String password, boolean isAdmin, long funds) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.funds = funds;
	}
	
	/**
	 *  Methods overridden from UserActions interface
	 */
	@Override
	public boolean withdraw(long withdrawAmount) {
		long newFunds = funds - withdrawAmount;
		
		if (newFunds < 0) {
			System.out.println("Error. Cannot widthdraw without overdrafting.");
			return false;
		}
		else {
			funds = newFunds;
		}
		
		return true;
	}

	@Override
	public boolean deposit(long depositAmount) {
		funds += depositAmount;
		
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
	
	/**
	 * Getters and Setters
	 * 
	 * @return
	 */
	
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public long getFunds() {
		return funds;
	}

	public void setFunds(long funds) {
		this.funds = funds;
	}
	
}
