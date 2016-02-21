package ibn.myneighbor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.SubMenu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.util.Log;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String[] mocActivityNeed = {
            "Feeding Dog",
            "Babysisting",
            "Car Pool",
            "Math Tutoring",
            "Plants Waatering",
            "Ubuntu","Windows7",
            "Max OS X"
    };

    private Integer[] imgid={
            R.drawable.animation,
            R.drawable.bear,
            R.drawable.dolphin,
            R.drawable.human1,
            R.drawable.human2,
            R.drawable.octopus,
            R.drawable.panda,
            R.drawable.snowman,
    };

    private String[] group = {
            "home",
            "school",
            "gym",
            "internship",
            "company",
            "friends",
            "love"
    };

    private String[] subgroup__mocActivityNeed = {
            "Feeding Dog",
            "Car Pool",
            "Math Tutoring",
    };

    private Integer[] subgroup_imgid={
            R.drawable.animation,
            R.drawable.dolphin,
            R.drawable.human2,
    };

    private ListView listView;
    private String selectedActivity;
    private int tmp_subgroupActivity =-1;
//    private ImageButton chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        CustomListAdapter adapter = new CustomListAdapter(this, mocActivityNeed, imgid);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.activity_need);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(tmp_subgroupActivity==0||tmp_subgroupActivity==-1){
                    selectedActivity = mocActivityNeed[+position];
                }else{
                    selectedActivity = subgroup__mocActivityNeed[+position];
                }
//                Toast.makeText(getApplicationContext(), SlectedActivity, Toast.LENGTH_SHORT).show();
                Intent detailActivity = new Intent(view.getContext(), DetailActivity.class);
                detailActivity.putExtra("SelectedActivity", selectedActivity);
                startActivity(detailActivity);
            }

        });

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navView.getMenu();
        SubMenu subGroup = m.addSubMenu("Groups");
        subGroup.add(0,0,0,"All");
        for(int i = 0;i<group.length;i++){
            subGroup.add(0,i+1,i+1,group[i]); //group_id, item_id, order
        }




    }

    public void onClickChat(View view){
//        final int position = (Integer) v.getTag();
//        Toast.makeText(v.getContext(), position, Toast.LENGTH_SHORT).show();
//        Log.d("Ibn", v.getTag() + " " + v.getParent());
        Intent chatActivity = new Intent(view.getContext(), ChatActivity.class);
        chatActivity.putExtra("SelectedActivity", view.getTag().toString());
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
        getMenuInflater().inflate(R.menu.main, menu);

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
        } else if (id == 0){
            CustomListAdapter adapter = new CustomListAdapter(this, mocActivityNeed, imgid);
            adapter.notifyDataSetChanged();
            listView = (ListView) findViewById(R.id.activity_need);
            listView.setAdapter(adapter);
            tmp_subgroupActivity=0;
        }else{
                CustomListAdapter adapter = new CustomListAdapter(this, subgroup__mocActivityNeed, subgroup_imgid);
                adapter.notifyDataSetChanged();
                listView = (ListView) findViewById(R.id.activity_need);
                listView.setAdapter(adapter);
                tmp_subgroupActivity=id;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if(nextActivity!=null) {
            startActivity(nextActivity);
        }

        return true;
    }
}
