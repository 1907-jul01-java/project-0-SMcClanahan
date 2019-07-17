package com.revature;

import java.util.Scanner;

import com.revature.Util.ConnectionUtil;


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
            if (login.getPermissions() >= 1){ 
                EmployeeUser y = new EmployeeUser(input, connection.getConnection());
                while(true){
                    System.out.println("What would you like to do?\n" +
                    "1. View Accounts\n" +
                    "2. Approve/Deny account applications\n" +
                    "3. Change funds in user account\n" +
                    "Or type \"exit\" \n");
                    if(login.getPermissions() == 2){
                        System.out.println("4. Admin: Delete an account\n");
                    }

                    String in = input.next();
                    if(in.equalsIgnoreCase("exit")){
                        break;
                    }
                    switch(in){
                        case "1":{
                            y.updateTable();
                            System.out.println(y.toString());
                            break;
                        } //end case 1
                        case "2":{
                            y.Apply();
                            break;
                        } //end case 2
                        case "3":{
                            y.updateTable();
                            System.out.println(y.toString());
                            y.setUser();
                            System.out.println("What would you like to do?\n" +
                            "1. Deposit into account\n" +
                            "2. Withdrawl from account\n" +
                            "3. Transfer between accounts\n");
                            switch(input.nextInt()){
                                case 1:{
                                    y.Deposit(y.GetAccounts());
                                    y.Balance();
                                    break;
                                } //end case 1
                                case 2:{
                                    y.Withdrawl(y.GetAccounts());
                                    y.Balance();
                                    break;
                                } //end case 2
                                case 3:{
                                    y.Balance();
                                    y.Deposit(y.GetAccounts());
                                    break;
                                } //end case 3  
                                default:
                                System.err.println("Invalid Response\n");                              
                            } //end switch
                            break;
                        } //end case 3
                        case "4":{
                            if(login.getPermissions() != 2){
                                System.err.println("You do not have permission for those commands\n");
                                break;
                            }
                            else {

                            }
                        } //end case 4
                        default:
                        System.out.println("Invalid Response\n");
                    } //end switch
                } //end while
            } //end if
            else {
                BasicUser User = new BasicUser(input, user, connection.getConnection());
                while(true){  
                        System.out.println("What would you like to do?\n" +
                        "1. View my account balance\n" +
                        "2. Apply for a new account\n" +
                        "3. Be added to a joint account\n" +
                        "4. Make a Deposit\n" +
                        "5. Make a Withdrawl\n" +
                        "6. Make a transfer between accounts\n" +
                        "Or type \"exit\" \n");

                        String in = input.nextLine();
                        if (in.equalsIgnoreCase("exit")){
                            break;
                        }

                        switch(in){
                            case "1" :{
                                User.Balance();
                                break;
                            } //end case 1
                            case "2":{
                                User.Apply();
                                break;
                            } //end case 2
                            case "3":{
                                //TODO Enter joint acct logic
                                break;
                            }
                            case "4":{
                                User.Balance();
                                User.Deposit(User.GetAccounts());
                                break;
                            } //end case 3
                            case "5":{
                                User.Balance();
                                User.Withdrawl(User.GetAccounts());
                                break;
                            } //end case 4
                            case "6":{
                                User.Transfer();
                                break;
                            } //end case 5
                            default:
                            System.err.println("Invalid Response\n");
                        } //end switch
                } //end while
            } //end else
        } //end error handling if 
        else{
            System.err.println("Error encountered while attempting to login\n Please ask an admin for assistance");
        } //end else
        connection.close();
        input.close();
    } //end main
} //end file
