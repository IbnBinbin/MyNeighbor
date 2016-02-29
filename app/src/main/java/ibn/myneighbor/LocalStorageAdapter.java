package ibn.myneighbor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ibn.myneighbor.Model.*;

import android.util.Log;
/**
 * Created by ttnok on 21/2/2559.
 */

public class LocalStorageAdapter extends SQLiteOpenHelper {
    //    private  Context context;
    private ParseStorageAdapter cloudDB;

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

    // Neighborhood table create statement
    private static final String CREATE_TABLE_NEIGHBORHOOR = "CREATE TABLE " + TABLE_NEIGHBORHOOD
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_INITIAL_POINT + " TEXT," + KEY_FINAL_POINT + " TEXT,"
            + KEY_DRAW_TYPE + " INTEGER," + KEY_OWNER + " TEXT" + ")";


    public LocalStorageAdapter() {
        super(MyApp.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Ibn", "app context: " + MyApp.getAppContext().getPackageName());
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

//        cloudDB = MyApp.getDBcloud();
//        cloudDB.deleteAllData("User");
//        cloudDB.deleteAllData("Conversation");
//        cloudDB.deleteAllData("Activity");
//        cloudDB.deleteAllData("Group");
//        cloudDB.deleteAllData("Neighborhood");
    }

    public long createUser(User u, boolean isFromLocal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, u.getUsername());
        values.put(KEY_BIO, u.getBio());
        values.put(KEY_PROFILE_PIC_ID, u.getProfilePic());
        values.put(KEY_PASSWORD, u.getPassword());
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_UPDATED_AT, getDateTime());
        long check = db.insert(TABLE_USER, null, values);
        u.setID((int)check);
        cloudDB = MyApp.getDBcloud();
        if (isFromLocal) {
            cloudDB.createUser(u);
        }
        return check;
    }


    //    public long createActivity(String title, String desc, int req_offer, String groupName, Date expire, String owner) {
    public long createActivity(Activity a, boolean isFromLocal) {

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
        a.setID((int)check);
        cloudDB = MyApp.getDBcloud();
        if (isFromLocal) {
            cloudDB.createActivity(a);
        }
        return check;
    }

    public long createGroup(Group g, boolean isFromLocal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OWNER, g.getOwner());
        values.put(KEY_GROUP_NAME, g.getGroupName());
        values.put(KEY_MEMBER, g.getMember());
        long check = db.insert(TABLE_GROUP, null, values);
        g.setID((int)check);
        cloudDB = MyApp.getDBcloud();
        if (isFromLocal) {
            cloudDB.createGroup(g);
        }
        return check;
    }

    public long createNeighborhood(Neighborhood n, boolean isFromLocal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INITIAL_POINT, n.getInitialPoint());
        values.put(KEY_FINAL_POINT, n.getFinalPoint());
        values.put(KEY_DRAW_TYPE, n.getDrawType());
        values.put(KEY_OWNER, n.getOwner());
        long check = db.insert(TABLE_NEIGHBORHOOD, null, values);
        n.setID((int) check);
        cloudDB = MyApp.getDBcloud();
        if (isFromLocal) {
            cloudDB.createNeighborhood(n);
        }
        return check;
    }

    public long createConversation(Conversation c, boolean isFromLocal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TOPIC, c.getTopic());
        values.put(KEY_OWNER, c.getOwner());
        values.put(KEY_CLIENT, c.getClient());
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_CHAT_MESSAGE, c.getChatMessage());
        long check = db.insert(TABLE_CONVERSATION, null, values);
        c.setID((int) check);
        cloudDB = MyApp.getDBcloud();
        if (isFromLocal) {
            cloudDB.createConversation(c);
        }
        return check;
    }

    public int checkActivityExistOnLocalDB(Activity a) { //check if exist or not
        String selectQuery;
        selectQuery = "SELECT * FROM " + TABLE_ACTIVITY +" WHERE "+KEY_TITLE+"= '"+a.getTitle()+"' and "+KEY_OWNER+" = '"+a.getOwner()+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int isRowExist = 1;
        if (c.moveToFirst()) {
            do {
                Log.d("Ibn...", c.getInt(c.getColumnIndex(KEY_ID)) + " " + c.getString(c.getColumnIndex(KEY_TITLE)));
            } while (c.moveToNext());
//            createActivity(a, false);
//            isRowExist = 0;
        }else{
            Log.d("Ibn...", c.getCount()+"");
            createActivity(a, false);
            isRowExist = 0;
        }

        return isRowExist;
    }

    public int checkUserExistOnLocalDB(User u) { //check if exist or not
        String selectQuery;
        selectQuery = "SELECT * FROM " + TABLE_USER +" WHERE "+KEY_USERNAME+"= '"+u.getUsername()+"' and "+KEY_ID+ " = "+u.getID();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int isRowExist = 1;
        if (!c.moveToFirst()) {
            createUser(u, false);
            isRowExist = 0;
        }

        return isRowExist;
    }

    public int checkNeighborhoodExistOnLocalDB(Neighborhood n) { //check if exist or not
        String selectQuery;
        selectQuery = "SELECT * FROM " + TABLE_NEIGHBORHOOD +" WHERE "+KEY_OWNER+"= '"+n.getOwner()+"' and "+KEY_FINAL_POINT+ " = '"+n.getFinalPoint()+"' and "+KEY_DRAW_TYPE+ " = "+n.getDrawType();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int isRowExist = 1;
        if (!c.moveToFirst()) {
            createNeighborhood(n, false);
            isRowExist = 0;
        }

        return isRowExist;
    }

    public int checkGroupExistOnLocalDB(Group g) { //check if exist or not
        String selectQuery;
        selectQuery = "SELECT * FROM " + TABLE_GROUP +" WHERE "+KEY_OWNER+"= '"+g.getOwner()+"' and "+KEY_ID+ " = "+g.getID();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int isRowExist = 1;
        if (!c.moveToFirst()) {
            createGroup(g, false);
            isRowExist = 0;
        }

        return isRowExist;
    }

    public int checkConversationExistOnLocalDB(Conversation c) { //check if exist or not
        String selectQuery;
        selectQuery = "SELECT * FROM " + TABLE_CONVERSATION +" WHERE "+KEY_OWNER+"= '"+c.getOwner()+"' AND "+KEY_CLIENT+" = '"+c.getClient()+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery(selectQuery, null);
        int isRowExist = 1;
        if (!c1.moveToFirst()) {
            createConversation(c, false);
            isRowExist = 0;
        }

        return isRowExist;
    }

//    public int updateUser(User u) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_USERNAME, u.getUsername());
//        values.put(KEY_BIO, u.getBio());
//        values.put(KEY_PROFILE_PIC_ID, u.getProfilePic());
//        values.put(KEY_PASSWORD, u.getPassword());
//        values.put(KEY_UPDATED_AT, getDateTime());
////        Log.d("Nok", "update task: "+values.valueSet());
////        db.update(TABLE_NAME, contentValues NAME + " = ? AND " + LASTNAME + " = ?", new String[]{"Manas", "Bajaj"});
//        int checkRowUpdate = db.update(TABLE_USER, values, KEY_ID+" = ? AND " + KEY_CREATED_AT + " = ? AND "+KEY_USERNAME+" =?", new String[]{String.valueOf(u.getID()), String.valueOf(u.getCreatedDate()), String.valueOf(u.getUsername())});
//        if(checkRowUpdate == 0){
//            createUser(u, false);
//        }
//        return checkRowUpdate;
//    }



    public ArrayList<Activity> getActivity(String group) throws ParseException {
        updateActivityFromCloud();
        ArrayList<Activity> listActivity = new ArrayList<Activity>();
        String selectQuery;
        if (group.equals("all")) {
            selectQuery = "SELECT * FROM " + TABLE_ACTIVITY + " ORDER BY " + KEY_CREATED_AT + " DESC";
        } else {
            selectQuery = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE "
                    + KEY_GROUP_NAME + " = '" + group + "' ORDER BY " + KEY_CREATED_AT + " DESC";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String tmp_date = c.getString(c.getColumnIndex(KEY_EXPIRT_DATE));
                String tmp_Cdate = c.getString(c.getColumnIndex(KEY_CREATED_AT));
                Date d = formatter.parse(tmp_date);
                Date dC = formatter.parse(tmp_Cdate);
                Log.d("Ibn","activity: "+c.getString(c.getColumnIndex(KEY_TITLE)));
                listActivity.add(new Activity(c.getString(c.getColumnIndex(KEY_TITLE)), c.getString(c.getColumnIndex(KEY_DESCRIPTION)), c.getInt(c.getColumnIndex(KEY_REQUEST_OR_OFFER)), c.getString(c.getColumnIndex(KEY_GROUP_NAME)), d, dC, c.getString(c.getColumnIndex(KEY_OWNER))));
            } while (c.moveToNext());
        }
//        Activity a = new Activity()
        return listActivity;
    }

    private void updateActivityFromCloud() { //delete data from local and load from cloud >> check the connection before delete
        cloudDB = MyApp.getDBcloud();
        cloudDB.getActivityToUpdateLocal();
    }

    public ArrayList<Group> getGroups() {
        updateGroupFromCloud();
        ArrayList<Group> groups = new ArrayList<Group>();
        String username = MyApp.getUsername();
        String selectQuery = "SELECT DISTINCT " + KEY_GROUP_NAME + ", " + KEY_OWNER + ", " + KEY_ID + ", " + KEY_MEMBER + " FROM " + TABLE_GROUP + " WHERE "
                + KEY_OWNER+ " = '" + username + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                groups.add(new Group(c.getInt(c.getColumnIndex(KEY_ID)), c.getString(c.getColumnIndex(KEY_OWNER)), c.getString(c.getColumnIndex(KEY_GROUP_NAME)), c.getString(c.getColumnIndex(KEY_MEMBER))));
            } while (c.moveToNext());
        }
        return groups;
    }

    private void updateGroupFromCloud() {
        cloudDB = MyApp.getDBcloud();
        cloudDB.getGroupToUpdateLocal();

    }

    public ArrayList<User> getUser(String owner) {
        updateUserFromCloud();
        ArrayList<User> listUser = new ArrayList<User>();
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                + KEY_USERNAME + " = '" + owner + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                listUser.add(new User(c.getString(c.getColumnIndex(KEY_USERNAME)), c.getString(c.getColumnIndex(KEY_BIO)), c.getInt(c.getColumnIndex(KEY_PROFILE_PIC_ID)), c.getString(c.getColumnIndex(KEY_PASSWORD))));
            } while (c.moveToNext());
        }

        return listUser;
    }

    private void updateUserFromCloud() {
        cloudDB = MyApp.getDBcloud();
        cloudDB.getUserToUpdateLocal();
    }

    public ArrayList<Activity> getPersonalActivity(String owner) throws ParseException {
        updateActivityFromCloud();
        ArrayList<Activity> listActivity = new ArrayList<Activity>();
        String selectQuery = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE "
                + KEY_OWNER + " = '" + owner + "'" + " ORDER BY " + KEY_CREATED_AT + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        cloudDB = MyApp.getDBcloud();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String tmp_date = c.getString(c.getColumnIndex(KEY_EXPIRT_DATE));
                String tmp_Cdate = c.getString(c.getColumnIndex(KEY_CREATED_AT));
                Date d = formatter.parse(tmp_date);
                Date dC = formatter.parse(tmp_Cdate);
                listActivity.add(new Activity(c.getString(c.getColumnIndex(KEY_TITLE)), c.getString(c.getColumnIndex(KEY_DESCRIPTION)), c.getInt(c.getColumnIndex(KEY_REQUEST_OR_OFFER)), c.getString(c.getColumnIndex(KEY_GROUP_NAME)), d, dC, c.getString(c.getColumnIndex(KEY_OWNER))));
            } while (c.moveToNext());
        }

        return listActivity;

    }

    public ArrayList<Conversation> getAllConversation(String user) {
        updateConversationFromCloud();
        ArrayList<Conversation> listAllConversation = new ArrayList<Conversation>();
        String selectQuery = "SELECT * FROM " + TABLE_CONVERSATION + " WHERE "
                + KEY_CLIENT + " = '" + user + "' OR " + KEY_OWNER + " = '" + user + "' GROUP BY " + KEY_OWNER + ", " + KEY_TOPIC + " ORDER BY " + KEY_CREATED_AT + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            Log.d("Ibn", "query");
            do {
                if (user.equals(c.getString(c.getColumnIndex(KEY_CLIENT)))) {
                    Log.d("Ibn", "Client "+ listAllConversation.size());
                    if (listAllConversation.size() > 0) {
                        for (int i = 0; i < listAllConversation.size(); i++) {
                            Log.d("Ibn", i + ": " + c.getString(c.getColumnIndex(KEY_TOPIC))+ " "+listAllConversation.get(i).getOwner()+" "+c.getString(c.getColumnIndex(KEY_OWNER)));
                            if (listAllConversation.get(i).getOwner().equals(c.getString(c.getColumnIndex(KEY_OWNER))) && listAllConversation.get(i).getTopic().equals(c.getString(c.getColumnIndex(KEY_TOPIC)))) {
                                Log.d("Ibn","...");
                                break;
                            }else if(i==listAllConversation.size()-1){
                                Log.d("Ibn", "..." + c.getString(c.getColumnIndex(KEY_TOPIC)));
                                listAllConversation.add(new Conversation(c.getString(c.getColumnIndex(KEY_TOPIC)), c.getString(c.getColumnIndex(KEY_OWNER)), c.getString(c.getColumnIndex(KEY_CLIENT)), c.getString(c.getColumnIndex(KEY_CHAT_MESSAGE))));
                                break;
                            }
                        }
                    } else {
                        Log.d("Ibn", "" + c.getString(c.getColumnIndex(KEY_TOPIC)));
                        listAllConversation.add(new Conversation(c.getString(c.getColumnIndex(KEY_TOPIC)), c.getString(c.getColumnIndex(KEY_OWNER)), c.getString(c.getColumnIndex(KEY_CLIENT)), c.getString(c.getColumnIndex(KEY_CHAT_MESSAGE))));
                    }
                } else {
                    Log.d("Ibn", "Owner "+listAllConversation.size());
                    if (listAllConversation.size() > 0) {
                        for (int i = 0; i < listAllConversation.size(); i++) {
                            Log.d("Ibn", i + ": " + c.getString(c.getColumnIndex(KEY_TOPIC)));
                            if ((listAllConversation.get(i).getOwner().equals(c.getString(c.getColumnIndex(KEY_CLIENT))) && listAllConversation.get(i).getTopic().equals(c.getString(c.getColumnIndex(KEY_TOPIC))))) {
                                break;
                            }else if(i==listAllConversation.size()-1){
                                Log.d("Ibn", "..." + c.getString(c.getColumnIndex(KEY_TOPIC)));

//                                if (listAllConversation.get(i).getClient().equals(c.getString(c.getColumnIndex(KEY_OWNER))) && listAllConversation.get(i).getTopic().equals(c.getString(c.getColumnIndex(KEY_TOPIC)))) {
                                listAllConversation.add(new Conversation(c.getString(c.getColumnIndex(KEY_TOPIC)), c.getString(c.getColumnIndex(KEY_CLIENT)), c.getString(c.getColumnIndex(KEY_OWNER)), c.getString(c.getColumnIndex(KEY_CHAT_MESSAGE))));
                                break;
                            }
                        }
                    } else {
                        Log.d("Ibn", "" + c.getString(c.getColumnIndex(KEY_TOPIC)));
                        listAllConversation.add(new Conversation(c.getString(c.getColumnIndex(KEY_TOPIC)), c.getString(c.getColumnIndex(KEY_CLIENT)), c.getString(c.getColumnIndex(KEY_OWNER)), c.getString(c.getColumnIndex(KEY_CHAT_MESSAGE))));
                    }
                }
            } while (c.moveToNext());
        }

        db.close();

        return listAllConversation;
    }

    private void updateConversationFromCloud() {
        cloudDB = MyApp.getDBcloud();
        cloudDB.getConversationToUpdateLocal();
    }

    public ArrayList<Conversation> getSpecificConversation(String owner, String topic, String user) {
        updateConversationFromCloud();
        ArrayList<Conversation> listAllConversation = new ArrayList<Conversation>();
        String selectQuery = "SELECT * FROM " + TABLE_CONVERSATION + " WHERE ("
                + KEY_OWNER + " = '" + owner + "'" + " AND " + KEY_TOPIC + " = '" + topic + "' AND " + KEY_CLIENT + " = '" + user + "') OR (" + KEY_OWNER + " = '" + user + "'" + " AND " + KEY_TOPIC + " = '" + topic + "' AND " + KEY_CLIENT + " = '" + owner + "')" + " ORDER BY " + KEY_CREATED_AT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                listAllConversation.add(new Conversation(c.getString(c.getColumnIndex(KEY_TOPIC)), c.getString(c.getColumnIndex(KEY_OWNER)), c.getString(c.getColumnIndex(KEY_CLIENT)), c.getString(c.getColumnIndex(KEY_CHAT_MESSAGE))));
            } while (c.moveToNext());
        }

        return listAllConversation;
    }

    public ArrayList<Neighborhood> getNeighborhood(String username) {
//        updateNeighborhoodFromCloud();
        ArrayList<Neighborhood> nbMarks = new ArrayList<Neighborhood>();
        String selectQuery = "SELECT * FROM " + TABLE_NEIGHBORHOOD + " WHERE "
                + KEY_OWNER + " = '" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Log.d("Ibn",c.getString(c.getColumnIndex(KEY_INITIAL_POINT))+" "+c.getColumnIndex(KEY_FINAL_POINT)+" "+c.getColumnIndex(KEY_DRAW_TYPE));
                nbMarks.add(new Neighborhood(c.getString(c.getColumnIndex(KEY_INITIAL_POINT)), c.getString(c.getColumnIndex(KEY_FINAL_POINT)), Integer.parseInt(c.getString(c.getColumnIndex(KEY_DRAW_TYPE))), c.getString(c.getColumnIndex(KEY_OWNER))));
            } while (c.moveToNext());
        }

        return nbMarks;
    }

    private void updateNeighborhoodFromCloud() {
        cloudDB = MyApp.getDBcloud();
        cloudDB.getNeighborhoodToUpdateLocal();
    }

    public boolean checkUser(String user){
        ArrayList<User> listUser = new ArrayList<User>();
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                + KEY_USERNAME + " = '" + user + "'";
        boolean check=false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                check = true;
            } while (c.moveToNext());
        }

        return check;
    }


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
        if(date == null){
            date = new Date();
        }
        return dateFormat.format(date);
    }


}