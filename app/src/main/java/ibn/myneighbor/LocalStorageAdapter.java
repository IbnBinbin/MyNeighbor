package ibn.myneighbor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ibn.myneighbor.Model.Activity;
import org.joda.time.LocalTime;
import android.util.Log;
import java.text.SimpleDateFormat;

/**
 * Created by ttnok on 21/2/2559.
 */

public class LocalStorageAdapter extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "Ibn";//LocalStorageAdapter.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myneighbor";

    // Table Names
    private static final String TABLE_CONVERSATION = "conversation";
    private static final String TABLE_USER = "user";
    private static final String TABLE_ACTIVITY = "activity";
    private static final String TABLE_GROUP = "privategroup";
    private static final String TABLE_NEIGHBORHOOD = "neighborhood";

    // Column names
    private static final String KEY_ID = "id";
    private static final String KEY_TOPIC = "topic";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_CLIENT = "client";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_CHAT_MESSAGE = "chat_message";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_BIO = "bio";
    private static final String KEY_PROFILE_PIC_ID = "profile_pic_id";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_UPDATED_AT = "updated_at";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_REQUEST_OR_OFFER = "request_or_offer";
    private static final String KEY_GROUP_NAME = "group_name";
    private static final String KEY_EXPIRT_DATE = "expire_date";

    private static final String KEY_MEMBER = "member";

    private static final String KEY_INITIAL_POINT = "initial_point";
    private static final String KEY_FINAL_POINT = "final_point";
    private static final String KEY_DRAW_TYPE = "draw_type";

    // Table Create Statements
    // Conversation table create statement
    private static final String CREATE_TABLE_CONVERSATION = "CREATE TABLE " + TABLE_CONVERSATION
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TOPIC + " TEXT," + KEY_OWNER + " TEXT,"
            + KEY_CLIENT + " TEXT," + KEY_CREATED_AT + " DATETIME," + KEY_CHAT_MESSAGE + " TEXT" + ")";
    // User table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME + " TEXT," + KEY_BIO
            + " TEXT," + KEY_PROFILE_PIC_ID + " INTEGER," + KEY_PASSWORD + " TEXT," + KEY_CREATED_AT + " DATETIME,"
            + KEY_UPDATED_AT + " DATETIME" + ")";

    // Activity table create statement
    private static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + TABLE_ACTIVITY
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
            + KEY_REQUEST_OR_OFFER + " INTEGER," + KEY_GROUP_NAME + " INTEGER," + KEY_EXPIRT_DATE + " DATETIME,"
            + KEY_CREATED_AT + " DATETIME," + KEY_UPDATED_AT + " DATETIME," + KEY_OWNER + " TEXT" + ")";

    // Group table create statement
    private static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUP
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_OWNER + " TEXT,"
            + KEY_GROUP_NAME + " TEXT," + KEY_MEMBER + " TEXT" + ")";

    // Activity table create statement
    private static final String CREATE_TABLE_NEIGHBORHOOR = "CREATE TABLE " + TABLE_NEIGHBORHOOD
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_INITIAL_POINT + " TEXT," + KEY_FINAL_POINT + " TEXT,"
            + KEY_DRAW_TYPE + " INTEGER," + KEY_OWNER + " TEXT" + ")";


    public LocalStorageAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CONVERSATION);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_ACTIVITY);
        db.execSQL(CREATE_TABLE_GROUP);
        db.execSQL(CREATE_TABLE_NEIGHBORHOOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEIGHBORHOOD);
        // create new tables
        onCreate(db);
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONVERSATION, null, null);
        db.delete(TABLE_USER, null, null);
        db.delete(TABLE_ACTIVITY, null, null);
        db.delete(TABLE_GROUP, null, null);
        db.delete(TABLE_NEIGHBORHOOD, null, null);
    }

    public long createUser(String userName, String bio, int profilePic, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, userName);
        values.put(KEY_BIO, bio);
        values.put(KEY_PROFILE_PIC_ID, profilePic);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_UPDATED_AT, getDateTime());
        long check = db.insert(TABLE_USER, null, values);

        return check;
    }

    //    public long createActivity(String title, String desc, int req_offer, String groupName, Date expire, String owner) {
    public long createActivity(Activity a) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, a.getTitle());
        values.put(KEY_DESCRIPTION, a.getDescription());
        values.put(KEY_REQUEST_OR_OFFER, a.getRequestOrOffer());
        values.put(KEY_GROUP_NAME, a.getGroupName());
        values.put(KEY_EXPIRT_DATE, getDateTime(a.getexpireDate()));
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_UPDATED_AT, getDateTime());
        values.put(KEY_OWNER, a.getOwner());
        long check = db.insert(TABLE_ACTIVITY, null, values);

        return check;
    }

    public long createGroup(String owner, String groupName, String member) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OWNER, owner);
        values.put(KEY_GROUP_NAME, groupName);
        values.put(KEY_MEMBER, member);
        long check = db.insert(TABLE_GROUP, null, values);

        return check;
    }

    public long createNeighborhood(String init, String finalP, int drawType, String owner) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INITIAL_POINT, init);
        values.put(KEY_FINAL_POINT, finalP);
        values.put(KEY_DRAW_TYPE, drawType);
        values.put(KEY_OWNER, owner);
        long check = db.insert(TABLE_NEIGHBORHOOD, null, values);

        return check;
    }

    public int updateActivity(int id, String title, String desc, int req_offer, String groupName, Date expire, String owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_DESCRIPTION, desc);
        values.put(KEY_REQUEST_OR_OFFER, req_offer);
        values.put(KEY_GROUP_NAME, groupName);
        values.put(KEY_EXPIRT_DATE, getDateTime(expire));
        values.put(KEY_UPDATED_AT, getDateTime());
        values.put(KEY_OWNER, owner);
//        Log.d("Nok", "update task: "+values.valueSet());
        return db.update(TABLE_ACTIVITY, values, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int updateUser(int id, String userName, String bio, int profilePic, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, userName);
        values.put(KEY_BIO, bio);
        values.put(KEY_PROFILE_PIC_ID, profilePic);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_UPDATED_AT, getDateTime());
//        Log.d("Nok", "update task: "+values.valueSet());
        return db.update(TABLE_USER, values, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Activity> getActivity(String group) throws ParseException {
        ArrayList<Activity> listActivity = new ArrayList<Activity>();
        String selectQuery = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE "
                + KEY_GROUP_NAME + " = '" + group+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Log.d("Ibn", c.getString(c.getColumnIndex(KEY_TITLE)));
//                Log.d("Ibn", c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
//                Log.d("Ibn", c.getString(c.getColumnIndex(KEY_REQUEST_OR_OFFER)));
//                Log.d("Ibn", c.getString(c.getColumnIndex(KEY_GROUP_NAME)));
//                Log.d("Ibn", c.getString(c.getColumnIndex(KEY_EXPIRT_DATE)));
//                Log.d("Ibn", c.getString(c.getColumnIndex(KEY_OWNER)));
                String tmp_date =c.getString(c.getColumnIndex(KEY_EXPIRT_DATE));
                Date d =formatter.parse(tmp_date);
                listActivity.add(new Activity(c.getString(c.getColumnIndex(KEY_TITLE)), c.getString(c.getColumnIndex(KEY_DESCRIPTION)), c.getInt(c.getColumnIndex(KEY_REQUEST_OR_OFFER)), c.getString(c.getColumnIndex(KEY_GROUP_NAME)), d, c.getString(c.getColumnIndex(KEY_OWNER))));
            } while (c.moveToNext());
        }
//        Activity a = new Activity()
        return listActivity;
    }
//    public List<Integer> getAllAlarmIDs(int occurrence_id) {
//        ArrayList<Integer> alarmIDs = new ArrayList<Integer>();
//        String selectQuery = "SELECT  * FROM " + TABLE_ALARMIDS + " WHERE "
//                + KEY_OCCURRENCE_ID + " = " + occurrence_id;
//        String selectQuery = "SELECT  * FROM " + TABLE_OCCURRENCES +" WHERE "+KEY_REMINDER_ID +"="+reminder_id;
//        String selectQuery = "SELECT  * FROM " + TABLE_TASKS +" WHERE "+KEY_REMINDER_ID +"="+reminder_id;
//
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c.moveToFirst()) {
//            do {
//                alarmIDs.add(c.getInt(c.getColumnIndex(KEY_VALUE_ALAREMID)));
//            } while (c.moveToNext());
//        }
//
//        return alarmIDs;
//    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     */
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
