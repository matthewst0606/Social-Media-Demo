package com.fantastic.view;

import java.util.Scanner;

import com.fantastic.model.User;

public class MainView {
    private Scanner input;

    public MainView(Scanner input) {
        this.input = input;
    }

    public String readLine() {
        return input.nextLine();
    }

    public String receive(String prompt) {
        System.out.print(prompt + ": ");
        return input.nextLine();
    }

    public void printLoginMenu() {
        System.out.print(
            """
            ---------------------------------
            1. Register User
            2. Login
            3. Exit
            ---------------------------------
            >> """ + " ");
    }

    public void printMainMenu() {
        System.out.print(
            """
            
            MAIN MENU:
            ---------------------------------
            1. Update Profile
            2. Create Post
            3. Display Posts by a given User
            4. Update Post
            5. Comment on Post
            6. Display Comments on a Post
            7. Display All Posts
            8. List All Users
            9. Exit
            ---------------------------------
            >> """ + " ");
    }

    public void printRegistrationSuccess(User u) {
        System.out.print("\nYou have registered successfully!\n\n");
        System.out.println("User ID: " + u.getUserID());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Username: " + u.getUsername());
        System.out.println("Created At: " + u.getCreatedAt());


    }

    public void printUserSettings() {
        System.out.print(
            """
            
            USER PROFILE SETTINGS:
            ---------------------------------
            1. Change profile picture
            2. edit bio
            3. account settings
            4. exit
            ---------------------------------
            >> """ + " ");
    }


    public void printAccountSettings() {
        System.out.print(
            """
            
            ACCOUNT SETTINGS:
            ---------------------------------
            1. Update email
            2. Update username
            3. Update password
            4. Delete account
            5. exit
            ---------------------------------
            >> """ + " ");
    }

    public void printWarning() {
        System.out.print(
            """
            
            ARE YOU SURE YOU WANT TO DELETE YOUR ACCOUNT?
            WARNING: This process cannot be undone!
            ---------------------------------------------
            """);
    }

    public void printError(String msg) {
        System.out.print("\n" + msg + "\n\n");
    }

    public void printSuccess(String msg) {
        System.out.print("\n" + msg + "\n");

    }

    public void printList(String msg) {
        System.out.print(msg + "\n");

    }

}
