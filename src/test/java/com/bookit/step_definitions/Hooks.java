package com.bookit.step_definitions;

import com.bookit.utilities.DBUtility;
import com.bookit.utilities.Driver;
import com.bookit.utilities.Environment;
import io.cucumber.java.After; // it has to be from cucumber! from JUNIt will not be recognized by cucumber
import io.cucumber.java.Before;


public class Hooks {

    //HOOKS without tax will be activated before each and every test!

    /**
     * This hook will be executed only for scenarios that are annotated with @db tag
     * it will connect to your database
     */
    @Before("@db")
    public void dbSetup(){
        DBUtility.createDBConnection(Environment.DB_HOST, Environment.DB_USERNAME, Environment.DB_PASSWORD);
    }

    /**
     * This hook will be executed only for scenarios that are annotated with @db tag
     */
    @After("@db")
    public void dbTearDown(){
        DBUtility.destroy();
    }

    /**
     * This hook will be executed only for scenarios that are annotated with @ui tag
     */
    @Before("ui")
    public void uiSetup(){
        Driver.getDriver().manage().window().maximize();
    }

    /**
     * This hook will be executed only for scenarios that are annotated with @ui tag
     */
    @After ("@ui") //how to trigger this for our UI test
    public void uiTearDown(){
        Driver.closeDriver();
    }
}
