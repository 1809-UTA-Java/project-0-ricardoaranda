package com.revature.repository;

import com.revature.Account;
import com.revature.UserAccount;

import com.revature.Account.AccountType;
import com.revature.util.*;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class AccountDao {
	public synchronized void createAccount(Account account) {
		String sql = "";
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			sql = "INSERT INTO USER_ACCOUNTS ( a_id, a_username, a_password, "
			+ "a_firstName, a_lastName, a_accounttype, "
			+ "a_isAdmin, a_active, a_balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, account.getAccountId().toString());
            ps.setString(2, account.getUsername()); 
            ps.setString(3, account.getPassword()); 
            ps.setString(4, account.getFirstName()); 
            ps.setString(5, account.getLastName()); 
            ps.setString(6, (account.getAccountType() == AccountType.CHECKINGS ? "CHECKINGS" : "SAVINGS")); 
            ps.setInt(7, account.isAdmin() == true ? 1 : 0);
            ps.setInt(8, account.isActive() == true ? 1 : 0);
            ps.setLong(9, account.getBalance());
            
            ps.executeUpdate();
            
//            int rowcount = databaseUpdate(conn, ps);
//            if (rowcount != 1) {
//                 throw new SQLException("PrimaryKey Error when updating DB!");
//            }
		}catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		} finally {
                if (ps != null)
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
          }
	} 
	
	public Account getAccountByUserName(String name) {
		PreparedStatement ps = null;
		Account a = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USER_ACCOUNTS WHERE (a_username = ? ) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String userName = rs.getString("a_username");
				String password = rs.getString("a_password");
				String firstName = rs.getString("a_firstname");
				String lastName = rs.getString("a_lastname");
				String id = rs.getString("a_id");
				String accountType = rs.getString("a_accounttype");
				boolean isAdmin = (rs.getString("a_isAdmin") == "0" ? false : true);
				boolean active = (rs.getString("a_active") == "0" ? false : true);
				long balance = (rs.getLong("a_balance"));
				
				
				UUID uid = UUID.fromString(id);
				
				a = new UserAccount();
				
				a.setUsername(userName);
				a.setPassword(password);
				a.setFirstName(firstName);
				a.setLastName(lastName);
				
				
				a.setAccountId(uid);
				
				a.setAccountType(accountType);
				a.setAdmin(isAdmin);
				a.setActive(active);
				a.setBalance(balance);
			}
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
