package com.dhvtsu.pampangafloodwatch.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by mleano on 5/27/2016.
 */
public class SmsReceiver extends BroadcastReceiver {

    public static String SMS_RECEIVE_FILTER = "SmsMessage.intent.MAIN";
    public static String SMS_MESSAGE = "message";
    public static String SMS_SENDER = "sender";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras == null)
            return;

        // To display a Toast whenever there is an SMS.
        //Toast.makeText(context,"Recieved",Toast.LENGTH_LONG).show();

        Object[] pdus = (Object[]) extras.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage SMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = SMessage.getOriginatingAddress();
            String message = SMessage.getMessageBody().toString();

            // A custom Intent that will used as another Broadcast
//            Intent in = new Intent("SmsMessage.intent.MAIN").
//                    putExtra("get_msg", sender + ":" + body);

            Intent in = new Intent(SMS_RECEIVE_FILTER);
            extras = new Bundle();
            extras.putString(SMS_SENDER, sender);
            extras.putString(SMS_MESSAGE, message);
            in.putExtras(extras);
            //You can place your check conditions here(on the SMS or the sender)
            //and then send another broadcast

            context.sendBroadcast(in);

            // This is used to abort the broadcast and can be used to silently
            // process incoming message and prevent it from further being
            // broadcasted. Avoid this, as this is not the way to program an app.
            // this.abortBroadcast();
        }
    }

}
