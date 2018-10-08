package com.revature;

import java.util.Scanner;
import java.util.UUID;

import com.revature.repository.AccountDao;

public class App 
{
    public static void main( String[] args )
    { 	
    	/* initialize database with superadmin, 
    	 * make sure the accounts table is created
    	 */
    	AccountDao adao = new AccountDao();
    	Account account = null;
    	
    	account = adao.getAccountByUserName("superadmin");
    	
    	if (account == null) {
    		account = new UserAccount("Ricardo", "Aranda", "superadmin", "asdf", UUID.randomUUID(), true, true, true, 0);
    		
    		adao.createAccount(account);
    	}
    	
    			
//    	Database.initialize();
    	Scanner scanner = new Scanner(System.in);
    	
		Session session = new Session(scanner);
		session.startProgram();
		
		scanner.close();
    }
}
