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
            int userType = login.getType();
            if (userType == 1){
            EmployeeUser y = new EmployeeUser(connection.getConnection());
            //TODO enter employee logic
            }
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
