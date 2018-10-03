package com.revature;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ArrayList<Account> accountsList = new ArrayList<>();
//		Session session = new Session();
//		session.startProgram();
		
//		Database.readObject("accounts.txt");
		accountsList = Database.writeAllObjects("accounts.txt");
		Database.readAllObjects("accounts.txt");
    }
}
