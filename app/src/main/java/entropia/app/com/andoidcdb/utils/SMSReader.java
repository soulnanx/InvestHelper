package entropia.app.com.andoidcdb.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import entropia.app.com.andoidcdb.pojo.Sms;

/**
 * Created by renan on 17/05/15.
 */
public class SMSReader {

    public static final String BRADESCO_ADDRESS = "23700";
    public static final String FILTER_BY_INV = "INV";
    public static final String AFTER_TEXT = "INV S/BX";

    public List<Sms> getAllSms(Activity activity, String address) {

        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = activity.getContentResolver();
        String SORT_ORDER = "date DESC";
        String WHERE_CONDITION = "address == %s";


        Cursor c = cr.query(message, null, String.format(WHERE_CONDITION, address), null, SORT_ORDER);
        activity.startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
//        c.close();

        return lstSms;
    }
}
