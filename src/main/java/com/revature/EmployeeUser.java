package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class EmployeeUser extends BasicUser{

    public EmployeeUser() {
    }

    public EmployeeUser(Connection connection) {
        super(connection);
    }
    
    public void setUser(String Username){
        try (PreparedStatement pstatement = connection.prepareStatement(" select id from userlogins where username = ? ")) {
            pstatement.setString(1, Username);
            ResultSet resultSet = pstatement.executeQuery();
            this.UserID = resultSet.getInt("id");
            System.out.println(this.UserID);
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

}