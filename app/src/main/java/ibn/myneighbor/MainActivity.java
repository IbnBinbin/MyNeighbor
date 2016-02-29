package ibn.myneighbor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import ibn.myneighbor.Model.*;
import android.content.SharedPreferences.Editor;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> activityNeedList = new ArrayList<String>();
    private ArrayList<String> ownerList = new ArrayList<String>();
    private ArrayList<Integer> profilePicList = new ArrayList<Integer>();
    private ArrayList<Integer> offerOrReqList = new ArrayList<Integer>();


    private ListView listView;
    private String selectedActivity;
    private String accountOwner = "Ibn";
    private LocalStorageAdapter db;
    private ArrayList<Group> allGroups;
    private String username;
    private SharedPreferences prefs = null;
//    private ImageButton chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApp.initOnBroadCastReceiver(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewActivity = new Intent(view.getContext(), CreateNewActivity.class);
                startActivity(createNewActivity);
                finish();
            }
        });

        prefs = MyApp.getPrefs();

        updateActivityAndOwnerPic("all");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        prefsEditor.commit();
//        cloudDB.testAddNewTaskToDB();

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstRun", true);

        if (isFirstRun) {
//            db = new LocalStorageAdapter();
//            assignInitialData(db);
//            db.close();
            assignInitialFromCloud();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstRun", false).commit();

            Intent createNewActivity = new Intent(this.getApplicationContext(), Login.class);
            startActivity(createNewActivity);
            finish();
        }
        username = MyApp.getUsername();
//        Log.d("Ibn", "Hello " + username);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedActivity = activityNeedList.get(+position);

//                Toast.makeText(getApplicationContext(), SlectedActivity, Toast.LENGTH_SHORT).show();
                Intent detailActivity = new Intent(view.getContext(), DetailActivity.class);
                ArrayList<String> tag = (ArrayList<String>) view.getTag();
                detailActivity.putExtra("ownerName", tag.get(0));
                detailActivity.putExtra("activity", tag.get(1));
                detailActivity.putExtra("imgID", Integer.parseInt(tag.get(2)));
//                detailActivity.putExtra("SelectedActivity", selectedActivity);
                startActivity(detailActivity);
            }

        });

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navView.getMenu();
        SubMenu subGroup = m.addSubMenu("Groups");
        subGroup.add(0, 0, 0, "All");
        allGroups = db.getGroups();
        for (int i = 0; i < allGroups.size(); i++) {
            subGroup.add(0, allGroups.get(i).getID(), i + 1, allGroups.get(i).getGroupName()); //group_id, item_id, order
        }


    }

    private void assignInitialFromCloud() {
        LocalStorageAdapter db = new LocalStorageAdapter();
        db.deleteAllData();
        db.closeDB();
        MyApp.getDBcloud().getUserToUpdateLocal();
        MyApp.getDBcloud().getActivityToUpdateLocal();
        MyApp.getDBcloud().getConversationToUpdateLocal();
        MyApp.getDBcloud().getGroupToUpdateLocal();
        MyApp.getDBcloud().getNeighborhoodToUpdateLocal();

    }

    private void assignInitialData(LocalStorageAdapter db) {
        db.deleteAllData();
        db.createActivity(new Activity("Feeding Dog", "dog dog dog", 0, "Gym", new Date(), null, "Egg"), true);
        db.createActivity(new Activity("Babysisting", "baby baby baby", 0, "home", new Date(), null,"Ibn"), true);
        db.createActivity(new Activity("Car Pool", "carrrrr", 1, "school", new Date(), null, "Pop"), true);
        db.createActivity(new Activity("Math Tutoring", "12345", 0, "home", new Date(), null, "Touch"), true);
        db.createActivity(new Activity("Plants Waatering", "waterr", 0, "school", new Date(), null, "Pim"), true);
        db.createActivity(new Activity("Ubuntu", "...", 1, "gym", new Date(), null,"Pim"),true);
        db.createActivity(new Activity("Dating", "~~~", 0, "all", new Date(), null, "Ibn"), true);

        db.createUser(new User("Ibn", "...", R.drawable.animation, "1234"), true);
        db.createUser(new User("Pop", "...", R.drawable.dolphin, "1111"), true);
        db.createUser(new User("Egg", "...", R.drawable.bear, "1232"), true);
        db.createUser(new User("Touch", "...", R.drawable.octopus, "122"), true);
        db.createUser(new User("Pim", "...", R.drawable.snowman, "333"),true);
        db.createUser(new User("Unknown", "...", R.drawable.unknown, "333"),true);

        db.createGroup(new Group(0, "Ibn", "home", "Touch, Pim"), true);
        db.createGroup(new Group(0, "Ibn", "school", "Egg, Pim"), true);
        db.createGroup(new Group(0, "Ibn", "Gym", "Egg"), true);
        db.createGroup(new Group(0, "Touch", "home", "Ibn, Pim"),true);
        db.createGroup(new Group(0, "Touch", "school", "Pop, Pim"),true);
        db.createGroup(new Group(0, "Touch", "office", "Egg"),true);
        db.createGroup(new Group(0, "Pim", "home", "Ibn"),true);

        db.createConversation(new Conversation("Test", "Ibn", "Pim", "Hello"),true);
        db.createConversation(new Conversation("Test1", "Touch", "Ibn", "Hello1"),true);
        db.createConversation(new Conversation("Test2", "Egg", "Pop", "Hello2"),true);
        db.createConversation(new Conversation("Test3", "Touch", "Egg", "Hello3"),true);
        db.createConversation(new Conversation("Test4", "Pop", "Ibn", "Hello4"),true);
    }

    public void onClickChat(View view) {
//        final int position = (Integer) v.getTag();
//        Toast.makeText(v.getContext(), position, Toast.LENGTH_SHORT).show();
//        Log.d("Ibn", view.getTag() + " " + view.getParent());
        Intent chatActivity = new Intent(view.getContext(), ChatActivity.class);
        ArrayList<String> tag = (ArrayList<String>) view.getTag();
        chatActivity.putExtra("ownerName", tag.get(0));
        chatActivity.putExtra("activity", tag.get(1));
        chatActivity.putExtra("imgID", Integer.parseInt(tag.get(2)));
        startActivity(chatActivity);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent nextActivity = null;
//        View v = new View(getApplication());

        if (id == R.id.profile) {
            nextActivity = new Intent(MainActivity.this, ProfileActivity.class);

        } else if (id == R.id.neighborhood) {
            nextActivity = new Intent(MainActivity.this, NeighborhoodActivity.class);

        } else if (id == R.id.conversions) {
            nextActivity = new Intent(MainActivity.this, AllChatActivity.class);
        } else if (id == 0) {
            updateActivityAndOwnerPic("all");
        } else {
            for (int i = 0; i < allGroups.size(); i++) {
                if (id == allGroups.get(i).getID()) {
                    updateActivityAndOwnerPic(allGroups.get(i).getGroupName());
                    break;
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (nextActivity != null) {
            startActivity(nextActivity);
        }

        return true;
    }

    private void updateActivityAndOwnerPic(String group) {
        db = new LocalStorageAdapter();
        ArrayList<Activity> act = new ArrayList<Activity>();
        activityNeedList.clear();
        profilePicList.clear();
        ownerList.clear();
        String g = group;
        try {
            act.addAll(db.getActivity(g));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Ibn", "error");
        }
        ArrayList<User> user = new ArrayList<User>();
        for (int i = 0; i < act.size(); i++) {
            if(act.get(i).getOwner()!=null) {

                activityNeedList.add(act.get(i).getTitle());
                ownerList.add(act.get(i).getOwner());
                offerOrReqList.add(act.get(i).getRequestOrOffer());
                user.addAll(db.getUser(ownerList.get(i)));
            }

        }
        for (int i = 0; i < user.size(); i++) {
            if(act.get(i).getOwner()!=null) {
                profilePicList.add(user.get(i).getProfilePic());
            }
        }

        CustomListAdapter adapter = new CustomListAdapter(this, activityNeedList, profilePicList, ownerList, offerOrReqList);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.activity_need);
        listView.setAdapter(adapter);
        db.closeDB();

    }


}
