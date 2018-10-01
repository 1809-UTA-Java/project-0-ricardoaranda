package com.revature;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		Session session = new Session();
		session.startProgram();
		
		Database.readObject("accounts.txt");
    }
}
