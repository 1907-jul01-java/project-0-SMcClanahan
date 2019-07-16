package com.revature.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private Connection connection;
    private String url;
    private String user;
    private String password;

    public ConnectionUtil() {
        try (InputStream input = new FileInputStream("src/main/java/com/revature/resources/connection.properties")){
            Properties properties = new Properties();
            properties.load(input);
            //properties.load(new FileReader("resources/connection.properties"));
            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
            //System.out.println(this.url);
            //System.out.println(this.password);
            //System.out.println(this.user);
            try{        
                DriverManager.getDriver(this.url);
                this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            } catch(NullPointerException e){
                e.getMessage();
            }
            //PrintStream connectionInfo = DriverManager.getLogStream();
            //connectionInfo.print(connectionInfo);
        } catch(SQLException e){
            e.getMessage();
        } catch(IOException e){
            e.getMessage();
        } catch(NullPointerException e){
            e.getMessage();
        }

    }

    public void close(){
        try{
            this.connection.close();
        }catch(SQLException e){

        }
    }

    public Connection getConnection(){
        return connection;
    }
}
