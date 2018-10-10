package com.revature;

import java.util.Scanner;

import org.junit.Test;

import junit.framework.TestCase;

public class UserSessionTest extends TestCase {
	@Test 
	public void test() {
		Scanner sc = new Scanner(System.in);
		UserSession userSession = new UserSession(sc, "superadmin");
		
		assertFalse(userSession.evaluateDeposit(2001));
		
		assertFalse(userSession.evaluateWithdraw(-10, 10));
		
		assertFalse(userSession.evaluateTransfer(100, 99));
	}
}
