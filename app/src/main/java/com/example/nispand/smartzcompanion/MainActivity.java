package com.example.nispand.smartzcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static android.view.View.OnClickListener;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button cal, wifibutton, phonecallbased;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cal = (Button) findViewById(R.id.Cal);
        wifibutton = (Button) findViewById(R.id.wifi);
        phonecallbased = (Button) findViewById(R.id.phonbased);
        cal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("android.CalEvent");
                startActivity(i);
            }
        });
        wifibutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WifiMenu.class);
                //intent.putExtra("Etext", finalSWifiState);
                startActivity(intent);

            }
        });
        phonecallbased.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this,Add_PhoneCallReminder.class);
                startActivity(k);
            }
        });
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
