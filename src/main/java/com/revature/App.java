package com.revature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import com.revature.repository.AccountDao;
import com.revature.util.ConnectionUtil;

public class App 
{
    public static void main( String[] args )
    {    	
    	AccountDao adao = new AccountDao();
    	Account user = new UserAccount("Ricardo", "Aranda", "steamy", "asdf", false, false, 7861);
    	Connection conn = null;
    	
    	try {
    		conn = ConnectionUtil.getConnection();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
    	
    	try {
			adao.createAccount(conn, user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    			
    	Database.initialize();
    	Scanner scanner = new Scanner(System.in);
    	
		Session session = new Session(scanner);
		session.startProgram();
		
		scanner.close();
    }
}
