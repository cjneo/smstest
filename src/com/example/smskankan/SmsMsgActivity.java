package com.example.smskankan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.smskankan.R;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SmsMsgActivity extends ActionBarActivity{


    Button analyseButton;
    TextView currentDateView;
    private ListView listview;

    private List<SmsInfo> infos;
    private List<ContactInfo> contactInfos;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
      //  LayoutInflater inflater = LayoutInflater.from(this);  
       // View layout = inflater.inflate(R.layout.activity_main, null);
       setContentView(R.layout.activity_main);
        Resources resources = getBaseContext().getResources();   
        Drawable btnDrawable = resources.getDrawable(R.drawable.bg_smsmsg);  
       // layout.setBackgroundDrawable(btnDrawable);
        this.getWindow().setBackgroundDrawableResource(R.drawable.bg_smsmsg);
        int smsnum=1;
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        
         smsnum =b.getInt("smsnum");
        currentDateView = (TextView) this.findViewById(R.id.TextDate);
        analyseButton = (Button) this.findViewById(R.id.analyse);
        analyseButton.setVisibility(View.GONE);
        // String lastYearToday = DateHelper.getLastyearToday(DateHelper
        // .getToday());
        Date lastyear=DateHelper.getIndexYear( DateHelper.getDateToday(),-1);
        Date lastyeardate1=DateHelper
                .getIndexDay(lastyear,3);
        Date lastyeardate2=DateHelper
                .getIndexDay(lastyear,-4);
            Date strtodate = lastyeardate1;

            long dateLong = strtodate.getTime();
            

            Date strtodate2 = lastyeardate2;
            long dateLong2 = strtodate2.getTime();
            String selection =   DateHelper.getStrFromDate(strtodate) +"————"+ DateHelper.getStrFromDate(strtodate2) ;
        String lastYearToday = DateHelper.getStrFromDate(DateHelper
                .getIndexDay(DateHelper.getDateToday(), 3));
      //  currentDateView.setText(selection);
      //  currentDateView.setText(smsnum+" ");
        currentDateView.setText("");
        Uri uri = Uri.parse(AllFinalInfo.SMS_URI_ALL);
        SmsContent sc = new SmsContent(this, uri);

        infos=sc.getSmsByThread_id(smsnum);
        if(infos==null){
            return;
        }
        uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        ContactContent contactContent = new ContactContent(this, uri);
        contactInfos = contactContent.getAllContact();

        listview = (ListView) this.findViewById(R.id.ListView_Sms);
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listview.setCacheColorHint(0);
        listview.setAdapter(new SmsListAdapter(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载action items
        getMenuInflater().inflate(R.menu.analyse, menu);
        return true;
    }

    class SmsListAdapter extends BaseAdapter {
        private LayoutInflater layoutinflater;
        itemview myview;
        Date date;
        SimpleDateFormat sdf;

        public final class itemview {
            public TextView text;
            public TextView textinfo;
            public TextView textnum;
        };

        public SmsListAdapter(Context c) {
            layoutinflater = LayoutInflater.from(c);
            this.date = new Date();
            this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }

        public void setDate(Long date) {
            this.date.setTime(date);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = infos.get(position).getType();
            //if (convertView == null) 
            {
                if (type == 2) {// msg send
                    convertView = layoutinflater
                            .inflate(R.layout.smsitem, null);
                    myview = new itemview();
                    myview.text = (TextView) convertView
                            .findViewById(R.id.TextView_SmsBody);
                    myview.textinfo = (TextView) convertView
                            .findViewById(R.id.TextView_SmsInfo);
                    myview.textnum = (TextView) convertView
                            .findViewById(R.id.TextView_SmsNum);
                } else {
                    convertView = layoutinflater.inflate(
                            R.layout.smsitem_other, null);
                    myview = new itemview();
                    myview.text = (TextView) convertView
                            .findViewById(R.id.TextView_SmsBody_other);
                    myview.textinfo = (TextView) convertView
                            .findViewById(R.id.TextView_SmsInfo_other);
                    myview.textnum = (TextView) convertView
                            .findViewById(R.id.TextView_SmsNum_other);
                }
                convertView.setTag(myview);
            } 
           // 
           // else {
             //   myview = (itemview) convertView.getTag();

            //}

            this.setDate(infos.get(position).getDate());
            String dateStr = this.sdf.format(date);

            String personid = infos.get(position).getperson();
            String phoneNumber = infos.get(position).getPhoneNumber();
            ContentResolver cr = getContentResolver();
            String selection = null;
            selection = ContactsContract.CommonDataKinds.Phone.DATA1 + "="
                    + phoneNumber;
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
            // 查询的字段
            String name = "";
            String id = "null";
            String phoneNum = "";
            int i = 0;
            for (; i < contactInfos.size(); i++) {
                if (contactInfos.get(i).getPhoneNum().equals(phoneNumber))
                // if(contactInfos.get(i).getPhoneNum()==new
                // String("15600602714"))

                {
                    phoneNum = contactInfos.get(i).getPhoneNum();
                    name = contactInfos.get(i).getDesplayName();
                    break;
                }
            }
            String tmpinfo = "id:" + id + "name:" + name + "phonenum"
                    + phoneNum + "person" + infos.get(position).getperson()
                    + " Date:" + dateStr + " PhoneNumber:"
                    + infos.get(position).getPhoneNumber() + " Type:"
                    + infos.get(position).getType() + " id:"
                    + infos.get(position).getid() + " threadid:"
                    + infos.get(position).getthread_id();
            if (type == 1) {//receive
                if (!name.equals("")) {
                    myview.textnum.setText(name + ":");
                } else {
                    myview.textnum.setText(phoneNumber + ":");
                }

                myview.text.setText(infos.get(position).getSmsbody());
                myview.textinfo.setText("接收日期："+dateStr);
            }
            else{
                
                if (!name.equals("")) {
                    myview.textnum.setText("我    :  "+name );
                } else {
                    myview.textnum.setText("我    :  "+ phoneNumber);
                }  
                
                

                myview.text.setText(infos.get(position).getSmsbody());
                myview.textinfo.setText("发送日期："+dateStr);
            }
            return convertView;
        }

    }
    
}
    


