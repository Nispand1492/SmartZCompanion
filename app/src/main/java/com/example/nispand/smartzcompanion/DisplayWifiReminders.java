package com.example.nispand.smartzcompanion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;


public class DisplayWifiReminders extends ActionBarActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wifi_reminders);


        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);

        // Defined Array values to show in ListView

        Context context=getApplicationContext();
        SQLiteDatabase db = context.openOrCreateDatabase("SCDB", context.MODE_PRIVATE, null);
        String sSelectQ="Select * from SCDB where ReminderSet = 1;";
        Cursor c= db.rawQuery(sSelectQ,null);
        final HashMap <Integer,String> hEvent = new HashMap <Integer,String> ();
        ArrayList <String> values = new ArrayList <String> ();
        if(c!=null && c.getCount() > 0) {
            values.add("Click on the reminder to Edit");
            c.moveToFirst();
            values.add(c.getString(c.getColumnIndex("Eventnote")));
            hEvent.put(1,c.getString(c.getColumnIndex("EventID")));

            Log.d("Disply",c.getString(c.getColumnIndex("Eventnote"))+c.getString(c.getColumnIndex("EventID")));
            int i=1;
            while (c.moveToNext()) {
                i++;
                Log.d("Disply",c.getString(c.getColumnIndex("Eventnote"))+c.getString(c.getColumnIndex("EventID")));
                hEvent.put(i,c.getString(c.getColumnIndex("EventID")));
                 values.add(c.getString(c.getColumnIndex("Eventnote")));


            }
        }
        else{
            Toast.makeText(DisplayWifiReminders.this, "No reminders to display!!", LENGTH_LONG).show();
            Intent intent = new Intent(DisplayWifiReminders.this,WifiMenu.class);
            startActivity(intent);
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

               // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                if(position != 0)
                {

                    //Toast.makeText(getApplicationContext(),"Position :" + position + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DisplayWifiReminders.this,EditWifiReminder.class);
                      intent.putExtra("EVENT_NOTE",itemValue);
                      String sEvent =hEvent.get(position).toString();
                    intent.putExtra("EVENT_ID",hEvent.get(position).toString());
                    startActivity(intent);

                }

            }

        });

    }

}
