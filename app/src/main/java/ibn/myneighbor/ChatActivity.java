package ibn.myneighbor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle=getIntent().getExtras();
        final String ownerName=bundle.getString("ownerName");
        final String activity=bundle.getString("activity");
        final int imgID=bundle.getInt("imgID");
        final TextView textView = (TextView) findViewById(R.id.chatText);
        textView.setText(ownerName+": "+activity);

        Button send = (Button) findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Ibn","sendddd: "+ textView.getText());
                LocalStorageAdapter db = new LocalStorageAdapter(view.getContext());
                db.createConversation(new Conversation(activity, "Ibn", ownerName, textView.getText().toString()));
                db.closeDB();
                Intent chatActivity = new Intent(view.getContext(), ChatActivity.class);
                ArrayList<String> tag = (ArrayList<String>) view.getTag();
                chatActivity.putExtra("ownerName", ownerName);
                chatActivity.putExtra("activity", activity);
                chatActivity.putExtra("imgID", imgID);
//                detailActivity.putExtra("SelectedActivity", selectedActivity);
                startActivity(chatActivity);
            }
        });


        db = new LocalStorageAdapter(this.getApplicationContext());
        ArrayList<Conversation> allConversation = new ArrayList<Conversation>();
        ArrayList<User> personalImgID = new ArrayList<User>();

        allConversation = db.getSpecificConversation(ownerName, activity);


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
        CustomAllChatList adapter = new CustomAllChatList(this, chatMessageList, profilePicList, ownerList, offerOrReqList);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.chat_list);
        listView.setAdapter(adapter);
    }

}
