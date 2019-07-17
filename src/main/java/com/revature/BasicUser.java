package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

class BasicUser implements Commands {
    protected int UserID;
    Connection connection;
    protected Scanner input;

    public BasicUser() {
    }

    public BasicUser(Scanner input, Connection connection) {
        this.connection = connection;
        this.input = input;
    }

    public BasicUser(Scanner input, int userID, Connection connection) { // constructor to set up connection information
        this.connection = connection;
        this.UserID = userID;
        this.input = input;
    } // end constructor

    @Override
    public double Balance() { // attempts to retrieve balance from sql database
        try { // begin try block
            ResultSet resultSet = this.stateQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ". " + resultSet.getString("accttype") + " "
                        + resultSet.getDouble("balance") + "\n"); // outputs
                // current
                // balance
                // on acct
                // from sql
                // database
            }
            System.out.println();
            return 0;

        } catch (Exception e) { // begin catch block
            e.printStackTrace();
        } // end try, catch block
        System.err.println("Attempt to retrieve amount failed"); // error message
        return -1; // error return key
    } // end Balance

    @Override
    public double Deposit(int acctType) { // attempts to update sql database with new deposit
        
        try{
        System.out.println("Please enter amount to be deposited\n");
        double amount = input.nextDouble();
        System.out.println(amount);
        if (amount <= 0) {
            System.out.println("Please enter only positive numbers\n");
            return -1;
        }

        PreparedStatement pstatement;
        try { // begin try block
            pstatement = this.connection.prepareStatement("select balance from accounts where id = ?");
            pstatement.setInt(1, acctType);
            ResultSet resultSet = pstatement.executeQuery();
            resultSet.next();
            double currentBalance = resultSet.getDouble("balance"); // gets current balance from sql database
            pstatement = this.connection.prepareStatement("update accounts set balance = ? where id = ?"); 
            pstatement.setDouble(1, currentBalance + amount);
            pstatement.setInt(2, acctType);
            pstatement.execute(); // execute update
            return currentBalance + amount; // returns new balance

        } catch (SQLException e) { // begin catch block
            e.printStackTrace();
        } // end try, catch block
        System.err.println("Deposit Failed"); // error message
    } catch (InputMismatchException e){
        System.err.println("Invalid Input");
    }
    return -1; // error return key
    } // end Deposit

    @Override
    public double Withdrawl(int acctType) {
       
        try{
        System.out.println("Please enter amount to be withdrawled\n");
        double amount = input.nextDouble();
        if (amount > getBalance(acctType)) {
            System.out.println("Cannot complete withdrawl\n" + "Account would be overdrawn\n");
        } else if (amount <= 0) {
            System.out.println("Please enter only positive numbers\n");
            return -1;
        }

        PreparedStatement pstatement;
        try { // begin try block
            pstatement = this.connection.prepareStatement("select balance from accounts where id = ?");
            pstatement.setInt(1, acctType);
            ResultSet resultSet = pstatement.executeQuery();
            resultSet.next();
            double currentBalance = resultSet.getDouble("balance"); // gets current balance from sql database
            pstatement = this.connection
                    .prepareStatement("update accounts set balance = ? where id = ?");
            pstatement.setDouble(1, currentBalance - amount);
            pstatement.setInt(2, acctType);
            pstatement.execute(); // execute update
            return currentBalance - amount; // returns new balance

        } catch (SQLException e) { // begin catch block
            e.printStackTrace();
        } // end try, catch block
        System.err.println("Withdrawl Failed"); // error message
        
    } catch (InputMismatchException e){
        System.err.println("Invalid Input");
    }
    return -1; // error return key
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

    protected ResultSet stateQuery(){
        try { // begin try block
            PreparedStatement pStatement = this.connection.prepareStatement(
                    "select * from accounts join userlogins_accounts" + " on accounts.id = userlogins_accounts.acctfk "
                            + " join userlogins on userlogins_accounts.userfk = userlogins.id"
                            + " where userlogins.id = ? group by accounts.id, userlogins_accounts.acctfk, userlogins_accounts.userfk, userlogins.id");
            pStatement.setInt(1, this.UserID);
            ResultSet resultSet = pStatement.executeQuery();
            System.out.println("StateQuery");
            return resultSet;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int GetAccounts() { // begin GetAccounts
        try { // begin try block
            System.out.println("Please select account\n");
            this.Balance();
            int response = this.input.nextInt();
            return response;
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (InputMismatchException e){
            System.err.println("Invalid Input");
        }// end try/catch
        return 0;
    }// end GetAccounts

    @Override
    public void Apply() {
        System.out.println("Please specify what type of account you would like to open\n");
        String accttype = input.nextLine();

        try (PreparedStatement pStatement = connection
                .prepareStatement("insert into applications (userfk, accttype) values (?,?)")) {
            pStatement.setInt(1, UserID);
            pStatement.setString(2, accttype);
            pStatement.executeUpdate();
            System.out.println("Your application has been submitted\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } // end try/catch
    } // end Apply

    public void Apply(int acctNumber){
        try (PreparedStatement pStatement1 = connection.prepareStatement("select * from accounts where id = ?")){
            pStatement1.setInt(1, acctNumber);
            ResultSet resultSet = pStatement1.executeQuery();
            if(resultSet.next()){
            
            PreparedStatement pStatement = connection
                .prepareStatement("insert into applications (userfk, accttype, accountid) values (?,?,?) ");
            pStatement.setInt(1, UserID);
            pStatement.setString(2, resultSet.getString("accttype"));
            pStatement.setInt(3, acctNumber);
            pStatement.executeUpdate();
            System.out.println("Your application has been submitted\n");
            }

            else{
                System.err.println("Account does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } // end try/catch
    }

    @Override
    public void Transfer() {
       
       try{
        System.out.println("Transfer from:");
        int from = this.GetAccounts();

        System.out.println("Transfer to:");
        int to = this.GetAccounts();

        if (to == from) {
            System.out.println("You cannot trasfer to and from the same account\n");
            this.Transfer();
        }

        System.out.println("How much would you like to transfer?\n");
        double amount = input.nextDouble();
        if (amount > this.getBalance(from)) {
            System.out.println("Cannot complete transfer \n account would be overdrawn\n");
            this.Transfer();
        }
        else{
        try {
            PreparedStatement pStatement = connection.prepareStatement("update accounts set balance = ? where id = ?");
            pStatement.setDouble(1, this.getBalance(to) + amount);
            pStatement.setInt(2, to);
            pStatement.executeUpdate();
            pStatement.setDouble(1, this.getBalance(from) - amount);
            pStatement.setInt(2, from);
            pStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    } catch (InputMismatchException e){
        System.err.println("Invalid Input");
    }
    } // end Transfer

    public double getBalance(int accttype) {
        try (PreparedStatement pStatement = connection.prepareStatement("select balance from accounts where id = ?")) {
            pStatement.setInt(1, accttype);
            ResultSet resultSet = pStatement.executeQuery();
            resultSet.next();
            return resultSet.getDouble("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

} // end class BasicUser