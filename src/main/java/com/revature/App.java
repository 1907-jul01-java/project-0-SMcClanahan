package com.revature;

import com.revature.Util.ConnectionUtil;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ConnectionUtil connection = new ConnectionUtil();
        BasicUser user = new BasicUser("sean", connection.getConnection());
        System.out.println(user.getUser());
    }
}
