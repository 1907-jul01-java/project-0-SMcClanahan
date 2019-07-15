package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import dnl.utils.text.table.TextTable;

class EmployeeUser extends BasicUser{

    public EmployeeUser() {
    }

    public EmployeeUser(Scanner input, Connection connection) {
        super(input, connection);
    }

    public void getAccountsTable(){
        try (PreparedStatement psStatement = connection.prepareStatement("select username, firstname, lastname, accounts.id, accttype, balance from accounts join userlogins on accounts.userfk = userlogins.id")){
            ResultSet resultSet = psStatement.executeQuery();
            String results[][];
            int counter;
            while (resultSet.next()){
                System.out.println(resultSet.getString("username") +
                " " + resultSet.getString("firstname") + 
                " " + resultSet.getString("lastname") + 
                " " + resultSet.getInt("id") + 
                " " + resultSet.getString("accttype") + 
                " $" + resultSet.getDouble("balance"));
                results[counter][1] = resultSet.getString("username");
                results[counter][2] = resultSet.getString("firstname");
                results[counter][3] = resultSet.getString("lastname");
                results[counter][4] = resultSet.getInt("id");
                results[counter][5] = resultSet.getString("accttype");
                results[counter][6] = resultSet.getDouble("balance");
                counter++;

            }        
            new TextTable(new String[] {"Username", "Firstname", "Lastname", "Account id", "Account Type", "Balance"},String results[][]);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setUser(){
        System.out.println("Please enter a username");
        String username = input.nextLine();
        try (PreparedStatement pstatement = connection.prepareStatement(" select id from userlogins where username = ? ")) {
            pstatement.setString(1, username);
            ResultSet resultSet = pstatement.executeQuery();
            this.UserID = resultSet.getInt("id");
            System.out.println(this.UserID);
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

}