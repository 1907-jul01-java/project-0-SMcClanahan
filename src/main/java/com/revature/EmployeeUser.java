package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class EmployeeUser extends BasicUser{
    private ArrayList<String> userList = new ArrayList<String>();
    public EmployeeUser() {
    }

    public EmployeeUser(Scanner input, Connection connection) {
        super(input, connection);
        
    }

    @Override
    public String toString(){
        return userList.toString();
    }

    public void updateTable(){
        try (PreparedStatement psStatement = connection.prepareStatement(
            "select * from accounts right join userlogins on accounts.userfk = userlogins.id")){
            ResultSet resultSet = psStatement.executeQuery();
            //this.userList.clear();
            int counter = 0;
            while (resultSet.next()){
                String tempString = (resultSet.getString("lastname") + "\t" +
                resultSet.getString("firstname") +  "\t" +
                resultSet.getString("username") +  "\t" +
                resultSet.getInt("id") +  "\t" +
                resultSet.getString("accttype") +  "\t" +
                resultSet.getDouble("balance") + "\n");   
                System.out.println(tempString);  
                userList.add(counter, tempString);    
                counter++;     
            }
            Collections.sort(userList, Collections.reverseOrder());
        } catch(SQLException e){
            e.printStackTrace();
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void setUser(){
        System.out.println("Please enter a username");
        String username = input.nextLine();
        try (PreparedStatement pstatement = connection.prepareStatement(" select id from userlogins where username = ? ")) {
            pstatement.setString(1, username);
            ResultSet resultSet = pstatement.executeQuery();
            resultSet.next();
            this.UserID = resultSet.getInt("id");
            System.out.println(this.UserID);
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

}