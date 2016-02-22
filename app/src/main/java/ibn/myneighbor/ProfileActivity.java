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
import java.util.*;

import java.text.ParseException;

import ibn.myneighbor.Model.Activity;
import ibn.myneighbor.Model.User;

public class ProfileActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewActivity = new Intent(view.getContext(), CreateNewActivity.class);
                startActivity(createNewActivity);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new LocalStorageAdapter(this.getApplicationContext());
        ArrayList<Activity> personalActivity=new ArrayList<Activity>();
        ArrayList<User> personalImgID=new ArrayList<User>();
        try {
            personalActivity=db.getPersonalActivity("Ibn");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        user=new ArrayList<User>();
        activityNeedList=new ArrayList<String>();
        ownerList=new ArrayList<String>();
        offerOrReqList=new ArrayList<Integer>();
        profilePicList=new ArrayList<Integer>();
        for (int i = 0; i < personalActivity.size(); i++) {
            activityNeedList.add(personalActivity.get(i).getTitle());
            ownerList.add(personalActivity.get(i).getOwner());
            offerOrReqList.add(personalActivity.get(i).getRequestOrOffer());
            user.addAll(db.getUser(ownerList.get(i)));

        }
        for (int i = 0; i < user.size(); i++) {
            profilePicList.add(user.get(i).getProfilePic());
        }


        CustomListAdapter adapter = new CustomListAdapter(this, activityNeedList, profilePicList, ownerList, offerOrReqList);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.activity_need);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectedActivity = activityNeedList.get(+position);


//                Toast.makeText(getApplicationContext(), SlectedActivity, Toast.LENGTH_SHORT).show();
                Intent detailActivity = new Intent(view.getContext(), DetailActivity.class);
                ArrayList<String> tag = (ArrayList<String>)view.getTag();
                detailActivity.putExtra("ownerName", tag.get(0));
                detailActivity.putExtra("activity", tag.get(1));
                detailActivity.putExtra("imgID", Integer.parseInt(tag.get(2)));
//                detailActivity.putExtra("SelectedActivity", selectedActivity);
                startActivity(detailActivity);
            }

        });
        db.closeDB();
    }

    public void onClickEdit(View view) {
        Intent createNewActivity = new Intent(view.getContext(), CreateNewActivity.class);
        //Todo edit profile page: query profile detail
//        createNewActivity.putExtra("ProfileDetail", ...);
        startActivity(createNewActivity);
    }

    public void onClickChat(View view){
//        final int position = (Integer) v.getTag();
//        Toast.makeText(v.getContext(), position, Toast.LENGTH_SHORT).show();
//        Log.d("Ibn", v.getTag() + " " + v.getParent());
        Intent chatActivity = new Intent(view.getContext(), ChatActivity.class);
        chatActivity.putExtra("SelectedActivity", view.getTag().toString());
        startActivity(chatActivity);
    }
}
