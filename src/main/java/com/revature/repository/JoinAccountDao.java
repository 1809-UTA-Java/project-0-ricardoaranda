package com.revature.repository;

import com.revature.Account;
import com.revature.UserAccount;
import com.revature.Account.AccountType;

import com.revature.util.*;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class JoinAccountDao {
	public void createJoinAccount(Account account) {
		String sql = "";
		PreparedStatement ps = null;
		AccountDao adao = new AccountDao();
		UUID joinId = adao.retrieveJoinId(account.getAccountId());
		
		if (joinId == null) {
			joinId = UUID.randomUUID();
			adao.updateAccountJoinId(account, joinId);
			
			try(Connection conn = ConnectionUtil.getConnection()) {
				sql = "INSERT INTO JOIN_ACCOUNTS (a_join_id, a_account_id)"
						+ "VALUES (?, ?)";
				ps = conn.prepareStatement(sql);
				
				ps.setString(1, joinId.toString());
				ps.setString(2, account.getAccountId().toString());
				
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
	}
	
	public void addToJoinAccount(Account prospectiveAccount, UUID establishedJoinID) {
		AccountDao adao = new AccountDao();
		UUID prospectiveJoinId = adao.retrieveJoinId(prospectiveAccount.getAccountId());
		if (prospectiveJoinId != null)
			System.out.println("This account is already linked to another");
		else {
			
			// create statement that UPDATES prospectiveAccount's a_join_id in USER_ACCOUNTS table.
			adao.updateAccountJoinId(prospectiveAccount, establishedJoinID);
			
			String sql = "";
			PreparedStatement ps = null;
			
			try(Connection conn = ConnectionUtil.getConnection()) {
				sql = "INSERT INTO JOIN_ACCOUNTS (a_join_id, a_account_id)"
						+ "VALUES (?, ?)";
				ps = conn.prepareStatement(sql);
				
				ps.setString(1, establishedJoinID.toString());
				ps.setString(2, prospectiveAccount.getAccountId().toString());
				
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
	}
	
	public void removeAccountFromJoin(UUID accountIdToRemove) {
		String sql = "";
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			sql = "DELETE FROM JOIN_ACCOUNTS WHERE (a_account_id = ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, accountIdToRemove.toString());
			
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
}
