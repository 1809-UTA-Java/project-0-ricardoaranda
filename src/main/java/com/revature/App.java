package com.revature;

import java.util.ArrayList;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
    	ArrayList<Account> accountsList = new ArrayList<>();
    	Scanner scanner = new Scanner(System.in);
    	
//    	accountsList = Database.readAllObjects("accounts");
//		Database.printAllObjects(accountsList);
    	
		Session session = new Session(accountsList, scanner);
		session.startProgram();
		
		scanner.close();
    }
}
