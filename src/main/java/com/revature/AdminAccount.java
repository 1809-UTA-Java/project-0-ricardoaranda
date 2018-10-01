package com.revature;

public class AdminAccount extends Account   {
	final String dataFileName = "accounts.txt";
	
	public AdminAccount() {
		this.username = "";
		this.password = "";
		this.accountId = 0;
	}
	
	public AdminAccount(String username, String password, int accountId) {
		this.username = username;
		this.password = password;
		this.accountId = accountId;
	}
}