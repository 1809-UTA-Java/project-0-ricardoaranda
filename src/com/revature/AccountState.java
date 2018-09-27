package com.revature;

import java.util.Scanner;

public class AccountState {
    boolean isLoggedIn;
    boolean isAdmin;

    void welcomePrompt() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Welcome!");
        System.out.println("Enter 'L' to log in or 'C' to create an account: ");

        while (!sc.hasNext("[LlEe]")) {
            System.out.println("Invalid input.\n");
            System.out.println("Please enter 'L' to log in or 'C' to create an account: ");
            sc.next();
        }

        String response = sc.next();
        System.out.println("Success! Your input was: " + response);

        sc.close();
    }
}