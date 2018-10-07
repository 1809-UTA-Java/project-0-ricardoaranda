package com.revature.repository;

import com.revature.Account;
import com.revature.Account.AccountType;
import com.revature.util.*;

import java.io.IOException;
import java.sql.*;

public class AccountDao {
	public synchronized void createAccount(Connection conn, Account account) throws SQLException {
		String sql = "";
		PreparedStatement ps = null;
		
		try {
			sql = "INSERT INTO USER_ACCOUNTS ( a_id, a_username, a_password, "
			+ "a_firstName, a_lastName, a_accountType, "
			+ "a_isAdmin, a_balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, account.getAccountId().toString());
            ps.setString(2, account.getUsername()); 
            ps.setString(3, account.getPassword()); 
            ps.setString(4, account.getFirstName()); 
            ps.setString(5, account.getLastName()); 
            ps.setString(6, (account.getAccountType() == AccountType.CHECKINGS ? "CHECKINGS" : "SAVINGS")); 
            ps.setInt(7, account.isAdmin() == true ? 1 : 0);
            ps.setLong(8, account.getBalance());
            
            int rowcount = databaseUpdate(conn, ps);
            if (rowcount != 1) {
                 throw new SQLException("PrimaryKey Error when updating DB!");
            }
		} finally {
                if (ps != null)
                    ps.close();
            }
	} 
	
	public Account getAccountByName(String name) {
		PreparedStatement ps = null;
		Account a = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS WHERE NAME=?";
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
		
		return a;
	}
	
	protected int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {

          int result = stmt.executeUpdate();

          return result;
    }
}
