package com.danielgomez.mindfacts;


import android.util.Log;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MindFacts extends android.app.Application {
    public static final int LOG_LEVEL_DEBUG = 0;
    public final int LOG_LEVEL_RELEASE = 1;
    public static int LOG_LEVEL = LOG_LEVEL_DEBUG;
    private static final String LOG_TAG = "Mind Facts";
    static TwitterFactory tf;

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("CFkybEm6aFbQG3yB0JtmKtxnp")
                .setOAuthConsumerSecret(
                        "fKibwXCZoukuRiqgOlDL0XfRImNr8xgIxmNpYLYsFyU6RSk2f3")
                .setOAuthAccessToken(
                        "124085278-UhdOHWrLG39virrPN8w45DddtAj764IoBBN3uwO0")
                .setOAuthAccessTokenSecret(
                        "nOGBZMo3AVqMCWUbquK71kgnjG2crt739qsoLpTRrn1fF");
        tf = new TwitterFactory(cb.build());
    }

    public static TwitterFactory getTwitterFactory(){
        return tf;
    }

    public static void log(String message){
        if(LOG_LEVEL == LOG_LEVEL_DEBUG){
            Log.d(LOG_TAG, message);
        }
    }

    public static void logError(String message, Throwable e){
        if(LOG_LEVEL == LOG_LEVEL_DEBUG){
            Log.e(LOG_TAG, message, e);
        }
    }
}
