package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BasicUser implements Commands {
    private String Username;
    private int UserID;
    Connection connection;

    public BasicUser() {
    }

    public BasicUser(String Username, Connection connection) {
        this.Username = Username;
        this.connection = connection;
        
        try (PreparedStatement pstatement = connection.prepareStatement(" select id from userlogins where username = ? ")) {
           pstatement.setString(1, Username);
           ResultSet resultset = pstatement.executeUpdate();
           this.UserID = resultset;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int Balance(String user, String acctType) {
        this.connection.prepareStatement(" select balance from accounts where userfk = ? ")
        return 0;
    }

    @Override
    public void Deposit(String user, String acctType, float amount) {

    }

    @Override
    public void Withdrawl(String user, String acctType, float amount) {

    }

    @Override
    public void Close(String user, String acctType) {

    }
}