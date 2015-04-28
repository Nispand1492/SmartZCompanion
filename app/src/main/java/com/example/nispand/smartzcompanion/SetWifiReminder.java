package com.example.nispand.smartzcompanion;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import static android.widget.Toast.LENGTH_LONG;

public class SetWifiReminder extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        final TextView mTV=(TextView) findViewById(R.id.editText2);
        Button b= (Button) findViewById(R.id.btnSet);

        mTV.setMovementMethod(new ScrollingMovementMethod());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = openOrCreateDatabase("SCDB",MODE_PRIVATE,null);


                db.execSQL("CREATE TABLE IF NOT EXISTS SCDB (EventID varchar(60),Eventnote varchar(600),EventDate varchar(50),ReminderSet BOOLEAN);");

                SimpleDateFormat fdate = new SimpleDateFormat("HHmmssSSS");
                SimpleDateFormat fcdate =new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                java.util.Date date= new java.util.Date();
                String sEventID = fdate.format(date);
                String sEventDate =fcdate.format(date);
                String sEventtxt = mTV.getText().toString();
                String sIquery = "Insert Into SCDB VALUES('"+sEventID+"', '"+sEventtxt+"', '"+sEventDate+"',1);";
                db.execSQL(sIquery);
                db.close();
                setDisplay("REMINDER IS SET!!");
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_reminder, menu);
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
    public void setDisplay(String toastNotif)
    {
        Toast.makeText(SetWifiReminder.this, toastNotif, LENGTH_LONG).show();
        Intent intent = new Intent(SetWifiReminder.this,MainActivity.class);
        startActivity(intent);
    }
}
