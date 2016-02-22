package ibn.myneighbor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ibn.myneighbor.Model.Group;

public class CreateNewGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewActivity = new Intent(view.getContext(), MainActivity.class);
                startActivity(createNewActivity);
            }
        });

        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText groupName = (EditText) findViewById(R.id.groupNameText);

                LocalStorageAdapter db = new LocalStorageAdapter(view.getContext());
                if(groupName.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(), "Please put the group name", Toast.LENGTH_SHORT).show();
                }else {
                    db.createGroup(new Group(0, "Ibn", groupName.getText().toString(), ""));
                    Intent createNewActivity = new Intent(view.getContext(), MainActivity.class);
                    startActivity(createNewActivity);
                }
                db.closeDB();
            }
        });
    }

}
