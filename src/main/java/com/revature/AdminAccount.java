package com.revature;

public class AdminAccount extends Account   {
	final String dataFileName = "accounts.txt";
	private static final long serialVersionUID = 1;
	
	public AdminAccount() {
		this.username = "";
		this.password = "";
	}
	
	public AdminAccount(String username, String password, boolean isAdmin, boolean isSuperAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.isSuperAdmin = isSuperAdmin;
	}
}