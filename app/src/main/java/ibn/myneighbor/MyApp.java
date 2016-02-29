package ibn.myneighbor;

/**
 * Created by ttnok on 25/2/2559.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MyApp extends Application {
    private static Application instance;
    private static SharedPreferences prefs = null;
    private static Context context;
    private static ParseStorageAdapter db_cloud;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences("ibn.myneighbor", MODE_PRIVATE);
        instance = this;
//        Log.d("Ibn", "app context: " + instance.getPackageName());

        MyApp.context = getApplicationContext();
        db_cloud = new ParseStorageAdapter();
    }

    public static void initOnBroadCastReceiver(Context context) {
        if(instance == null) {
            instance = new MyApp();
//            Log.d("Ibn", "app context init: " + instance.getPackageName());

        }
        if(prefs == null ) {
            prefs = context.getSharedPreferences("ibn.myneighbor", MODE_PRIVATE);
        }
        if(context == null) {
            MyApp.context = context;
        }

    }

    public static Context getContext() {
        if(instance == null) {
            return instance.getBaseContext();
        } else {
            return context;
        }
    }

    public static String getUsername() {
        return prefs.getString("username", "NULL");
    }

    public static void setUsername(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public static SharedPreferences getPrefs() {
        return MyApp.prefs;
    }

    public static Context getAppContext() {
        return MyApp.context;
    }

    public static ParseStorageAdapter getDBcloud() {
        return db_cloud;
    }

}