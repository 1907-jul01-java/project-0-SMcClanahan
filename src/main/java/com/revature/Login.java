package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    Connection connection;
    Scanner input;
    int type;

    public Login(Scanner input, Connection connection) {
        this.connection = connection;
        this.input = input;
    }

    public int ask() {
        String response;
        System.out.println("Do you have an acccout? (y,n) \n");
        response = input.nextLine();
        if (response.equals("y") || response.equals("yes")) {
            return this.Match();
        } else if (response.equals("n") || response.equals("no")) {
            return 0;
        } else {
            System.out.println("invalid response\n");
            this.ask();
        }
        System.err.println("Error, Login failed");
        return -1;
    }

    private int Match() {
        System.out.println("Please enter username\n");
        String response = input.nextLine();
        try (PreparedStatement pstatement = connection
                .prepareStatement("select * from userlogins where username = ?")) {
            pstatement.setString(1, response);
            ResultSet resultSet = pstatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Please enter password\n");
                response = input.nextLine();
                if (response.equals(resultSet.getString("pass"))) {
                    System.out.println("Welcome\t" + resultSet.getString("firstname") + " " + resultSet.getString("lastname") + "\n");
                    return resultSet.getInt("id");
                } else {
                    System.out.println("invalid password\n");
                    this.ask();
                }
            } else {
                System.out.println("Username not found\n");
                this.ask();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public int Create(int Type) {
        this.type = Type;
        String username, pass, firstname, lastname;

        do {
            System.out.println("Please create a username\n");
            username = input.nextLine();
            if (username.equals("exit")) {
                break;
            }
        } while (username.equals(null) || username.equals(""));

        if (username.equals("Login")) {
            return this.Match();
        }

        try (PreparedStatement pstatement = connection
                .prepareStatement("select * from userlogins where username = ?")) {
            pstatement.setString(1, username);
            ResultSet resultSet = pstatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Username already exists \n type \"Login\" to login or \n");
                this.Create(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        do {
            System.out.println("Please create a password\n");
            pass = input.nextLine();
            if (pass.equals("exit")) {
                return -1;
            }
        } while (pass.equals(null) || pass.equals(""));
        System.out.println("Please enter Firstname\n");
        firstname = input.nextLine();
        System.out.println("Please enter Lastname\n");
        lastname = input.nextLine();

        try (PreparedStatement pstatement = connection.prepareStatement(
                "insert into userlogins (username, pass, firstname, lastname, accesstype) values (?,?,?,?,?)")) {

                    pstatement.setString(1, username);
                    pstatement.setString(2, pass);
                    pstatement.setString(3, firstname);
                    pstatement.setString(4, lastname);
                    pstatement.setInt(5, type);
                    if(pstatement.executeUpdate() == 1){
                        return 0;
                    }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return -1;
    }

    public int getType() {
        return type;
    }
}