package com.revature;

import com.revature.Util.*;
import com.revature.resources.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    BasicUser user;
    ConnectionUtil connection;
    @Before public void TestInit(){
    connection = new ConnectionUtil();
    Scanner input = new Scanner(System.in);
    user = new BasicUser(input, 1, connection.getConnection());
    }

    @Test
    public void TestUser(){
        assertNotNull(user);
        assertNotNull(connection);
        assertEquals(1, user.getUser(), 0);
    }

    @Test
    public void TestBalance(){
        //assertEquals(300, user.Balance("checking"),0);

    }
    
}

