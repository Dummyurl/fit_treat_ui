package code.utils;

import code.database.AppSettings;

public class AppUrls {

    //Demo URL
    //public static final String baseUrl = "http://trendytoday.in/kmc_development/api/";

    //Live URL
    public static final String baseUrl = "https://fittreat.cfapps.us10.hana.ondemand.com/";
    public static final String login = baseUrl+"auth/login";
    public static final String register = baseUrl+"auth/register";
    public static final String logout = baseUrl+"Logout";
    public static final String inbox = baseUrl+"api/reloadMessages/"+AppSettings.getString(AppSettings.userId);
    public static final String readMessage = baseUrl+"api/readMessage/"+AppSettings.getString(AppSettings.userId)+"/";


}
