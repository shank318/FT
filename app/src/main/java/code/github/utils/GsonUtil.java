package code.github.utils;

import com.google.gson.Gson;

/**
 * Created by shank on 4/29/17.
 */


/**
 * Singleton class for GSON
 */
public class GsonUtil {

    private static Gson gson;

    public static Gson getGson(){
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }
}
