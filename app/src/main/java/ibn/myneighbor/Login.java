package ibn.myneighbor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApp.initOnBroadCastReceiver(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.username);
                LocalStorageAdapter db = new LocalStorageAdapter();
                if(db.checkUser(username.getText().toString())){
                    MyApp.setUsername(username.getText().toString());
                }else{
                    MyApp.setUsername("Unknown");

                }
//                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("username", username.getText().toString()).commit();
                Intent createNewActivity = new Intent(getBaseContext(), MainActivity.class);
                startActivity(createNewActivity);
//                finish();
            }
        });
    }

}
