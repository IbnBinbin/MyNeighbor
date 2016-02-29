package ibn.myneighbor;

import android.app.Application;
import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ibn.myneighbor.Model.*;

//import com.parse.*;

/**
 * Created by ttnok on 25/2/2559.
 */
public class ParseStorageAdapter extends Application {

    public ParseStorageAdapter() {
        // Enable Local Datastore.
//        Log.d("Ibn", "in parse");
        Parse.enableLocalDatastore(MyApp.getAppContext());
        Parse.initialize(MyApp.getAppContext(), "QIpqplsgqKagVuBBpUQVYLyHCXESIdZ5DOdKXLSM", "z9UeMLxOrquuG0NPf8q4yscvSkt91rW1taaWhZn1");

    }

        public void testAddNewTaskToDB(){
        //test
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("Blabla", "bagannnnnnnnn");
        testObject.saveEventually();
//        testObject.saveInBackground();
    }
//
    public void createUser(User u) {
        ParseObject values = new ParseObject("User");
        values.put("ID", u.getID());
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
                    Log.d("Ibn", "finish user table");
                } else {
                    // The save failed.
                    Log.d("Ibn", "createUserTable: " + e.getMessage());
                }
            }
        });

    }

    public void createActivity(Activity a) {
        ParseObject values = new ParseObject("Activity");
        values.put("ID", a.getID());
        values.put("TITLE", a.getTitle());
        values.put("DESCRIPTION", a.getDescription());
        values.put("REQUEST_OR_OFFER", a.getRequestOrOffer());
        values.put("GROUP_NAME", a.getGroupName());
        values.put("EXPIRT_DATE", getDateTime(a.getexpireDate()));
        values.put("CREATED_AT", getDateTime(a.getCreatedDate()));
        values.put("UPDATED_AT", getDateTime());
        values.put("OWNER", a.getOwner());
//        logAlert.saveInBackground();
        values.saveEventually(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Log.d("Ibn", "finish activity table");
                } else {
                    // The save failed.
                    Log.d("Ibn", "createActivityTable: " + e.getMessage());
                }
            }
        });

    }

    public void createGroup(Group g) {
        ParseObject values = new ParseObject("Group");
        values.put("ID", g.getID());
        values.put("OWNER", g.getOwner());
        values.put("GROUP_NAME", g.getGroupName());
        values.put("MEMBER", g.getMember());
//        logAlert.saveInBackground();
        values.saveEventually(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Log.d("Ibn", "finish group table");
                } else {
                    // The save failed.
                    Log.d("Ibn", "createGroupTable: " + e.getMessage());
                }
            }
        });

    }

    public void createNeighborhood(Neighborhood n) {
        ParseObject values = new ParseObject("Neighborhood");
        values.put("ID", n.getID());
        values.put("INITIAL_POINT", n.getInitialPoint());
        values.put("FINAL_POINT", n.getFinalPoint());
        values.put("DRAW_TYPE", n.getDrawType());
        values.put("OWNER", n.getOwner());
//        logAlert.saveInBackground();
        values.saveEventually(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Log.d("Ibn", "finish neighborhood table");
                } else {
                    // The save failed.
                    Log.d("Ibn", "createNeighborhoodTable: " + e.getMessage());
                }
            }
        });

    }

    public void createConversation(Conversation c) {
        ParseObject values = new ParseObject("Conversation");
        values.put("ID", c.getID());
        values.put("TOPIC", c.getTopic());
        values.put("OWNER", c.getOwner());
        values.put("CLIENT", c.getClient());
        values.put("CREATED_AT", getDateTime());
        values.put("CHAT_MESSAGE", c.getChatMessage());
//        logAlert.saveInBackground();
        values.saveEventually(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Log.d("Ibn", "finish conversation table");
                } else {
                    // The save failed.
                    Log.d("Ibn", "createConversationTable: " + e.getMessage());
                }
            }
        });
        ;
    }


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


    public void getActivityToUpdateLocal() {
        final ArrayList<Activity> listObject = new ArrayList<Activity>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Activity");
//        try {
//            List<ParseObject> objects=query.find();
//            Log.d("Ibn","query: "+objects.size());
//            for (int i = 0; i < objects.size(); i++) {
//                ParseObject c = objects.get(i);
//                boolean check = listObject.add(new Activity(c.getString("TITLE"), c.getString("DESCRIPTION"), c.getInt("REQUEST_OR_OFFER"), c.getString("GROUP_NAME"), c.getDate("EXPIRT_DATE"), c.getDate("CREATED_AT"), c.getString("OWNER")));
//                Log.d("Ibn", i+" "+check+" "+listObject.size());
//
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // your logic here
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject c = objects.get(i);
                        boolean check = listObject.add(new Activity(c.getString("TITLE"), c.getString("DESCRIPTION"), c.getInt("REQUEST_OR_OFFER"), c.getString("GROUP_NAME"), c.getDate("EXPIRT_DATE"), c.getDate("CREATED_AT"), c.getString("OWNER")));
                        listObject.get(i).setID(c.getInt("ID"));
//                        Log.d("Ibn", i + " " + check + " " + listObject.size());
                    }
                    for (int i = 0; i < listObject.size(); i++) {
                        LocalStorageAdapter db = new LocalStorageAdapter();
                        db.checkActivityExistOnLocalDB(listObject.get(i)); //update local
                        db.closeDB();
                    }
                } else {
                    Log.d("Ibn", "Error get activity: " + e.getMessage());
                }
            }
        });
        Log.d("Ibn", "size: " + listObject.size());
        return;
    }

    public ArrayList<User> getUserToUpdateLocal() {
        final ArrayList<User> listObject = new ArrayList<User>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
//        try {
//            List<ParseObject> objects = query.find();
//            for (int i = 0; i < objects.size(); i++) {
//                ParseObject c = objects.get(i);
//                listObject.add(new User(c.getString("USERNAME"), c.getString("BIO"), c.getInt("PROFILE_PIC_ID"), c.getString("PASSWORD")));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // your logic here
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject c = objects.get(i);
                        listObject.add(new User(c.getString("USERNAME"), c.getString("BIO"), c.getInt("PROFILE_PIC_ID"), c.getString("PASSWORD")));
                        listObject.get(i).setID(c.getInt("ID"));
                    }
                    for (int i = 0; i < listObject.size(); i++) {
                        LocalStorageAdapter db = new LocalStorageAdapter();
                        db.checkUserExistOnLocalDB(listObject.get(i)); //update local
                        db.closeDB();
                    }
                } else {
                    Log.d("Ibn", "Error get user: " + e.getMessage());
                }
            }
        });
        return listObject;
    }

    public ArrayList<Conversation> getConversationToUpdateLocal() {
        final ArrayList<Conversation> listObject = new ArrayList<Conversation>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Conversation");
//        try {
//            List<ParseObject> objects = query.find();
//            for (int i = 0; i < objects.size(); i++) {
//                ParseObject c = objects.get(i);
//                listObject.add(new Conversation(c.getString("TOPIC"), c.getString("OWNER"), c.getString("CLIENT"), c.getString("MESSAGE")));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // your logic here
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject c = objects.get(i);
                        listObject.add(new Conversation(c.getString("TOPIC"), c.getString("OWNER"), c.getString("CLIENT"), c.getString("CHAT_MESSAGE")));
                        listObject.get(i).setID(c.getInt("ID"));
                    }
                    for (int i = 0; i < listObject.size(); i++) {
                        LocalStorageAdapter db = new LocalStorageAdapter();
                        db.checkConversationExistOnLocalDB(listObject.get(i)); //update local
                        db.closeDB();
                    }
                } else {
                    Log.d("Ibn", "Error get conversation: " + e.getMessage());
                }
            }
        });
        return listObject;
    }

    public ArrayList<Neighborhood> getNeighborhoodToUpdateLocal() {
        final ArrayList<Neighborhood> listObject = new ArrayList<Neighborhood>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Neighborhood");
//        try {
//            List<ParseObject> objects = query.find();
//            for (int i = 0; i < objects.size(); i++) {
//                ParseObject c = objects.get(i);
////                int type = c.getInt(c.getString("DRAW_TYPE"));
//                listObject.add(new Neighborhood(c.getString("POINT"), c.getString("FINAL_POINT"), c.getInt(c.getString("DRAW_TYPE")), c.getString("OWNER")));
//                listObject.get(i).setID(c.getInt("ID"));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // your logic here
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject c = objects.get(i);
                        listObject.add(new Neighborhood(c.getString("INITIAL_POINT"), c.getString("FINAL_POINT"), c.getInt("DRAW_TYPE"), c.getString("OWNER")));
                        listObject.get(i).setID(c.getInt("ID"));
//                        Log.d("Ibn",listObject.get(i).getOwner()+" "+listObject.get(i).getFinalPoint());
                    }
                    for (int i = 0; i < listObject.size(); i++) {
                        LocalStorageAdapter db = new LocalStorageAdapter();
                        db.checkNeighborhoodExistOnLocalDB(listObject.get(i)); //update local
                        db.closeDB();
                    }
                } else {
                    Log.d("Ibn", "Error get neighborhood: " + e.getMessage());
                }
            }
        });
        return listObject;
    }

    public ArrayList<Group> getGroupToUpdateLocal() {
        final ArrayList<Group> listObject = new ArrayList<Group>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Group");
        try {
            List<ParseObject> objects = query.find();
//            Log.d("Ibn", objects.size()+"");
            for (int i = 0; i < objects.size(); i++) {
                ParseObject c = objects.get(i);
                listObject.add(new Group(c.getInt("ID"), c.getString("OWNER"), c.getString("GROUP_NAME"), c.getString("MEMBER")));

            }
            for (int i = 0; i < listObject.size(); i++) {
                LocalStorageAdapter db = new LocalStorageAdapter();
                db.checkGroupExistOnLocalDB(listObject.get(i)); //update local
                db.closeDB();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    // your logic here
//                    for (int i = 0; i < objects.size(); i++) {
//                        ParseObject c = objects.get(i);
//                        listObject.add(new Group(c.getInt("ID"), c.getString("OWNER"), c.getString("GROUP_NAME"), c.getString("MEMBER")));
//                    }
//                    for (int i = 0; i < listObject.size(); i++) {
//                        LocalStorageAdapter db = new LocalStorageAdapter();
//                        db.checkGroupExistOnLocalDB(listObject.get(i)); //update local
//                        db.closeDB();
//                    }
//                } else {
//                    Log.d("Ibn", "Error get group: " + e.getMessage());
//                }
//            }
//        });
        return listObject;
    }


    public void deleteAllData(String tableName) {
        final String className=tableName;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        try {
            List<ParseObject> objects = query.find();
            for (int i = 0; i < objects.size(); i++) {
                ParseObject point = ParseObject.createWithoutData(className, objects.get(i).getObjectId());
                point.delete();
//                Log.d("Ibn", "delete: "+i);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    for (int i = 0; i < objects.size(); i++) {
//                        ParseObject point = ParseObject.createWithoutData(className, objects.get(i).getObjectId());
//                        point.deleteEventually(new DeleteCallback() {
//                            public void done(ParseException e) {
//                                if (e == null) {
//                                    // Delete successfully.
//                                    Log.d("Ibn", className + " finish delete");
//                                } else {
//                                    // The delete failed.
//                                    Log.d("Ibn", className + " delete: " + e.getMessage());
//                                }
//                            }
//                        });
//                    }
//                } else {
//                    Log.d("Ibn", className + " Error Delete: " + e.getMessage());
//                }
//            }
//        });
    }
}
