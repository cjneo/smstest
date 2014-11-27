package com.example.smstest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AnalyseActivity extends Activity {
    private List<SmsInfo> infos;
    Uri uri;
    TextView analyseText;
    List<ContactInfo> contactInfos;
    private ProgressBar bar3;
    Map<String, String> dateSms = new HashMap<String, String>();
    Map<String, SmsNameNum> hashOfMonth = new HashMap<String, SmsNameNum>();

    List<UGSmsInfo> uginfos;

    String outputText = null;

    Handler uiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
                analyseText.setText(outputText);
                break;
            case 2:
                analyseText.setText(outputText);
                break;
            case 3:
                tryContactInfo();
                break;
            }
            super.handleMessage(msg);
        }
    };

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.analyse);
        bar3 = (ProgressBar) findViewById(R.id.bar3);
        bar3.setVisibility(View.VISIBLE);
        analyseText = (TextView) findViewById(R.id.analyseText);
        uri = Uri.parse(AllFinalInfo.SMS_URI_ALL);
        analyseText.setMovementMethod(ScrollingMovementMethod.getInstance());
        SmsContent sc = new SmsContent(this, uri);
        // infos = sc.getAllSms();
        infos = sc.getSmsByPerson();
        outputText = "ok";
        outputText += "\n" + "你一共有/" + infos.size() + "条短信";
        analyseText.setText(outputText);
        // this function count the number of sms of each month
        /*
         * new Thread() { public void run() { int intPersonValid = 0; int
         * intReceive = 0; int intSend = 0; if (infos.size() <= 0) return; long
         * longDate = infos.get(0).getDate(); DateHelper dateHelper = new
         * DateHelper(); int smsNum = 0; String stringDate =
         * dateHelper.getStringOfLong(longDate); stringDate =
         * dateHelper.getThisMonth(stringDate); longDate =
         * dateHelper.getLongOfString(stringDate); outputText += "\n" +
         * stringDate;
         * 
         * for (int i = 0; i < infos.size(); i++) { int position = i; String
         * personid = infos.get(position).getperson(); long date =
         * infos.get(position).getDate();
         * 
         * while (date < longDate) { if (smsNum > 0) { dateSms.put(stringDate,
         * smsNum + ""); outputText += stringDate + ":" + smsNum + " "; } smsNum
         * = 0; stringDate = dateHelper.getLastMonth(stringDate); longDate =
         * dateHelper.getLongOfString(stringDate); }
         * 
         * smsNum++;
         * 
         * if (personid != null) { intPersonValid++; }
         * 
         * int type = infos.get(position).getType(); // String typeone="1"; //
         * String typetwo="2"; if (type == 1) { intReceive++; } else if (type ==
         * 2) { intSend++; }
         * 
         * }
         * 
         * outputText += "\n" + "其中you名字的短信有" + intPersonValid + "条"; outputText
         * += "\n" + "收到的短信为" + intReceive + "条"; outputText += "\n" + "发出的短信为"
         * + intSend + "条";
         * 
         * Message message2 = new Message(); message2.what = 2;
         * uiHandler.sendMessage(message2);
         * 
         * } } // .start() ;
         */
        new Thread() {
            public void run() {
                String ugUserNum = infos.get(0).getthread_id();
                String ugUserPerson = infos.get(0).getperson();
                int sum = 0;
                outputText += "sizeof" + infos.size() + " \n";
                for (int i = 0; i < infos.size(); i++) {
                    int position = i;
                    String tmpNum = infos.get(position).getthread_id();
                    if (ugUserPerson == null) {
                        ugUserPerson = infos.get(i).getperson();
                    }
                    if (tmpNum.equals(ugUserNum)) {
                        sum++;
                    } else if (!(tmpNum.equals(ugUserNum))) {
                        UGSmsInfo ug = new UGSmsInfo();
                        // ug.setSum(sum);
                        // ug.setNameString(ugUserNum);
                        outputText += ugUserNum + ":" + sum + " |"
                                + ugUserPerson + " \n";
                        // uginfos.add(ug);
                        sum = 1;
                        ugUserNum = tmpNum;
                        ugUserPerson = null;
                    }
                }
                outputText += "\n" + "发出的短信为" + "条";
                Message message2 = new Message();
                message2.what = 2;
                uiHandler.sendMessage(message2);

                Message message3 = new Message();
                message3.what = 3;
                uiHandler.sendMessage(message3);
            }
        }.start();
    }

    public void tryContactInfo() {
        uri = ContactsContract.Contacts.CONTENT_URI;
        uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        ContactContent contactContent = new ContactContent(this, uri);
        contactInfos = contactContent.getAllContact();
        getContact();
        new Thread() {
            public void run() {
                for (int i = 0; i < contactInfos.size(); i++) {
                    ContactInfo info = contactInfos.get(i);
                    outputText += info.getContactId() + " "
                            + info.getDesplayName();

                }
                Message message2 = new Message();
                message2.what = 2;
                uiHandler.sendMessage(message2);
            }
            
        }.start();
    }

    public void getContact() {
        // 获得所有的联系人
        Cursor cur = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        // 循环遍历
        if (cur.moveToFirst()) {
            int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cur
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            do {
                // 获得联系人的ID号
                String contactId = cur.getString(idColumn);
                // 获得联系人姓名
                String disPlayName = cur.getString(displayNameColumn);
                outputText+=contactId+" "+disPlayName;
                // 查看该联系人有多少个电话号码。如果没有这返回值为0
                int phoneCount = cur
                        .getInt(cur
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    // 获得联系人的电话号码
                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
                    if (phones.moveToFirst()) {
                        do {
                            // 遍历所有的电话号码
                            String phoneNumber = phones
                                    .getString(phones
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            System.out.println(phoneNumber);
                        } while (phones.moveToNext());
                    }
                }
            } while (cur.moveToNext());
        }
        Message message2 = new Message();
        message2.what = 2;
        uiHandler.sendMessage(message2);
    }

}
