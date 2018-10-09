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
		
		if (joinId == null) 
			joinId = UUID.randomUUID();	
		
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
