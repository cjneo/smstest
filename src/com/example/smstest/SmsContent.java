package com.example.smstest;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

public class SmsContent {
    private Activity activity;
    private Uri uri;
    List<SmsInfo> infos;
   // List<UGsmsinfo> uginfos;

    public SmsContent(Activity activity, Uri uri) {
        infos = new ArrayList<SmsInfo>();
        this.activity = activity;
        this.uri = uri;
    }

    /*
     
     //* we dont need this function any more cause 
      * it is too slow 
      
    public void sortByPhoneNum() {
        int i,j;
        i=0;
        SmsInfo smstmp,sms1,sms2;
        if(infos.size()<=1)
            return;
        j=0;
        for(i=1;i<infos.size();i++){
           // j=i-1;
            for(j=i-1;j-1>=0;j--){
                
                if(infos.get(j-1).getPhoneNumber().compareTo(
                        infos.get(j).getPhoneNumber())<0)
                {
                    smstmp=infos.get(j-1);
                    infos.set(j-1,infos.get(j));
                    infos.set(j,smstmp);
                    
                }
                else break;
            }
        }
    }
*/
    public List<SmsInfo> getSmsByPerson() {
        Cursor cursor = getAllSmsCursorByPerson();
        return getSmsInfo(cursor);
    }
    public List<SmsInfo> getPastSms() {
        Cursor cursor = getPastSmsCursor();
        return getSmsInfo(cursor);
    }
    public List<SmsInfo> getIndexSms(int forward,int backward) {
        Cursor cursor = getIndexSmsCursor(forward,backward);
        return getSmsInfo(cursor);
    }
    public List<SmsInfo> getAllSms() {
        Cursor cursor = getAllSmsCursor();
        getSmsInfo(cursor);
   //     sortByPhoneNum();
        return infos;
    }

    @SuppressWarnings("deprecation")
    public List<SmsInfo> getSmsInfo(Cursor cusor) {

        int _idColumn = cusor.getColumnIndex("_id");
        int thread_idColumn = cusor.getColumnIndex("thread_id");
        int personColumn = cusor.getColumnIndex("person");
        int phoneNumberColumn = cusor.getColumnIndex("address");
        int smsbodyColumn = cusor.getColumnIndex("body");
        int dateColumn = cusor.getColumnIndex("date");
        int typeColumn = cusor.getColumnIndex("type");
        if (cusor != null) {
            while (cusor.moveToNext()) {
                // when a message is from the draft box
                // it contains no phonenum; it will cause a error
                if (cusor.getString(phoneNumberColumn) == null) {
                    continue;
                }
                SmsInfo smsinfo = new SmsInfo();
                smsinfo.setid(cusor.getString(0));
                smsinfo.setthread_id(cusor.getString(1));
                smsinfo.setperson(cusor.getString(personColumn));
                smsinfo.setDate(cusor.getLong(dateColumn));
                smsinfo.setPhoneNumber(cusor.getString(phoneNumberColumn));
                smsinfo.setSmsbody(cusor.getString(smsbodyColumn));
                smsinfo.setType(cusor.getInt(typeColumn));
                infos.add(smsinfo);
            }
            cusor.close();
        }

        return infos;
    }
    public Cursor getIndexSmsCursor(int forward,int backward) {
        String[] projection = new String[] { "_id", "thread_id", "address",
                "person", "body", "date", "type" };

        Cursor cusor = null;
        List<String> dateSelection = new ArrayList<String>();
        
        String lastYearToday = DateHelper.getStrFromDate(DateHelper
                .getIndexDay(DateHelper.getDateToday(), 3));
        Date lastyear=DateHelper.getIndexYear( DateHelper.getDateToday(),-1);
        Date lastyeardate1=DateHelper
                .getIndexDay(lastyear,forward);
        Date lastyeardate2=DateHelper
                .getIndexDay(lastyear,-backward);
            Date strtodate = lastyeardate1;

            long dateLong = strtodate.getTime();
            

            Date strtodate2 = lastyeardate2;
            long dateLong2 = strtodate2.getTime();
            String selection = "(( date" + " > " + Long.toString(dateLong2)
                    + ") AND ( date < " + Long.toString(dateLong) + "))";
            dateSelection.add(selection);
        
      //  String selection = "(" + dateSelection.get(0) + "OR"
        //        + dateSelection.get(1) + "OR" + dateSelection.get(2) + ")";
        ContentResolver cr = activity.getContentResolver();
        cusor = cr.query(uri, projection, selection, null, "date desc");

        return cusor;

    }
    public Cursor getPastSmsCursor() {
        String[] projection = new String[] { "_id", "thread_id", "address",
                "person", "body", "date", "type" };

        Cursor cusor = null;
        List<String> dateSelection = new ArrayList<String>();
        int looptime = 3;
        String strToday = DateHelper.getToday();
        while (looptime-- > 0) {
            String lastYearToday = DateHelper.getLastyearToday(strToday);
            String lastYearTomorrow = DateHelper.gettomorrow(lastYearToday);
            strToday = lastYearToday;
            String dateStr = lastYearToday;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(dateStr, pos);

            long dateLong = strtodate.getTime();
            String dateStr2 = lastYearTomorrow;
            ParsePosition pos2 = new ParsePosition(0);
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

            Date strtodate2 = formatter2.parse(dateStr2, pos2);
            long dateLong2 = strtodate2.getTime();
            String selection = "(( date" + " > " + Long.toString(dateLong)
                    + ") AND ( date < " + Long.toString(dateLong2) + "))";
            dateSelection.add(selection);
        }
        String selection = "(" + dateSelection.get(0) + "OR"
                + dateSelection.get(1) + "OR" + dateSelection.get(2) + ")";
        ContentResolver cr = activity.getContentResolver();
        cusor = cr.query(uri, projection, selection, null, "date desc");

        return cusor;

    }

    public Cursor getAllSmsCursor() {
        String[] projection = new String[] { "_id", "thread_id", "address",
                "person", "body", "date", "type" };

        Cursor cusor = null;
        ContentResolver cr = activity.getContentResolver();
        cusor = cr.query(uri, projection, null, null, "date desc");

        return cusor;

    }
     public Cursor getAllSmsCursorByPerson(){
         String[] projection = new String[] { "_id", "thread_id", "address",
                 "person", "body", "date", "type" };

         Cursor cusor = null;
         ContentResolver cr = activity.getContentResolver();
         cusor = cr.query(uri, projection, null, null, "thread_id desc");

         return cusor;
     }

}