package com.example.nispand.smartzcompanion;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalEvent extends ActionBarActivity {
    CalendarView calendar;
    FrameLayout frame;
    CalendarContract Calendars;
    TextView tx;
    LinearLayout ll;
    ListView lv;
    Button Add, Search;
    EditText selectdate;
    long cday;
    long cmonth;
    long cyear;
    long xyz;
    Date myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_event);
        Add = (Button) findViewById(R.id.Add);
        Search = (Button) findViewById(R.id.Search);
        calendar = (CalendarView) findViewById(R.id.calendarView2);
        selectdate = (EditText) findViewById(R.id.selectdate);

        //  lv = (ListView) findViewById(R.id.listView);
        List<String> li;
        li = new ArrayList<String>();
        //frame = (FrameLayout) (findViewById(R.id.frame1));
        //    ll = (LinearLayout) findViewById(R.id.mylinear);
        //     ll.setVisibility(View.INVISIBLE);
        //  tx = (TextView) findViewById(R.id.textView4);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat Formatter = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    myDate    = Formatter.parse(String.valueOf(selectdate.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                add_event(myDate);
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat Formatter = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    myDate    = Formatter.parse(String.valueOf(selectdate.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                search_event();
            }
        });
        final Calendar myCalendar = Calendar.getInstance();
        // Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectdate.setText(monthOfYear+"/"+dayOfMonth+"/"+year);
            }

        };


        selectdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CalEvent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    public void initializeCalendar() {

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);

        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);


    }
    public void add_event(Date myDate)
    {
                String month = (String) DateFormat.format("MM", myDate);
                String day = (String) android.text.format.DateFormat.format("dd", myDate);
                String year = (String) android.text.format.DateFormat.format("yyyy", myDate);
                int setm = Integer.parseInt(month);
                int setday = Integer.parseInt(day);
                int sety =Integer.parseInt(year);
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("BeginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("Rule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
                intent.putExtra("Title", "Put Title ");
                GregorianCalendar calDate = new GregorianCalendar(sety,setm,setday);
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,calDate.getTimeInMillis());
                startActivity(intent);
    }
    public void search_event(){



        //calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            //public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                String month = (String) DateFormat.format("MM", myDate);
                String day = (String) android.text.format.DateFormat.format("dd", myDate);
                String year = (String) android.text.format.DateFormat.format("yyyy", myDate);
                int setm = Integer.parseInt(month);
                int setday = Integer.parseInt(day);
                int sety =Integer.parseInt(year);

                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

                //calendar.setVisibility(View.INVISIBLE);
                //    ll.setVisibility(View.VISIBLE);
                long date1 = calendar.getDate();
                Date d2 = new Date(date1);
                String cMonth = (String) android.text.format.DateFormat.format("MM", myDate);
                String cyear = (String) android.text.format.DateFormat.format("yyyy", myDate);
                String cday = (String) android.text.format.DateFormat.format("dd", myDate);

                String[] projection = {CalendarContract.Events._ID,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.EXDATE,
                };
                // Get a Cursor over the Events Provider.
                Cursor cursor= null;
                ContentResolver cr = getContentResolver();
                cursor =cr.query(
                        CalendarContract.Events.CONTENT_URI, projection, null, null,null);
                // Get the index of the columns.
                int nameIdx = cursor.getColumnIndexOrThrow(CalendarContract.Events.TITLE);
                int idIdx = cursor.getColumnIndexOrThrow(CalendarContract.Events._ID);
                int D1 = cursor.getColumnIndexOrThrow(CalendarContract.Events.DTSTART);
                int i = 0;

                // Initialize the result set.
                String[] result = new String[cursor.getCount()];
                String[] eventname = new String[cursor.getCount()];
                String[] eventID = new String[cursor.getCount()];
                String id1;
                String nameid;
                List<String> li = new ArrayList<String>();

                ListView list;
                ArrayAdapter<String> adp = new ArrayAdapter<String>
                        (getBaseContext(), R.layout.list, li);
                // Iterate over the result Cursor.
                while (cursor.moveToNext()) {
                    long eventid = 0;
                    list = (ListView) findViewById(R.id.myList);
                    list.setVisibility(View.VISIBLE);
                    Date d1 = new Date(cursor.getLong(D1));
                    System.out.print("First date" + d2);
                    String ccMonth = (String) android.text.format.DateFormat.format("MM", d1);
                    String ccyear = (String) android.text.format.DateFormat.format("yyyy", d1);
                    String ccday = (String) android.text.format.DateFormat.format("dd", d1);
                    // Extract the name.
                    String name = cursor.getString(nameIdx);
                    // Extract the unique ID.
                    String id = cursor.getString(idIdx);
                    result[cursor.getPosition()] = name + "(" + id + ")";
                    if (ccMonth.compareTo(cMonth) == 0 & ccday.compareTo(cday) == 0) {
                        eventid = cursor.getLong(idIdx);
                        nameid = name + ":" + id ;
                        li.add(nameid);
                        adp.notifyDataSetChanged();

                     /*   Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventid);
                        Intent intent = new Intent(Intent.ACTION_EDIT).setData(uri);
                        startActivity(intent);
                        */


                    }
                    list.setAdapter(adp);
                    final ListView finalList = list;
                    list.setClickable(true);
                    final ListView finalList1 = list;
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String val = (String) (finalList.getItemAtPosition(position));
                            String[] selectedid = val.split(":");
                            selectedid[1].trim();
                            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Long.parseLong(selectedid[1]));
                            Intent intent = new Intent(Intent.ACTION_VIEW).setData(uri);
                            startActivity(intent);

                            finalList1.setVisibility(View.GONE);
                        }
                    });
                }
            // }


        //});
    }

    public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_cal_event, menu);
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