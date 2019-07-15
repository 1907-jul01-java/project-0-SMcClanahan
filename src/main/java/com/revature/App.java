package com.revature;

import java.util.Scanner;

import com.revature.Util.ConnectionUtil;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ConnectionUtil connection = new ConnectionUtil();
        Scanner input = new Scanner(System.in);
        Login login = new Login(input, connection.getConnection());
        int user = login.ask();
        if (user >= 0){
            if (user == 0){
                user = login.Create(0);
            }
            if (user == 1){
                EmployeeUser y = new EmployeeUser(input, connection.getConnection());
                while(true){
                    System.out.println("What would you like to do?\n" +
                    "1. View Accounts\n" +
                    "2. Approve/Deny account applications\n" +
                    "3. Change funds in user account\n");

                    switch(input.nextLine()){
                        case "1":{
                            y.getAccountsTable();
                            break;
                        } //end case 1
                        case "2":{
                            System.out.println("not done yet");
                            break;
                        } //end case 2
                        case "3":{
                            y.getAccountsTable();
                            y.setUser();
                            System.out.println("What would you like to do?\n" +
                            "1. Deposit into account\n" +
                            "2. Withdrawl from account\n" +
                            "3. Transfer between accounts\n");
                            switch(input.nextInt()){
                                case 1:{
                                    y.Balance();
                                    y.Deposit(y.GetAccounts());
                                    break;
                                } //end case 1
                                case 2:{
                                    y.Balance();
                                    y.Withdrawl(y.GetAccounts());
                                    break;
                                } //end case 2
                                case 3:{
                                    y.Balance();
                                    y.Deposit(y.GetAccounts());
                                    break;
                                } //end case 3
                            } //end switch
                            break;
                        } //end case 3
                    } //end switch
                } //end while
            } //end if
            else {
                BasicUser User = new BasicUser(input, user, connection.getConnection());
                while(!input.nextLine().equals("exit")){ //TODO fix while 
                    if(user == -1){
                        break;
                    } //end if
                        System.out.println("What would you like to do?\n" +
                        "1. View my account balance\n" +
                        "2. Apply for a new account\n" +
                        "3. Make a Deposit\n" +
                        "4. Make a Withdrawl\n" +
                        "5. Make a transfer between accounts\n");
                        switch(input.nextLine()){
                            case "1" :{
                                User.Balance();
                                break;
                            } //end case 1
                            case "2":{
                                User.Apply();
                                break;
                            } //end case 2
                            case "3":{
                                User.Balance();
                                User.Deposit(User.GetAccounts());
                                break;
                            } //end case 3
                            case "4":{
                                User.Balance();
                                User.Withdrawl(User.GetAccounts());
                                break;
                            } //end case 4
                            case "5":{
                                User.Transfer();
                                break;
                            } //end case 5
                        } //end switch
                } //end while
            } //end else
            
            //TODO enter admin logic
        } //end error handling if 
        else{
            System.err.println("Error encountered while attempting to login\n Please ask an admin for assistance");
        } //end else
    } //end main
} //end file
