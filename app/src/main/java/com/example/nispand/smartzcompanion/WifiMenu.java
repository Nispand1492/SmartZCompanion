package com.example.nispand.smartzcompanion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;


public class WifiMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifimenu);



        Button bSetRem = (Button) findViewById(R.id.btnSetRem);

        bSetRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WifiMenu.this,SetWifiReminder.class);
                startActivity(intent);
            }
        });
        Button bDel = (Button) findViewById(R.id.delRem);


        bDel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SQLiteDatabase db = openOrCreateDatabase("SCDB",MODE_PRIVATE,null);
                String sSelectQ="Select * from SCDB;";
                Cursor c= db.rawQuery(sSelectQ,null);
                if(c!=null)
                {
                    if(c.getCount() > 0)
                    {
                        int cnt =c.getCount();
                        db.execSQL("Delete from SCDB;");
                        setDisplay("Deleted "+cnt+"WiFi reminders.");
                    }
                    else
                    {
                        setDisplay("No reminders to be deleted!!");
                    }
                }

            }
        });

        Button bEdit = (Button) findViewById(R.id.editBtn);
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WifiMenu.this,DisplayWifiReminders.class);
                startActivity(intent);
            }
        });
    }

    public void setDisplay(String toastNotif)
    {
        Toast.makeText(WifiMenu.this, toastNotif, LENGTH_LONG).show();
        Intent intent = new Intent(WifiMenu.this,MainActivity.class);
        startActivity(intent);
    }


}
