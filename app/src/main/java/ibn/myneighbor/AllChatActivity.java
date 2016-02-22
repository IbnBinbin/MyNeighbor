package ibn.myneighbor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;

import ibn.myneighbor.Model.Activity;
import ibn.myneighbor.Model.Conversation;
import ibn.myneighbor.Model.User;

public class AllChatActivity extends AppCompatActivity {

    private LocalStorageAdapter db;
    private ListView listView;
    private String selectedActivity;
    private ArrayList<User> user;
    private ArrayList<String> activityNeedList;
    private ArrayList<String> ownerList;
    private ArrayList<Integer> offerOrReqList;
    private ArrayList<Integer> profilePicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new LocalStorageAdapter(this.getApplicationContext());
        ArrayList<Conversation> allConversation = new ArrayList<Conversation>();
        ArrayList<User> personalImgID = new ArrayList<User>();

        allConversation = db.getAllConversation("Ibn");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = new ArrayList<User>();
        activityNeedList = new ArrayList<String>();
        ownerList = new ArrayList<String>();
        profilePicList = new ArrayList<Integer>();
        for (int i = 0; i < allConversation.size(); i++) {
            activityNeedList.add(allConversation.get(i).getTopic());
            ownerList.add(allConversation.get(i).getOwner());
            user.addAll(db.getUser(ownerList.get(i)));
        }
        for (int i = 0; i < user.size(); i++) {
            profilePicList.add(user.get(i).getProfilePic());
        }
        CustomAllChatList adapter = new CustomAllChatList(this, activityNeedList, profilePicList, ownerList, offerOrReqList);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.allConversation);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectedActivity = activityNeedList.get(+position);


//                Toast.makeText(getApplicationContext(), SlectedActivity, Toast.LENGTH_SHORT).show();
                Intent detailActivity = new Intent(view.getContext(), ChatActivity.class);
                ArrayList<String> tag = (ArrayList<String>) view.getTag();
                detailActivity.putExtra("ownerName", tag.get(0));
                detailActivity.putExtra("activity", tag.get(1));
                detailActivity.putExtra("imgID", Integer.parseInt(tag.get(2)));
//                detailActivity.putExtra("SelectedActivity", selectedActivity);
                startActivity(detailActivity);
            }

        });
    }

}
