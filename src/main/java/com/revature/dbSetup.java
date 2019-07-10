package com.revature;

import java.sql.*;

class dbSetup{
    private String url = "jdbc:postgresql://localhost:5432/Project0db";
    private String username = "Project0db";
    private String password = "luft";

    public int connect(String user, String pass){
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            Statement statement = connection.createStatement();
            
            if(statement.executeQuery("SELECT userName FROM accounts;") != null) {
                System.out.println("Welcome " + user);
                return 1;
            }
            else if(statement.executeQuery("SELECT userName FROM employees;") != null){
                return 2;
            }
            else if(statement.executeQuery("SELECT userName FROM admin;") != null){
                System.out.println("Admin status verified on user: "+ user);
                return 3;
            }
            else{
                System.out.println("User not found");
                return -1;
            }
        } catch(SQLException e){

        }
        System.out.println("connection failed");
        return 0;
    }

}
