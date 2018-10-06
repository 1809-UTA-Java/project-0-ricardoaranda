package com.revature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Database {
	public static void initialize() {
    	File usersPath = new File("accounts");
    	File employeesPath = new File("employees");
    	File transactionsPath = new File ("pending-transactions");
    	File joinAccounts = new File("join-accounts");
    	
    	if (!usersPath.exists()) {
    		try {
				usersPath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	if (!employeesPath.exists()) {
    		try {
    			employeesPath.createNewFile();
    			
    			/* create super admin */
    	    	ArrayList<Account> accountsList = new ArrayList<>();
    	    	AdminAccount superAdmin = new AdminAccount("superadmin", "asdf", true, true);
    	    	accountsList.add(superAdmin);
    	    	Database.writeObject("employees", superAdmin);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	if (!transactionsPath.exists()) {
    		try {
    			transactionsPath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	if (!joinAccounts.exists()) {
    		try {
    			joinAccounts.createNewFile();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
	
	static void writeObject(String fileName, Object obj) {
		ArrayList<Account> newList = readAllObjects(fileName);
		newList.add((Account) obj);
		
		// TODO: inscanceOf(Transactions)
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (fileName))) {
			oos.writeObject(newList);
			
			oos.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	static int getObjectIndex(String fileName, String username) {
		int index = 0;
		ArrayList<Account> accountsList = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fileName))) {		
			accountsList = (ArrayList<Account>) ois.readObject();
			
			for (Account a : accountsList) {
				if (a.getUsername() != null && a.getUsername().contains(username))
						index = accountsList.indexOf(a);
			}        
            ois.close();
		}
		catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        } 
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
		
		return index;
		
	}
	
	static <T> void writeFromArrayList(String fileName, ArrayList<T> accountsList) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (fileName))) {
			oos.writeObject(accountsList);
			
			oos.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	static <T> ArrayList<T> readAllObjects(String fileName) {
		ArrayList<T> accountsList = new ArrayList<>();
		File path = new File(fileName);
		
		if (path.isFile() && path.length() == 0) {
			return accountsList;	// empty accounts list
		}
		else {
			try (ObjectInputStream ois= new ObjectInputStream(new FileInputStream(fileName))) {
				accountsList = (ArrayList<T>) ois.readObject();
				
				ois.close();
			}
			catch (FileNotFoundException ex) {
	            ex.printStackTrace();
	        }
	        catch (IOException ex) {
	        	System.out.println("from readAllObjects");
	            ex.printStackTrace();
	        }
	        catch (ClassNotFoundException ex) {
	            ex.printStackTrace();
	        }
			
			return accountsList;
		}
	}
	
	// for debugging
	static public void printAllObjects (ArrayList<Account> list) {
		ArrayList<Account> newList= new ArrayList<>(list);
		Iterator<Account> it = newList.iterator();
		while (it.hasNext()) {
			Account acc = (Account) it.next();
			System.out.println("Username: " + acc.getUsername());
			System.out.println("Password: " + acc.getPassword());
			System.out.println("First name: " + acc.getFirstName());
			System.out.println("Last name: " + acc.getLastName());
			System.out.println("Account ID: + " + acc.getAccountId());
			System.out.println("Account type: " + acc.getAccountType());
			System.out.println("Balance: " + acc.getBalance() + "\n");
		}
	}
	
	static public void printJoinAccounts () {
		ArrayList<ArrayList<String>> list = Database.readAllObjects("join-accounts");
		Iterator<ArrayList<String>> listIt = list.iterator();
		System.out.println("Printing join lists");
		while (listIt.hasNext()) {
			ArrayList<String> link = listIt.next();
			System.out.println(link.toString());
		}
	}
}