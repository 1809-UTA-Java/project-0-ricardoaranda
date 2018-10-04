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
    	
//    	accountsList = Database.readAllObjects("accounts");
//		Database.printAllObjects(accountsList);
    	
		Session session = new Session(accountsList);
		session.startProgram();
    }
}
