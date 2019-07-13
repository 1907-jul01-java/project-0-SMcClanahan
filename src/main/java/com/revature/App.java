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
                System.out.println(user);
            }
            if (login.getType() == 1){
            EmployeeUser y = new EmployeeUser(connection.getConnection());}
            else {
            BasicUser User = new BasicUser(user, connection.getConnection());}
            
            while(!input.nextLine().equals("exit")){
                System.out.println("While");
                if(user == -1){
                    break;
                }
            }
        } 
        else{
            System.err.println("Error encountered while attempting to login\n Please ask an admin for assistance");
        }
    }
}
