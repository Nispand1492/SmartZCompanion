package com.example.nispand.smartzcompanion;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.lang.String;

public class CalEvent extends ActionBarActivity {
    CalendarView calendar;
    CalendarContract Calendars;
    Button Add, Search ,Delete;
    long cday;
    long cmonth;
    long cyear;
    long xyz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_event);
        Add = (Button) findViewById(R.id.Add);
        Search = (Button) findViewById(R.id.Search);
        Delete = (Button)findViewById(R.id.Delete);
        calendar = (CalendarView) findViewById(R.id.calendarView2);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.VISIBLE);
                initializeCalendar();
                add_event();
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.VISIBLE);
                initializeCalendar();
                search_event();
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.VISIBLE);
                initializeCalendar();
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
    public void add_event()
    {
        //sets the listener to be notified upon selected date change
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("BeginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("Rule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
                intent.putExtra("Title", "Put Title ");
                GregorianCalendar calDate = new GregorianCalendar(year, month, day);
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
                startActivity(intent);
            }
        });
    }
    public void search_event(){
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                long date1 = calendar.getDate();
                Date d2 = new Date(date1);
                String cday = (String)android.text.format.DateFormat.format("date",d2);
                String cmonth = (String) android.text.format.DateFormat.format("MMM", d2);
                System.out.print(""+cday+""+cmonth);
                String[] projection = { CalendarContract.Events._ID,
                        CalendarContract.Events.TITLE,CalendarContract.Events.DTSTART };
                // Get a Cursor over the Events Provider.
                Cursor cursor = getContentResolver().query(
                        CalendarContract.Events.CONTENT_URI, projection, null, null,
                        null);
                // Get the index of the columns.
                int nameIdx = cursor
                        .getColumnIndexOrThrow(CalendarContract.Events.TITLE);
                int idIdx = cursor.getColumnIndexOrThrow(CalendarContract.Events._ID);
                int D1 = cursor.getColumnIndexOrThrow(CalendarContract.Events.DTSTART);

                // Initialize the result set.
                String[] result = new String[cursor.getCount()];
                // Iterate over the result Cursor.
                while (cursor.moveToNext()) {
                    Date d1 = new Date (cursor.getLong(D1));
                    String ccday = (String)android.text.format.DateFormat.format("date",d1);
                    String ccmonth = (String) android.text.format.DateFormat.format("MMM", d1);
                    // Extract the name.
                    String name = cursor.getString(nameIdx);
                    // Extract the unique ID.
                    String id = cursor.getString(idIdx);
                    result[cursor.getPosition()] = name + "(" + id + ")";
                    if (cday.compareTo(ccday)==0 & cmonth.compareTo(ccmonth)==0) {
                        Toast.makeText(getApplicationContext(), name + "(" + id + ")" + "(" + d1 + ")", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
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