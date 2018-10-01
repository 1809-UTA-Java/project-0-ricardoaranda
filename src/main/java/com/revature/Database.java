package com.revature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Database {
	static void writeObject(String fileName, Object obj) {
		try (ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fileName, true))) {
            oos.writeObject(obj);
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
	
	static ArrayList<Account> readAllObjects(String fileName) {
		return null;
	}
}