package edu.uta.edu.uta.utils;

/**
 * Created by gaurav on 11/23/15.
 */
public class AppUrls {
    final static String BASE_URL = "http://192.168.0.7:8000/";


    final  static String LOGIN_URL = BASE_URL+"login";
    final static String LOGOUT_URL = BASE_URL+"logout";
    final static String REGISTER_URL = BASE_URL+"register";

    final static String GET_USER_URL = BASE_URL+"users/";

    public static String getGetUserUrl(int userid) {
        return GET_USER_URL+userid;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    public static String getLogoutUrl() {
        return LOGOUT_URL;
    }

    public static String getRegisterUrl() {
        return REGISTER_URL;
    }
}
