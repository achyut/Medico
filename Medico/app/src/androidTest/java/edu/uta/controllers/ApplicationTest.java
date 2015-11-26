package edu.uta.controllers;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;

import edu.uta.entities.User;
import edu.uta.managers.RequestManager;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends InstrumentationTestCase {

    /*public void test(){
        RequestManager requestManager = new RequestManager();

        User usr = (User) requestManager.requestGET("users/1", "TAG_REGISTER", User.class,null);
        assertNotNull(usr);

        if(usr!=null){
            System.out.println(usr);
        }
        else{
            System.out.println("error");
        }
    }
    */
}