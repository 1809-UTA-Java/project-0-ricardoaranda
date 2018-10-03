package com.revature;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
	static void writeObject(String fileName, Object obj) {
		try (ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fileName))) {
            oos.writeObject(obj);
            
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	static void readObject(String fileName) {
		try (ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fileName))) {		
			Account userAccount = new UserAccount();
			
			userAccount = (UserAccount) ois.readObject();
			
            System.out.println("Username: " + userAccount.getUsername());
            System.out.println("Password: " + userAccount.getPassword());
            System.out.println("First name: " + userAccount.getFirstName());
            System.out.println("Last name: " + userAccount.getLastName());
            System.out.println("Account type: " + userAccount.getAccountType());
            System.out.println("Privileges: " + userAccount.isAdmin());
            
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
		
		// ArrayList to array, to be able to access object fields
//		Account array[] = new Account[accountsList.size()];
//		accountsList.toArray(array);
//		for (int i = 0; i < accountsList.size(); i++) {
//			System.out.println("Username: " + array[i].getUsername());
//			System.out.println("Password: " + array[i].getPassword());
//			System.out.println("First name: " + array[i].getFirstName());
//			System.out.println("Last name: " + array[i].getLastName());
//			System.out.println("Account type: " + array[i].getAccountType() + "\n");
//		}

		return accountsList;
	}
	
	static public void readAllObjects(String fileName) {
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
		
		System.out.println("Outputing all objects in file..." + '\n');
		Account array[] = new Account[accountsList.size()];
		accountsList.toArray(array);
		for (int i = 0; i < accountsList.size(); i++) {
			System.out.println("Username: " + array[i].getUsername());
			System.out.println("Password: " + array[i].getPassword());
			System.out.println("First name: " + array[i].getFirstName());
			System.out.println("Last name: " + array[i].getLastName());
			System.out.println("Account type: " + array[i].getAccountType() + "\n");
		}
	}
}