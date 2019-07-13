package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BasicUser implements Commands {
    protected int UserID;
    Connection connection;

    public BasicUser() {
    }

    public BasicUser(Connection connection) {
        this.connection = connection;
    }

    public BasicUser(String Username, Connection connection) { // constructor to set up connection information
        this.connection = connection;

        try (PreparedStatement pstatement = connection.prepareStatement(" select id from userlogins where username = ? ")) {
            pstatement.setString(1, Username);
            ResultSet resultSet = pstatement.executeQuery();
            this.UserID = resultSet.getInt("id");
            System.out.println(this.UserID);
            System.out.println("Constructor Completed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Constructor Failed");
        } catch (NullPointerException e){
            e.printStackTrace();
            System.err.println("Constructor Failed");
        }
    } //end constructor

    @Override
    public double Balance(String acctType) { // attempts to retrieve balance from sql database
        try { // begin try block
            PreparedStatement pstatement = this.connection.prepareStatement( "select balance from accounts where userfk = ?" );
            pstatement.setInt(1, this.UserID);
            ResultSet resultSet =  pstatement.executeQuery();
            return resultSet.getDouble("balance"); //returns current balance on acct from sql database

        } catch (Exception e) { //begin catch block
            e.printStackTrace();
        } //end try, catch block
        System.err.println("Attempt to retrieve amount failed"); //error message
        return -1; //error return key
    } //end Balance

    @Override
    public double Deposit(String acctType, float amount) { //attempts to update sql database with new deposit information
        PreparedStatement pstatement;
        try { //begin try block
            pstatement = this.connection.prepareStatement("select balance from accounts where userfk = ? and accttype = ?");
            pstatement.setInt(1, this.UserID);
            pstatement.setString(2, acctType);
            ResultSet resultSet = pstatement.executeQuery();
            double currentBalance = resultSet.getDouble("balance"); //gets current balance from sql database
            pstatement = this.connection.prepareStatement("update accounts set balance = ? where userfk = ? ");
            pstatement.setDouble(1, currentBalance + amount);
            pstatement.setInt(2, this.UserID);
            pstatement.execute(); //execute update
            return currentBalance + amount; //returns new balance

        } catch (SQLException e) { //begin catch block
            e.printStackTrace();
        } //end try, catch block
        System.err.println("Deposit Failed"); //error message
        return -1; //error return key
    } //end Deposit

    @Override
    public double Withdrawl(String acctType, float amount) {
        PreparedStatement pstatement;
        try { //begin try block
            pstatement = this.connection.prepareStatement("select balance from accounts where userfk = ? and accttype = ?");
            pstatement.setInt(1, this.UserID);
            pstatement.setString(2, acctType);
            ResultSet resultSet = pstatement.executeQuery();
            double currentBalance = resultSet.getDouble("balance"); //gets current balance from sql database
            pstatement = this.connection.prepareStatement("update accounts set balance = ? where userfk = ? ");
            pstatement.setDouble(1, currentBalance + amount);
            pstatement.setInt(2, this.UserID);
            pstatement.execute(); //execute update
            return currentBalance - amount; //returns new balance

        } catch (SQLException e) { //begin catch block
            e.printStackTrace();
        } //end try, catch block
        System.err.println("Withdrawl Failed"); //error message
        return -1; //error return kek
    } //end Withdrawl

    @Override
    public void Close() { //function for close of connection
        try { //begin try block
            this.connection.close(); //closes connection to database
        } catch (SQLException e) { //begin catch block
            e.printStackTrace();
            System.err.println("Connection refused to close"); //error message
        } //end try, catch block
    } //end Close

    public int getUser(){
        return this.UserID;
    }
} //end class BasicUser