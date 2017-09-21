package code.github.utils;

import android.util.Log;

/**
 * Created by shank on 4/29/17.
 */


/**
 * Utility class for easy and extensive logging
 */
public class Logger {
    public static void debug(String message){
        Log.d("DEBUG", message);
    }

    public static void error(String error){
        Log.e("ERROR", error);
    }
}
