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
            
		} catch (SQLException ex) {
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
				boolean isAdmin = (rs.getInt("a_isAdmin") == 1 ? true : false);
				boolean active = (rs.getInt("a_active") == 1 ? true : false);
				long balance = (rs.getLong("a_balance"));
				
				a = new UserAccount();
				
				a.setUsername(userName);
				a.setPassword(password);
				a.setFirstName(firstName);
				a.setLastName(lastName);
				
				UUID uid = UUID.fromString(id);
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
	
	public void saveAccountState(Account account) {
		PreparedStatement ps = null;
		String sql = "UPDATE USER_ACCOUNTS SET a_username = ?, a_password = ?, a_firstName = ?, "
	               + "a_lastName = ?, a_accountType = ?, a_isAdmin = ?, "
	               + "a_active = ?, a_balance = ? WHERE (a_id = ? ) ";
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			ps = conn.prepareStatement(sql);
			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());
			ps.setString(3, account.getFirstName());
			ps.setString(4, account.getLastName());
			ps.setString(5, account.getAccountType() == AccountType.CHECKINGS ? "CHECKINGS" : "SAVINGS");
			ps.setInt(6, account.isAdmin() == true ? 1 : 0);
			ps.setInt(7, account.isActive() == true ? 1 : 0);
			ps.setLong(8, account.getBalance());
			
			ps.setString(9, account.getAccountId().toString());
			
			ps.executeUpdate();
			
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}  finally {
            if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
      }
		
	}
	
	public void deleteAccount(Account account) {
		PreparedStatement ps = null;
		String sql = "DELETE FROM USER_ACCOUNTS WHERE (a_id = ?)";
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			ps = conn.prepareStatement(sql);
			ps.setString(1, account.getAccountId().toString());
			
			ps.executeUpdate();
		} 
		catch (SQLException ex) {
			ex.getMessage();
		} 
		catch (IOException ex) {
			ex.getMessage();
		} 
		finally {
            if (ps != null)
				try {
					ps.close();
				} 
            	catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void viewAllUsers(int x) {
		PreparedStatement ps = null;
		Account a = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USER_ACCOUNTS WHERE (a_isAdmin = ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, x);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String userName = rs.getString("a_username");
				String password = rs.getString("a_password");
				String firstName = rs.getString("a_firstname");
				String lastName = rs.getString("a_lastname");
				String id = rs.getString("a_id");
				String accountType = rs.getString("a_accounttype");
				boolean isAdmin = (rs.getInt("a_isAdmin") == 1 ? true : false);
				boolean active = (rs.getInt("a_active") == 1 ? true : false);
				long balance = (rs.getLong("a_balance"));
				
				a = new UserAccount();
				
				a.setUsername(userName);
				a.setPassword(password);
				a.setFirstName(firstName);
				a.setLastName(lastName);
				
				UUID uid = UUID.fromString(id);
				a.setAccountId(uid);
				
				a.setAccountType(accountType);
				a.setAdmin(isAdmin);
				a.setActive(active);
				a.setBalance(balance);
				
				System.out.println(a.toString());
			}
			rs.close();
		} 
		catch (SQLException ex) {
			ex.getMessage();
		} 
		catch (IOException ex) {
			ex.getMessage();
		} 
		finally {
            if (ps != null)
				try {
					ps.close();
				} 
            	catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void viewPendingTransactions() {
		PreparedStatement ps = null;
		Account a = null;
//		List<Account> accounts = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USER_ACCOUNTS WHERE (a_active = ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String userName = rs.getString("a_username");
				String password = rs.getString("a_password");
				String firstName = rs.getString("a_firstname");
				String lastName = rs.getString("a_lastname");
				String id = rs.getString("a_id");
				String accountType = rs.getString("a_accounttype");
				boolean isAdmin = (rs.getInt("a_isAdmin") == 1 ? true : false);
				boolean active = (rs.getInt("a_active") == 1 ? true : false);
				long balance = (rs.getLong("a_balance"));
				
				a = new UserAccount();
				
				a.setUsername(userName);
				a.setPassword(password);
				a.setFirstName(firstName);
				a.setLastName(lastName);
				
				UUID uid = UUID.fromString(id);
				a.setAccountId(uid);
				
				a.setAccountType(accountType);
				a.setAdmin(isAdmin);
				a.setActive(active);
				a.setBalance(balance);
				
//				accounts.add(a);
				System.out.println(a.toString());
			}
			rs.close();
		} 
		catch (SQLException ex) {
			ex.getMessage();
		} 
		catch (IOException ex) {
			ex.getMessage();
		} 
		finally {
            if (ps != null)
				try {
					ps.close();
				} 
            	catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
//		return accounts;
	}
	
	public UUID retrieveJoinId(UUID accountId) {
		String sql = "SELECT A_JOIN_ID FROM USER_ACCOUNTS WHERE (a_id = ?)";
		PreparedStatement ps = null;
		UUID joinId = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			ps = conn.prepareStatement(sql);
			ps.setString(1, accountId.toString());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				joinId = UUID.fromString(rs.getString("a_join_id"));
			}
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (NullPointerException ex) {
			
		} catch (IOException ex) {
			ex.getMessage();
		}
		return joinId;
	}
	
	public void updateAccountJoinId(Account account, UUID joinId) {
		String sql = "UPDATE USER_ACCOUNTS SET a_join_id = ? WHERE (a_id = ?)";
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {			
			ps = conn.prepareStatement(sql);
			ps.setString(1, joinId.toString());
			ps.setString(2, account.getAccountId().toString());
			
			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}
	
	protected int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {

          int result = stmt.executeUpdate();

          return result;
    }
}
