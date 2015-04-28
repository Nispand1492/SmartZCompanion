package com.example.nispand.smartzcompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

            Log.d("MyPhoneListener",state+"   incoming no:"+incomingNumber +"State:" +state);

            if (state == 2) {
                String smobnum = incomingNumber;
                String msg = "Mobile Number : " + smobnum;
                int duration = Toast.LENGTH_LONG;
                if (msg.matches(".*\\d.*")){
                   // for (int i = 0; i < 2; i++) {
                        if (msg.matches(".*\\d.*")) {
                            Toast toast = Toast.makeText(context, msg, duration);
                            toast.show();
                        }
                   // }
                }
            }
        }
    }
}
