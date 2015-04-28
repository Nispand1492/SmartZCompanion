package com.example.nispand.smartzcompanion;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;


public class EditWifiReminder extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SQLiteDatabase db = openOrCreateDatabase("SCDB",MODE_PRIVATE,null);
        setContentView(R.layout.activity_edit_wifi_reminder);
        String sEvent = getIntent().getExtras().getString("EVENT_NOTE");
        final String sEventID = getIntent().getExtras().getString("EVENT_ID");
        //Log.d("Check","EVENT_NAME :"+sEvent+" EVENT_ID:"+sEventID);
        final TextView mTV=(TextView) findViewById(R.id.editText);
        mTV.setMovementMethod(new ScrollingMovementMethod());
        mTV.setText(sEvent);



        Button b= (Button) findViewById(R.id.btnSave);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUquery ="Update SCDB SET Eventnote ='"+mTV.getText().toString()+"' WHERE EventID = '"+sEventID+"';";
                db.execSQL(sUquery);

//                sUquery = "Update SCDB SET ReminderSet =1 Where EventID= "+sEventID;
//                db.execSQL(sUquery);

               setDisplay("Reminder Saved and switched on");
            }
        });

        Button btn= (Button) findViewById(R.id.btnDel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUquery = "Delete from SCDB Where EventID = '"+sEventID+"';";
                db.execSQL(sUquery);

              setDisplay("Reminder Deleted");
            }
        });

        Button bswitchOff= (Button) findViewById(R.id.btnSwitchOff);
        bswitchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUquery = "Update SCDB SET ReminderSet = 0 Where EventID = '"+sEventID+"';";
                db.execSQL(sUquery);

               setDisplay("Reminder switched off");

            }
        });
    }

    public void setDisplay(String toastNotif)
    {

        Toast.makeText(EditWifiReminder.this, toastNotif, LENGTH_LONG).show();
        Intent intent = new Intent(EditWifiReminder.this,WifiMenu.class);
        startActivity(intent);
    }

}
