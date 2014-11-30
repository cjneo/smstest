package com.example.smstest;

import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button analyseButton;
    TextView currentDateView;
    private ListView listview;

    private List<SmsInfo> infos;
    private List<ContactInfo> contactInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentDateView = (TextView) this.findViewById(R.id.TextDate);
        analyseButton = (Button) this.findViewById(R.id.analyse);
        analyseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                intent.putExtras(b);
                intent.setClass(MainActivity.this, AnalyseActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        String lastYearToday = DateHelper.getLastyearToday(DateHelper
                .getToday());
        currentDateView.setText(lastYearToday);

        Uri uri = Uri.parse(AllFinalInfo.SMS_URI_ALL);
        SmsContent sc = new SmsContent(this, uri);

  //      infos = sc.getPastSms();
        
        //infos = sc.getAllSms();
        infos =  sc.getSmsByPerson();
       // infos.sortByPhoneNum();
        uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        ContactContent contactContent =new ContactContent(this,uri);
        contactInfos = contactContent.getAllContact();
        
        listview = (ListView) this.findViewById(R.id.ListView_Sms);
        listview.setAdapter(new SmsListAdapter(this));
  
    }

    class SmsListAdapter extends BaseAdapter {
        private LayoutInflater layoutinflater;
        itemview myview;
        Date date;
        SimpleDateFormat sdf;

        public final class itemview {
            public TextView text;
            public TextView textinfo;
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
            if (convertView == null) {
                convertView = layoutinflater.inflate(R.layout.smsitem, null);
                myview = new itemview();
                myview.text = (TextView) convertView
                        .findViewById(R.id.TextView_SmsBody);
                myview.textinfo = (TextView) convertView
                        .findViewById(R.id.TextView_SmsInfo);
                convertView.setTag(myview);
            } else {
                myview = (itemview) convertView.getTag();

            }
            myview.text.setText(infos.get(position).getSmsbody());
            this.setDate(infos.get(position).getDate());
            String dateStr = this.sdf.format(date);

            String personid = infos.get(position).getperson();
            String phoneNumber=infos.get(position).getPhoneNumber();
            ContentResolver cr = getContentResolver();
            String selection=null;
             selection = ContactsContract.CommonDataKinds.Phone.DATA1+"="+phoneNumber;
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；  
            // 查询的字段  
            String name = "";
            String id="null";
            String phoneNum="";
            int i=0;
            for(;i<contactInfos.size();i++){
                if(contactInfos.get(i).getPhoneNum().equals(phoneNumber))
                  //  if(contactInfos.get(i).getPhoneNum()==new String("15600602714"))

                {
                    phoneNum=contactInfos.get(i).getPhoneNum();
                    name=contactInfos.get(i).getDesplayName();
                    break;
                }
            }
            String tmpinfo = "id:"+id+"name:" + name + "phonenum"+phoneNum+"person"
                    + infos.get(position).getperson() + " Date:" + dateStr
                    + " PhoneNumber:" + infos.get(position).getPhoneNumber()
                    + " Type:" + infos.get(position).getType() + " id:"
                    + infos.get(position).getid() + " threadid:"
                    + infos.get(position).getthread_id();

            myview.textinfo.setText(tmpinfo);

            return convertView;
        }

    }
}
