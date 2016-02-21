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

public class ProfileActivity extends AppCompatActivity {

    private String[] profile__mocActivityNeed = {
            "Feeding Dog",
            "Math Tutoring",
            "Plants Waatering",
    };

    private Integer[] profile_imgid = {
            R.drawable.panda,
            R.drawable.panda,
            R.drawable.panda,
    };

    private ListView listView;
    private String selectedActivity;

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

        CustomListAdapter adapter = new CustomListAdapter(this, profile__mocActivityNeed, profile_imgid);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.activity_need);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedActivity = profile__mocActivityNeed[+position];

//                Toast.makeText(getApplicationContext(), SlectedActivity, Toast.LENGTH_SHORT).show();
                Intent detailActivity = new Intent(view.getContext(), DetailActivity.class);
                detailActivity.putExtra("SelectedActivity", selectedActivity);
                startActivity(detailActivity);
            }

        });
    }

    public void onClickEdit(View view) {
        Intent createNewActivity = new Intent(view.getContext(), CreateNewActivity.class);
        //Todo query data and sent to create/edit page
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
