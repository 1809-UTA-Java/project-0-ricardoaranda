package com.revature;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {    	
    	Database.initialize();
    	Scanner scanner = new Scanner(System.in);
    	
		Session session = new Session(scanner);
		session.startProgram();
		
		scanner.close();
    }
}
