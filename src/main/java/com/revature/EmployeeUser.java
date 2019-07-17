package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
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
        try {
            ResultSet resultSet = this.stateQuery();
            this.userList.clear();
            //userList.add(0,"Account Number \t Last Name \t First Name \t Username \t Account \t Balance \n");
            int counter = 0;
            while (resultSet.next()){
                String tempString = (resultSet.getInt("id") + " \t " + 
                resultSet.getString("lastname") + " \t " +
                resultSet.getString("firstname") +  " \t " +
                resultSet.getString("username") +  " \t " +
                resultSet.getString("accttype") +  " \t " +
                resultSet.getDouble("balance") + " \n ");   
                userList.add(counter, tempString); 
                //System.out.println(userList.toString());
                //System.out.println(tempString);   
                counter++;
            }
            Collections.sort(this.userList, Collections.reverseOrder());
        } catch(SQLException e){
            e.printStackTrace();
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void Apply(){
        try (PreparedStatement pStatement = connection.prepareStatement("select * from applications join userlogins on applications.userfk = userlogins.id")) {
            ResultSet resultSet = pStatement.executeQuery();
            
            if(resultSet.next()){

                do {
                    System.out.println(resultSet.getInt("id") + " \t " + resultSet.getString("accttype") + " \t " + resultSet.getString("lastname") + " \t " + resultSet.getString("firstname") + " \t " + resultSet.getInt("accountid") + " \t " + resultSet.getString("accountstatus") + "\n");
                }while(resultSet.next());
                System.out.println("Please select id to confirm/deny");
                int response = input.nextInt();
                System.out.println("Please enter \"confirm\" or \"deny\" ");
                String confirmation = input.next();
                if (confirmation.equalsIgnoreCase("confirm")){
                    try  {
                        //CallableStatement procedureStatement = connection.prepareCall("{CALL createaccounts(?)}");
                        //procedureStatement.setInt(1, response);
                        //procedureStatement.execute();
                        PreparedStatement preparedStatement = connection.prepareStatement("select * from applications where id = ?");
                        preparedStatement.setInt(1, response);
                        ResultSet resultSet2 = preparedStatement.executeQuery();
                        resultSet2.next();
                        String accttype = resultSet2.getString("accttype");
                        int userfk = resultSet2.getInt("userfk");
                        int acctID = resultSet2.getInt("accountid");
                        PreparedStatement pStatement2;
                        PreparedStatement pStatement3;
                        if (acctID > 0){
                        pStatement3 = connection.prepareStatement("insert into userlogins_accounts (userfk, acctfk) select ?, ? from accounts");
                        pStatement3.setInt(1, userfk);
                        pStatement3.setInt(2, acctID);
                        pStatement3.executeUpdate();
                        }
                        else{
                        pStatement2 = connection.prepareStatement("insert into accounts (accttype) values (?)");
                        pStatement2.setString(1, accttype);
                        pStatement2.executeUpdate();
                        pStatement3 = connection.prepareStatement("insert into userlogins_accounts (userfk, acctfk) select ?, Max(id) from accounts");
                        pStatement3.setInt(1, userfk);
                        pStatement3.executeUpdate();
                        pStatement2.close();
                        }
                        
                        pStatement3.close();
                        PreparedStatement pStatement4 = connection.prepareStatement("update applications set accountstatus = ? where id = ?");
                        pStatement4.setString(1, "Approved");
                        pStatement4.setInt(2, response);
                        pStatement4.executeUpdate();
                        pStatement4.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } //end catch
                } //end if
                else if (confirmation.equalsIgnoreCase("deny")){
                    try  {
                        PreparedStatement pStatement4 = connection.prepareStatement("update applications set accountstatus = ? where id = ?");
                        pStatement4.setString(1, "Denied");
                        pStatement4.setInt(2, response);
                        pStatement4.executeUpdate();
                        pStatement4.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.err.println("Invalid Response");
                }
            } //end if
            else{
                System.out.println("No new accounts to confirm\n");
            } //end else 
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InputMismatchException e){
            System.err.println("Invalid input");
        }
    }

    public void setUser(){
        try{
        System.out.println("Please enter account number");
        this.UserID = input.nextInt();
        } catch(InputMismatchException e){
            System.err.println("Invalid Input: SetUser");
        }
    }
    @Override
    protected ResultSet stateQuery(){
        try { // begin try block
            PreparedStatement pStatement = this.connection.prepareStatement(
                    "select * from accounts join userlogins_accounts" + " on accounts.id = userlogins_accounts.acctfk "
                            + " join userlogins on userlogins_accounts.userfk = userlogins.id"
                            + " group by accounts.id, userlogins_accounts.acctfk, userlogins_accounts.userfk, userlogins.id");
            ResultSet resultSet = pStatement.executeQuery();
            //System.out.println("StateQuery");
            return resultSet;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}