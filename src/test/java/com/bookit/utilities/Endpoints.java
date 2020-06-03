package com.bookit.utilities;

public class Endpoints {
    //if we have more end points probably better separate
    //each USER API utilities in a different package under bookit
    //like we have for database

    public static String DELETE_STUDENT = "/api/students/{id}";
    public static String ADD_STUDENT = "/api/students/student";
    public static String GET_ALL_BATCHES = "/api/batches";
    public static String GET_ME = "/api/students/me";
    public static String ADD_BATCH = "/api/batches/batch";
    public static String ADD_TEAM = "/api/teams/team";


}
