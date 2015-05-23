package com.example.nispand.smartzcompanion;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;


public class Add_PhoneCallReminder extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {
    AutoCompleteTextView contactname=null;
    private ArrayAdapter<String> adapter;

    // Store contacts values in these arraylist
    public static ArrayList<String> phoneValueArr = new ArrayList<String>();
    public static ArrayList<String> nameValueArr = new ArrayList<String>();

    EditText contactmessgae;
    String toNumberValue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__phone_call_reminder);
        // Initialize AutoCompleteTextView values

        contactname = (AutoCompleteTextView) findViewById(R.id.editContactName);
        contactmessgae = (EditText) findViewById(R.id.phonemessage);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        contactname.setThreshold(1);
        contactname.setAdapter(adapter);
        contactname.setOnItemSelectedListener(this);
        contactname.setOnClickListener(this);
        contactname.setOnItemClickListener(this);
        readContactData();
        contactmessgae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactmessgae.setText("");
            }
        });
        final Button save = (Button) findViewById(R.id.savephone);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhone(contactname);
            }
        });

    }

    private void savePhone (final AutoCompleteTextView toNumber) {
                String NameSel = "";
                NameSel = toNumber.getText().toString();


                final String ToNumber = toNumberValue;


                if (ToNumber.length() == 0) {
                    Toast.makeText(getBaseContext(), "Please fill phone number",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), NameSel + " : " + toNumberValue,
                            Toast.LENGTH_LONG).show();
                }
        SQLiteDatabase db = openOrCreateDatabase("SCDB",MODE_PRIVATE,null);


        db.execSQL("CREATE TABLE IF NOT EXISTS SCPDB (EventID varchar(60),Eventnote varchar(600),Contactname varchar(50),ReminderSet BOOLEAN,ContactNumber varchar(20));");
        SimpleDateFormat fdate = new SimpleDateFormat("HHmmssSSS");
        SimpleDateFormat fcdate =new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        java.util.Date date= new java.util.Date();
        String sEventID = fdate.format(date);
        String sEventDate =fcdate.format(date);
        String sIquery = "Insert Into SCDB VALUES('"+sEventID+"', '"+NameSel+"', '"+sEventDate+"',1,'"+NameSel+"');";
        db.execSQL(sIquery);
        db.close();
        setDisplay("REMINDER IS SET!!");
    }
    public void setDisplay(String toastNotif)
    {
        Toast.makeText(Add_PhoneCallReminder.this, toastNotif, LENGTH_LONG).show();
        Intent intent = new Intent(Add_PhoneCallReminder.this,MainActivity.class);
        startActivity(intent);
    }

    private void readContactData() {
        try {

            /*********** Reading Contacts Name And Number **********/

            String phoneNumber = "";
            ContentResolver cr = getBaseContext()
                    .getContentResolver();

            //Query to get contact name

            Cursor cur = cr
                    .query(ContactsContract.Contacts.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

            // If data data found in contacts
            if (cur.getCount() > 0) {

                Log.i("AutocompleteContacts", "Reading   contacts........");

                int k=0;
                String name = "";

                while (cur.moveToNext())
                {

                    String id = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    //Check contact have phone number
                    if (Integer
                            .parseInt(cur
                                    .getString(cur
                                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                    {

                        //Create query to get phone number by contact id
                        Cursor pCur = cr
                                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                + " = ?",
                                        new String[] { id },
                                        null);
                        int j=0;

                        while (pCur
                                .moveToNext())
                        {
                            // Sometimes get multiple data
                            if(j==0)
                            {
                                // Get Phone number
                                phoneNumber =""+pCur.getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                // Add contacts names to adapter
                                adapter.add(name);

                                // Add ArrayList names to adapter
                                phoneValueArr.add(phoneNumber.toString());
                                nameValueArr.add(name.toString());

                                j++;
                                k++;
                            }
                        }  // End while loop
                        pCur.close();
                    } // End if

                }  // End while loop

            } // End Cursor value check
            cur.close();


        } catch (Exception e) {
            Log.i("AutocompleteContacts","Exception : "+ e);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add__phone_call_reminder, menu);
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

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        int i = nameValueArr.indexOf(""+arg0.getItemAtPosition(arg2));
        if (i >= 0) {

            // Get Phone Number
            toNumberValue = phoneValueArr.get(i);

            InputMethodManager imm = (InputMethodManager) getSystemService(
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            // Show Alert
            Toast.makeText(getBaseContext(),
                    "Position:" + arg2 + " Name:" + arg0.getItemAtPosition(arg2) + " Number:" + toNumberValue,
                    Toast.LENGTH_LONG).show();

            Log.d("AutocompleteContacts",
                    "Position:"+arg2+" Name:"+arg0.getItemAtPosition(arg2)+" Number:"+toNumberValue);

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // Get Array index value for selected name

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    @Override
    public void onClick(View v) {
        contactname.setText("");

    }
}
