package com.revature;

import com.revature.Util.*;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    BasicUser user;
    @Test public void TestInit(){
    ConnectionUtil connection = new ConnectionUtil();
    this.user = new BasicUser("sean", connection.getConnection());
    }

    @Test
    public void TestBalance(){
        assertEquals(300, user.Balance("checking"));

    }
    
}

