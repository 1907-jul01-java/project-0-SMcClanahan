package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class BasicUser implements Commands {
    protected int UserID;
    Connection connection;

    public BasicUser() {
    }

    public BasicUser(Connection connection) {
        this.connection = connection;
    }

    public BasicUser(int userID, Connection connection) { // constructor to set up connection information
        this.connection = connection;
        this.UserID = userID;
    } // end constructor

    @Override
    public double Balance() { // attempts to retrieve balance from sql database
        try { // begin try block
            PreparedStatement pstatement = this.connection
                    .prepareStatement("select accttype, balance from accounts where userfk = ?");
            pstatement.setInt(1, this.UserID);
            ResultSet resultSet = pstatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getString("accttype") + " " + resultSet.getDouble("balance")); // outputs current balance on acct from sql database           
            }
            return 0;
             

        } catch (Exception e) { // begin catch block
            e.printStackTrace();
        } // end try, catch block
        System.err.println("Attempt to retrieve amount failed"); // error message
        return -1; // error return key
    } // end Balance

    @Override
    public double Deposit(int acctType, double amount) { // attempts to update sql database with new deposit
                                                           // information
        PreparedStatement pstatement;
        try { // begin try block
            pstatement = this.connection
                    .prepareStatement("select balance from accounts where userfk = ? and id = ?");
            pstatement.setInt(1, this.UserID);
            pstatement.setInt(2, acctType);
            ResultSet resultSet = pstatement.executeQuery();
            resultSet.next();
            double currentBalance = resultSet.getDouble("balance"); // gets current balance from sql database
            pstatement = this.connection.prepareStatement("update accounts set balance = ? where userfk = ? ");
            pstatement.setDouble(1, currentBalance + amount);
            pstatement.setInt(2, this.UserID);
            pstatement.execute(); // execute update
            return currentBalance + amount; // returns new balance

        } catch (SQLException e) { // begin catch block
            e.printStackTrace();
        } // end try, catch block
        System.err.println("Deposit Failed"); // error message
        return -1; // error return key
    } // end Deposit

    @Override
    public double Withdrawl(int acctType, double amount) {
        PreparedStatement pstatement;
        try { // begin try block
            pstatement = this.connection
                    .prepareStatement("select balance from accounts where userfk = ? and id = ?");
            pstatement.setInt(1, this.UserID);
            pstatement.setInt(2, acctType);
            ResultSet resultSet = pstatement.executeQuery();
            resultSet.next();
            double currentBalance = resultSet.getDouble("balance"); // gets current balance from sql database
            pstatement = this.connection.prepareStatement("update accounts set balance = ? where userfk = ? ");
            pstatement.setDouble(1, currentBalance + amount);
            pstatement.setInt(2, this.UserID);
            pstatement.execute(); // execute update
            return currentBalance - amount; // returns new balance

        } catch (SQLException e) { // begin catch block
            e.printStackTrace();
        } // end try, catch block
        System.err.println("Withdrawl Failed"); // error message
        return -1; // error return kek
    } // end Withdrawl

    @Override
    public void Close() { // function for close of connection
        try { // begin try block
            this.connection.close(); // closes connection to database
        } catch (SQLException e) { // begin catch block
            e.printStackTrace();
            System.err.println("Connection refused to close"); // error message
        } // end try, catch block
    } // end Close

    public int getUser() {
        return this.UserID;
    }

    @Override
    public int GetAccounts() { //begin GetAccounts
        int counter = 0;
        try { //begin try block
            PreparedStatement pStatement = this.connection.prepareStatement("select accttype, id from accounts where userfk = ?");
            pStatement.setInt(1, this.UserID);
            ResultSet resultSet = pStatement.executeQuery();
            System.out.println("Please select account");
            while (resultSet.next()) {
                System.out.println((++counter) + " " + resultSet.getString("accttype"));
            }
            try (Scanner input = new Scanner(System.in)){
                int response  = input.nextInt();
                return response;
            } catch(Exception e){
                e.printStackTrace();
            }
        } catch (SQLException e) { //begin catch block
            e.printStackTrace();
        } //end try/catch
        return 0;
    }// end GetAccounts
} //end class BasicUser