package com.example.smskankan;

import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.smskankan.R;
import com.example.smskankan.MainActivity.myPagerAdapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;

public class MainActivity extends ActionBarActivity {
    private ViewPager mViewPager;
    private PagerTitleStrip mPagerTitleStrip;
     ArrayList<String> titles;
     ArrayList<View> views;
    private List<SmsInfo> infos;
    Uri uri;
    TextView analyseText;
    List<ContactInfo> contactInfos;
    ProgressBar bar3;
    String urlRankList;
    ListView listview;
    List<Map<String, Object>> listems;
    Map<String, Object> listem;
    SimpleAdapter simplead;
    Map<String, String> dateSms = new HashMap<String, String>();
    Map<String, SmsNameNum> hashOfMonth = new HashMap<String, SmsNameNum>();
    ContactContent contactContent;

    List<UGSmsInfo> uginfos = new ArrayList<UGSmsInfo>();

    String outputText = "";

    Handler uiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:

                analyseText.setText(outputText);
                break;
            case 2:
                analyseText.setText(outputText);
                bar3.setVisibility(View.INVISIBLE);

                break;
            case 3:

                int count = 1;
                String namefirst="";

                for (int i = 0; i < uginfos.size(); i++) {
                    UGSmsInfo ug = uginfos.get(i);
                    // int j=i+1;
                    if (ug.getNameString() != null) {
                        // outputText += (count++) + ": " + ug.getNameString()
                        // + " " + ug.getSum() + "\n";
                        listem = new HashMap<String, Object>();
                        if(count==1)
                            namefirst=ug.getNameString();
                        listem.put("smsname",
                                "" + count + " " + ug.getNameString());
                        listem.put("smstime", " ");
                        listem.put("smssum", ug.getSum() + " ");
                        listem.put("smsnum",ug.getthread_id());
                        listems.add(listem);
                        count++;

                    }
                }
                outputText += "\n" + "短信联系的" + listems.size() + "个";
                if (uginfos.size() > 0) {
                    outputText += "\n" + "短信来往最多的是："
                            + namefirst;
                }
                analyseText.setText(outputText);

                simplead.notifyDataSetChanged();
                break;
            }
            super.handleMessage(msg);
        }
    };

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.analyse);
       // mViewPager = (ViewPager)findViewById(R.id.viewpager);
       // mPagerTitleStrip = (PagerTitleStrip)findViewById(R.id.pagertitle);
         
        //将要分页显示的View装入数组中
       // LayoutInflater mLi = LayoutInflater.from(this);
       // View view2 = mLi.inflate(R.layout.layout1, null);
       // View view1 = mLi.inflate(R.layout.analyse, null);
     
        //每个页面的Title数据
//         views = new ArrayList<View>();
  //       views.add(view2);
    //     views.add(view1);
        
         
        // titles = new ArrayList<String>();
       // titles.add("统计");
      //  titles.add("联系人");
       // titles.add("联系人" );
      //  Button button = (Button) view1.findViewById(R.id.button_1);
        
        
//        mViewPager.setAdapter(new myPagerAdapter());

        
//        bar3 = (ProgressBar) view1.findViewById(R.id.bar3);
        bar3 = (ProgressBar) findViewById(R.id.bar3);

        bar3.setVisibility(View.VISIBLE);
        analyseText = (TextView) view1.findViewById(R.id.analyseText);
        uri = Uri.parse(AllFinalInfo.SMS_URI_ALL);
        analyseText.setMovementMethod(ScrollingMovementMethod.getInstance());

        listview = (ListView) findViewById(R.id.ranklistview);
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listview.setCacheColorHint(0);
        listems = new ArrayList<Map<String, Object>>();

        SmsContent sc = new SmsContent(this, uri);
        // infos = sc.getAllSms();
        infos = sc.getSmsByPerson();

        uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        contactContent = new ContactContent(this, uri);
        contactInfos = contactContent.getAllContact();
        
        if(infos==null||contactInfos==null){
            outputText+="没有您的权限允许，可不能正常运行哦\n请授权后再使用";
            analyseText.setText(outputText);
            return;
        }
        if(infos.size()==0||contactInfos.size()==0){
            outputText+="没有您的权限允许，可不能正常运行哦 \n请授权后再使用\n"+
        "抱歉并未读到通讯录中联系人信息或者与联系人有关的短信信息";
            analyseText.setText(outputText);
            return;
        }
        outputText +="您一共有短信" + infos.size() + "条\n" ;
        outputText +=   "联系人"
                + contactInfos.size() + "个\n";

        simplead = new SimpleAdapter(this, listems, R.layout.listitem,
                new String[] { "smsname", "smstime", "smssum" }, new int[] {
                        R.id.smsname, R.id.smstime, R.id.smssum });

        listview.setAdapter(simplead);
        listview.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
               // int smsnum=(Integer) listems.get(position).get(new String("smsnum"));
                int smsnum=1;
                String ii;
                ii= (String) listems.get(position).get("smsnum");
                smsnum=Integer.valueOf(ii).intValue();
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putInt("smsnum",smsnum);
                intent.putExtras(b);
                intent.setClass(MainActivity.this, SmsMsgActivity.class);
              //  intent.setClass(MainActivity.this, AnalyseActivity.class);
                startActivityForResult(intent, 0);
            }
            
        });
   
        new Thread() {
            public void run() {
                String ugUserNum = infos.get(0).getthread_id();
                String ugUserPerson = infos.get(0).getperson();
                String ugUserPhoneNum = null;
                String ugUserName = null;
                int sum = 0;
                boolean isGetName = false;
                int tryGetNameTime = 0;

                for (int i = 0; i < infos.size(); i++) {
                    int position = i;
                    String tmpNum = infos.get(position).getthread_id();

                    if (tmpNum.equals(ugUserNum)) {
                        sum++;
                    } else if (!(tmpNum.equals(ugUserNum))) {

                        if (sum > 0) {

                            // outputText += tmpNum+":"+ugUserPhoneNum + ":" +
                            // sum + " |"
                            // + ugUserName + " \n";
                            UGSmsInfo ug = new UGSmsInfo();
                            ug.setSum(sum);
                            ug.setNameString(ugUserName);
                            ug.setthread_id(ugUserNum);
                            uginfos.add(ug);
                        }

                        sum = 1;
                        ugUserNum = tmpNum;
                        ugUserPerson = null;
                        isGetName = false;
                        tryGetNameTime = 0;
                        ugUserName = null;
                        ugUserPhoneNum = infos.get(position).getPhoneNumber();
                    }
                    if (!isGetName) {
                        if (infos.get(position).getType() == 2) {
                            if (tryGetNameTime < 3) {
                                ugUserPhoneNum = infos.get(position)
                                        .getPhoneNumber();
                                tryGetNameTime++;
                                ugUserName = contactContent
                                        .findNameByPhoneNum(ugUserPhoneNum);
                                if (ugUserName != null) {
                                    isGetName = true;
                                }
                            }
                        }
                    }
                }
                Collections.sort(uginfos);
                int count = 1;
                for (int i = 0; i < uginfos.size(); i++) {
                    UGSmsInfo ug = uginfos.get(i);

                    if (ug.getNameString() != null) {
                        // outputText += (count++) + ": " + ug.getNameString()
                        // + " " + ug.getSum() + "\n";
                    }
                }

                Message message2 = new Message();
                message2.what = 2;
                uiHandler.sendMessage(message2);

                Message message3 = new Message();
                message3.what = 3;
                uiHandler.sendMessage(message3);
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载action items
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_analyse:
            Intent intent = new Intent();
            Bundle b = new Bundle();
            intent.putExtras(b);
            intent.setClass(MainActivity.this, AnalyseActivity.class);
            startActivityForResult(intent, 0);
            break;
        }
        return true;
    }
    /*
     * public void tryContactInfo() { uri =
     * ContactsContract.Contacts.CONTENT_URI; uri =
     * ContactsContract.CommonDataKinds.Phone.CONTENT_URI; ContactContent
     * contactContent = new ContactContent(this, uri); contactInfos =
     * contactContent.getAllContact(); getContact(); new Thread() { public void
     * run() { for (int i = 0; i < contactInfos.size(); i++) { ContactInfo info
     * = contactInfos.get(i); outputText += info.getContactId() + " " +
     * info.getDesplayName();
     * 
     * } Message message2 = new Message(); message2.what = 2;
     * uiHandler.sendMessage(message2); }
     * 
     * }.start(); }
     * 
     * public void getContact() { // 获得所有的联系人 Cursor cur =
     * getContentResolver().query( ContactsContract.Contacts.CONTENT_URI, null,
     * null, null, null); // 循环遍历 if (cur.moveToFirst()) { int idColumn =
     * cur.getColumnIndex(ContactsContract.Contacts._ID); int displayNameColumn
     * = cur .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME); do { //
     * 获得联系人的ID号 String contactId = cur.getString(idColumn); // 获得联系人姓名 String
     * disPlayName = cur.getString(displayNameColumn);
     * outputText+=contactId+" "+disPlayName; // 查看该联系人有多少个电话号码。如果没有这返回值为0 int
     * phoneCount = cur .getInt(cur
     * .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)); if
     * (phoneCount > 0) { // 获得联系人的电话号码 Cursor phones =
     * getContentResolver().query(
     * ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
     * ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
     * null, null); if (phones.moveToFirst()) { do { // 遍历所有的电话号码 String
     * phoneNumber = phones .getString(phones
     * .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
     * System.out.println(phoneNumber); } while (phones.moveToNext()); } } }
     * while (cur.moveToNext()); } Message message2 = new Message();
     * message2.what = 2; uiHandler.sendMessage(message2); }
     */
    
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
    class myPagerAdapter extends PagerAdapter{
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
         
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(views.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
            //return "";
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(views.get(position));
            return views.get(position);
        }
    }
    @Override    
    protected void onDestroy() {    
        super.onDestroy();    
         //写自己的代码，一定要在super.onDestory()下面写  
    }  
}