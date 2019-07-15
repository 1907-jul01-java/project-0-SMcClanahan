package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class BasicUser implements Commands {
    protected int UserID;
    Connection connection;
    private Scanner input;

    public BasicUser() {
    }

    public BasicUser(Connection connection) {
        this.connection = connection;
    }

    public BasicUser(Scanner input, int userID, Connection connection) { // constructor to set up connection information
        this.connection = connection;
        this.UserID = userID;
        this.input = input;
    } // end constructor

    @Override
    public double Balance() { // attempts to retrieve balance from sql database
        try { // begin try block
            PreparedStatement pstatement = this.connection
                    .prepareStatement("select accttype, balance from accounts where userfk = ?");
            pstatement.setInt(1, this.UserID);
            ResultSet resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("accttype") + " " + resultSet.getDouble("balance")); // outputs
                                                                                                            // current
                                                                                                            // balance
                                                                                                            // on acct
                                                                                                            // from sql
                                                                                                            // database
            }
            return 0;

        } catch (Exception e) { // begin catch block
            e.printStackTrace();
        } // end try, catch block
        System.err.println("Attempt to retrieve amount failed"); // error message
        return -1; // error return key
    } // end Balance

    @Override
    public double Deposit(int acctType) { // attempts to update sql database with new deposit
        System.out.println("Please enter amount to be deposited");
        double amount = input.nextDouble();                          
        
        PreparedStatement pstatement;
        try { // begin try block
            pstatement = this.connection.prepareStatement("select balance from accounts where userfk = ? and id = ?");
            pstatement.setInt(1, this.UserID);
            pstatement.setInt(2, acctType);
            ResultSet resultSet = pstatement.executeQuery();
            resultSet.next();
            double currentBalance = resultSet.getDouble("balance"); // gets current balance from sql database
            pstatement = this.connection.prepareStatement("update accounts set balance = ? where userfk = ? and id = ?");
            pstatement.setDouble(1, currentBalance + amount);
            pstatement.setInt(2, this.UserID);
            pstatement.setInt(3, acctType);
            pstatement.execute(); // execute update
            return currentBalance + amount; // returns new balance

        } catch (SQLException e) { // begin catch block
            e.printStackTrace();
        } // end try, catch block
        System.err.println("Deposit Failed"); // error message
        return -1; // error return key
    } // end Deposit

    @Override
    public double Withdrawl(int acctType) {
        System.out.println("Please enter amount to be withdrawled");
        double amount = input.nextDouble();

        PreparedStatement pstatement;
        try { // begin try block
            pstatement = this.connection.prepareStatement("select balance from accounts where userfk = ? and id = ?");
            pstatement.setInt(1, this.UserID);
            pstatement.setInt(2, acctType);
            ResultSet resultSet = pstatement.executeQuery();
            resultSet.next();
            double currentBalance = resultSet.getDouble("balance"); // gets current balance from sql database
            pstatement = this.connection.prepareStatement("update accounts set balance = ? where userfk = ? and id = ?");
            pstatement.setDouble(1, currentBalance - amount);
            pstatement.setInt(2, this.UserID);
            pstatement.setInt(3, acctType);
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
    public int GetAccounts() { // begin GetAccounts
        try { // begin try block
            PreparedStatement pStatement = this.connection
                    .prepareStatement("select accttype, id from accounts where userfk = ?");
            pStatement.setInt(1, this.UserID);
            ResultSet resultSet = pStatement.executeQuery();
            System.out.println("Please select account");
            if (!resultSet.next()) {
                System.out.println("No accounts found");
            }
            do {
                System.out.println((resultSet.getInt("id")) + " " + resultSet.getString("accttype"));
            } while (resultSet.next());
                int response = this.input.nextInt();
                return response;
        } catch (SQLException e) { // begin catch block
            e.printStackTrace();
        } // end try/catch
        return 0;
    }// end GetAccounts

    @Override
    public void Apply() {
        System.out.println("Please specify what type of account you would like to open");
        String accttype = input.nextLine();

            try (PreparedStatement pStatement = connection
                    .prepareStatement("insert into applications (id, accttype) values (?,?)")) {
                pStatement.setInt(1, UserID);
                pStatement.setString(2, accttype);
                pStatement.executeUpdate();
                System.out.println("Your application has been submitted");
            } catch (SQLException e) {
                e.printStackTrace();
            } // end try/catch
    } //end Apply

    @Override
    public void Transfer() {
        this.Balance();
        System.out.println("Transfer from:");
        int from = this.GetAccounts();

        System.out.println("Transfer to:");
        int to = this.GetAccounts();
        
        if (to == from){
            System.out.println("You cannot trasfer to and from the same account");
        }

        System.out.println("How much would you like to transfer?");
            double amount = input.nextDouble();

        //TODO enter premade command to update values in database
    }

    
} //end class BasicUser