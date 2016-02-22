package ibn.myneighbor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import android.app.DatePickerDialog;
import android.util.Log;

import java.util.*;

import ibn.myneighbor.Model.Activity;
import ibn.myneighbor.Model.Group;

public class CreateNewActivity extends AppCompatActivity {

    private int num_req_offer=0;
    private Date d;
    private String group;

    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        d=new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        ib = (ImageButton) findViewById(R.id.imageButton1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.editText);
        et.setText(day+"/"+month+"/"+year);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        et.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                        d=new Date(year, monthOfYear, dayOfMonth);
                    }

                };
                new DatePickerDialog(CreateNewActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewActivity = new Intent(view.getContext(), MainActivity.class);
                startActivity(createNewActivity);
            }
        });

        Switch req_offer = (Switch) findViewById(R.id.offer_request);
        req_offer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //do stuff when Switch is ON
                    num_req_offer = 1;
                } else {
                    //do stuff when Switch if OFF
                    num_req_offer = 0;
                }
            }
        });

        Spinner spinnerGroup = (Spinner) findViewById(R.id.spinnerGroup);
        List<String> list = new ArrayList<String>();
        LocalStorageAdapter db=new LocalStorageAdapter(this.getApplicationContext());
        ArrayList<Group> groupArrayList= db.getGroups();
        list.add("all");
        for(int i=0;i< groupArrayList.size();i++){
            list.add(groupArrayList.get(i).getGroupName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(dataAdapter);

        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                group = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        DatePicker expireDate = (DatePicker) findViewById(R.id.expireDate);
//        expireDate.seton


        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = (EditText) findViewById(R.id.titleText);
                EditText desc = (EditText) findViewById(R.id.descText);

                LocalStorageAdapter db = new LocalStorageAdapter(view.getContext());
                if(title.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(), "Please put the title", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("Ibn", title.getText().toString()+" "+ desc.getText().toString()+" "+ num_req_offer+" "+ group+" "+ d+" "+ "Ibn");
                    long check = db.createActivity(new Activity(title.getText().toString(), desc.getText().toString(), num_req_offer, group, d, "Ibn"));
                    Log.d("Ibn",check+"");
                    Intent createNewActivity = new Intent(view.getContext(), MainActivity.class);
                    startActivity(createNewActivity);
                }
                db.closeDB();
            }
        });

    }



}
