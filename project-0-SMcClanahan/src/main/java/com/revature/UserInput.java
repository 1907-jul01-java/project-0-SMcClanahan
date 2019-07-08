package com.revature;

import java.util.Scanner;

interface UserInput {
    Scanner scanner = new Scanner(System.in);

    public default void Login() {
        System.out.println(" Please Enter Username\n");
        String userName = scanner.nextLine();

        //TODO insert logic checking databases for user credentials

        System.out.println(" Please Enter Password\n");
        String password = scanner.nextLine();
        //TODO insert logic checking password against user password in database
    }

    public default void newUser(){
        String dataBaseUserName;
        System.out.println("Welcome! Please enter a username");
        String userName = scanner.nextLine();
        if (userName == dataBaseUserName){
            System.out.println("Username Already Exists!");
            newUser();
        }
        else{
            System.out.println("Please Enter a Password\n Must be at lease 8 characters long");
            String password = scanner.nextLine();
        }
    }
}