package com.example.nispand.smartzcompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //Create Listner
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener(context);

            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }

    }

    private class MyPhoneStateListener extends PhoneStateListener {

        Context context; //Context to make Toast if required
        public MyPhoneStateListener(Context context) {
            super();
            this.context = context;
        }
        public void onCallStateChanged(int state, String incomingNumber) {
            SQLiteDatabase db = context.openOrCreateDatabase("SCPD",context.MODE_PRIVATE,null);

            Log.d("MyPhoneListener",state+"   incoming no:"+incomingNumber +"State:" +state);

            if (state == 2) {
                String sSelectQ="Select * from SCPD where ReminderSet = 1;";
                Cursor c= db.rawQuery(sSelectQ,null);
                String eventnote;

                String smobnum = incomingNumber;
                smobnum=smobnum.replaceAll("[^0-9.]", "");

                if(c!=null && c.getCount() > 0) {

                    c.moveToFirst();
                    eventnote =c.getString(c.getColumnIndex("Eventnote"));
                    String sContactNum =c.getString(c.getColumnIndex("ContactNumber"));
                    sContactNum=sContactNum.replaceAll("[^0-9.]", "");


                    Boolean bFinished = true;
                    do{

                        if(smobnum.equals(sContactNum))
                        {
                            for (int i = 0; i < 2; i++) {

                                    Toast toast = Toast.makeText(context, eventnote, Toast.LENGTH_LONG);
                                    toast.show();

                            }
                            bFinished = false;
                        }

                    }while(c.moveToNext()&&bFinished);


                }


//                String msg = "Mobile Number : " + smobnum;
//                int duration = Toast.LENGTH_LONG;
//
//                if (msg.matches(".*\\d.*")){
//                   // for (int i = 0; i < 2; i++) {
//                        if (msg.matches(".*\\d.*")) {
//                            Toast toast = Toast.makeText(context, msg, duration);
//                            toast.show();
//                        }
//                   // }
//                }
            }
        }
    }
}
