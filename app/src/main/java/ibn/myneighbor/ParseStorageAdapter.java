package ibn.myneighbor;

import android.content.Context;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ibn.myneighbor.Model.*;

//import com.parse.*;

/**
 * Created by ttnok on 25/2/2559.
 */
public class ParseStorageAdapter {

    public ParseStorageAdapter(Context context){
        // Enable Local Datastore.
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, "QIpqplsgqKagVuBBpUQVYLyHCXESIdZ5DOdKXLSM", "z9UeMLxOrquuG0NPf8q4yscvSkt91rW1taaWhZn1");

    }
//    public void testAddNewTaskToDB(){
//        //test
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("Blabla", "bagannnnnnnnn");
//        testObject.saveEventually();
////        testObject.saveInBackground();
//    }
//
    public void createUser (User u){
        ParseObject values = new ParseObject("User");
        values.put("USERNAME", u.getUsername());
        values.put("BIO", u.getBio());
        values.put("PROFILE_PIC_ID", u.getProfilePic());
        values.put("PASSWORD", u.getPassword());
        values.put("CREATED_AT", getDateTime());
        values.put("UPDATED_AT", getDateTime());
//        logAlert.saveInBackground();
        values.saveEventually(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Log.d("Nok", "finish log alert");
                } else {
                    // The save failed.
                    Log.d("Nok", "createLogAlert: " + e.getMessage());
                }
            }
        });
        ;
    }
//
//    public void createLogTaskComplete (int user_id, Task task){
//        ParseObject logTaskComplete = new ParseObject("LogCompleteTask");
//        logTaskComplete.put("USER_ID", user_id);
//        logTaskComplete.put("TASK_NAME", task.getName());
//        logTaskComplete.put("TIMESTAMP", LocalDateTime.now().toString());
////        logTaskComplete.saveInBackground();
//        logTaskComplete.saveEventually(new SaveCallback() {
//            public void done(ParseException e) {
//                if (e == null) {
//                    // Saved successfully.
//                    Log.d("Nok", "finish log task complete");
//                } else {
//                    // The save failed.
//                    Log.d("Nok", "createLogTaskComplete: "+ e.getMessage());
//                }
//            }
//        });;
//    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }
}
