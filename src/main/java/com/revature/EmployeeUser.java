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
        try (PreparedStatement pStatement = this.connection.prepareStatement(
            "select * from accounts join userlogins_accounts" + " on accounts.id = userlogins_accounts.acctfk "
                    + " join userlogins on userlogins_accounts.userfk = userlogins.id"
                    + " group by accounts.id, userlogins_accounts.acctfk, userlogins_accounts.userfk, userlogins.id")){
            ResultSet resultSet = pStatement.executeQuery();
            this.userList.clear();
            //userList.add(0,"Account Number \t Last Name \t First Name \t Username \t Account \t Balance \n");
            int counter = 0;
            while (resultSet.next()){
                String tempString = (resultSet.getInt("id") + "\t" + 
                resultSet.getString("lastname") + "\t" +
                resultSet.getString("firstname") +  "\t" +
                resultSet.getString("username") +  "\t" +
                resultSet.getString("accttype") +  "\t" +
                resultSet.getDouble("balance") + "\n");   
                userList.add(counter, tempString); 
                System.out.println(userList.toString());
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
        try (PreparedStatement pStatement = connection.prepareStatement("select * from applications")) {
            ResultSet resultSet = pStatement.executeQuery();
            
            if(resultSet.next()){

                do {
                    System.out.println(resultSet.getInt("id") + "\t" + resultSet.getString("accttype") + "\t" + resultSet.getInt("accountid") + "\t" + resultSet.getString("accountstatus") + "\n");
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
                        PreparedStatement pStatement2 = connection.prepareStatement("insert into accounts (accttype) select accttype from applications where id = ?;");
                        pStatement2.setInt(1, response);
                        pStatement2.executeUpdate();
                        PreparedStatement pStatement3 = connection.prepareStatement("insert into userlogins_accounts (userfk, acctfk) select ?, Max(id) from accounts;");
                        pStatement3.setInt(1, response);
                        pStatement3.executeUpdate();

                        pStatement2.close();
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
            } //end if
            else{
                System.out.println("No new accounts to confirm\n");
            } //end else 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUser(){
        System.out.println("Please enter account number");
        this.UserID = input.nextInt();
    }

}