package ibn.myneighbor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle=getIntent().getExtras();
        String ownerName=bundle.getString("ownerName");
        String activity=bundle.getString("activity");
        int imgID=bundle.getInt("imgID");
        TextView textView = (TextView) findViewById(R.id.activity_name);
        textView.setText(ownerName + ": " + activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(ownerName + ": " + activity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
