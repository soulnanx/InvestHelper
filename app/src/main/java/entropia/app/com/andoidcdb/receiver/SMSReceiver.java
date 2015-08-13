package entropia.app.com.andoidcdb.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;

import entropia.app.com.andoidcdb.notification.CustomNotification;
import entropia.app.com.andoidcdb.utils.SMSReader;

public class SMSReceiver extends BroadcastReceiver {

    private SharedPreferences preferences;

    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();

                        if (msg_from.equals(SMSReader.BRADESCO_ADDRESS) || msg_from.contains(SMSReader.BRADESCO_ADDRESS)){
                            CustomNotification.sendNotification(context, "novos dados");
                        }

                        String msgBody = msgs[i].getMessageBody();
                    }
                }catch(Exception e){
                }
            }
        }
    }
}
