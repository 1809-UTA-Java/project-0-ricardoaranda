package com.revature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Database {
	static void writeObject(String fileName, Object obj) {
		ArrayList<Account> newList = readAllObjects(fileName);
		
		// TODO: inscanceOf(Transactions)
		newList.add((Account) obj);
		
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
	
	static public ArrayList<Account> writeAllObjects(String fileName) {
		ArrayList<Account> accountsList = new ArrayList<>();
		Account account1 = new UserAccount("Rick", "Sanchez", "pickleRick", "p4ssw0rd", false, false, 0, 0);
		Account account2 = new UserAccount("Mary", "Ramos", "fridaMay", "asdf", false, false, 0, 0);
		
		accountsList.add(account1);
		accountsList.add(account2);
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (fileName))) {
			oos.writeObject(accountsList);
			
			oos.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return accountsList;
	}
	
	static public ArrayList<Account> readAllObjects(String fileName) {
		ArrayList<Account> accountsList = new ArrayList<>();
		try (ObjectInputStream ois= new ObjectInputStream(new FileInputStream(fileName))) {
			accountsList = (ArrayList<Account>) ois.readObject();
			
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
		
		return accountsList;
	}
	
	static public void printAllObjects (ArrayList<Account> list) {
		ArrayList<Account> newList= new ArrayList<>(list);
		Iterator<Account> it = newList.iterator();
		while (it.hasNext()) {
			Account acc = (Account) it.next();
			System.out.println("Username: " + acc.getUsername());
			System.out.println("Password: " + acc.getPassword());
			System.out.println("First name: " + acc.getFirstName());
			System.out.println("Last name: " + acc.getLastName());
			System.out.println("Account type: " + acc.getAccountType() + "\n");
		}
	}
}