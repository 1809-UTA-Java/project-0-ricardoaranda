package com.revature;

public class Demo {

	public static void main(String[] args) {
		Session session = new Session();
		session.startProgram();
		
		Database.readObject("accounts.txt");
	}

}
