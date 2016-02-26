package ibn.myneighbor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.util.Log;
import java.util.ArrayList;

import ibn.myneighbor.Model.Conversation;
import ibn.myneighbor.Model.User;

public class ChatActivity extends AppCompatActivity {

    private LocalStorageAdapter db;
    private ListView listView;
    private String selectedActivity;
    private ArrayList<User> user;
    private ArrayList<String> chatMessageList;
    private ArrayList<String> ownerList;
    private ArrayList<Integer> offerOrReqList;
    private ArrayList<Integer> profilePicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApp.initOnBroadCastReceiver(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle=getIntent().getExtras();
        final String ownerName=bundle.getString("ownerName");
        final String activity=bundle.getString("activity");
        final int imgID=bundle.getInt("imgID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(ownerName + ": " + activity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textView = (TextView) findViewById(R.id.chatText);

        Button send = (Button) findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Ibn", "sendddd: " + textView.getText());
                String username = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("username", "NULL");
                LocalStorageAdapter db = new LocalStorageAdapter();
                db.createConversation(new Conversation(activity, username, ownerName, textView.getText().toString()), true);
                db.closeDB();
                Intent chatActivity = new Intent(view.getContext(), ChatActivity.class);
                ArrayList<String> tag = (ArrayList<String>) view.getTag();
                chatActivity.putExtra("ownerName", ownerName);
                chatActivity.putExtra("activity", activity);
                chatActivity.putExtra("imgID", imgID);
//                detailActivity.putExtra("SelectedActivity", selectedActivity);
                startActivity(chatActivity);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        db = new LocalStorageAdapter();
        ArrayList<Conversation> allConversation = new ArrayList<Conversation>();
        ArrayList<User> personalImgID = new ArrayList<User>();
        String username = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("username", "NULL");
        allConversation = db.getSpecificConversation(ownerName, activity, username);


        user = new ArrayList<User>();
        chatMessageList = new ArrayList<String>();
        ownerList = new ArrayList<String>();
        profilePicList = new ArrayList<Integer>();
        for (int i = 0; i < allConversation.size(); i++) {
            chatMessageList.add(allConversation.get(i).getChatMessage());
            ownerList.add(allConversation.get(i).getOwner());
            user.addAll(db.getUser(ownerList.get(i)));
        }
        for (int i = 0; i < user.size(); i++) {
            profilePicList.add(user.get(i).getProfilePic());
        }
        CustomAllChatList adapter = new CustomAllChatList(this, chatMessageList, profilePicList, ownerList, offerOrReqList, false);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.chat_list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

}
