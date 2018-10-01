package com.revature;

public interface UserActions {
	boolean withdraw(long withdrawAmount);
	boolean deposit(long depositAmount);
	boolean transfer(long transferAmount);
	boolean joinAccounts();
}