package com.example.smstest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AnalyseActivity extends Activity {
    private List<SmsInfo> infos;
    Uri uri;
    TextView analyseText;

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
        new Thread() {
            public void run() {
                int intPersonValid = 0;
                int intReceive = 0;
                int intSend = 0;
                if (infos.size() <= 0)
                    return;
                long longDate = infos.get(0).getDate();
                DateHelper dateHelper = new DateHelper();
                int smsNum = 0;
                String stringDate = dateHelper.getStringOfLong(longDate);
                stringDate = dateHelper.getThisMonth(stringDate);
                longDate = dateHelper.getLongOfString(stringDate);
                outputText += "\n" + stringDate;

                for (int i = 0; i < infos.size(); i++) {
                    int position = i;
                    String personid = infos.get(position).getperson();
                    long date = infos.get(position).getDate();

                    while (date < longDate) {
                        if (smsNum > 0) {
                            dateSms.put(stringDate, smsNum + "");
                            outputText += stringDate + ":" + smsNum + " ";
                        }
                        smsNum = 0;
                        stringDate = dateHelper.getLastMonth(stringDate);
                        longDate = dateHelper.getLongOfString(stringDate);
                    }

                    smsNum++;

                    if (personid != null) {
                        intPersonValid++;
                    }

                    int type = infos.get(position).getType();
                    // String typeone="1";
                    // String typetwo="2";
                    if (type == 1) {
                        intReceive++;
                    } else if (type == 2) {
                        intSend++;
                    }

                }

                outputText += "\n" + "其中you名字的短信有" + intPersonValid + "条";
                outputText += "\n" + "收到的短信为" + intReceive + "条";
                outputText += "\n" + "发出的短信为" + intSend + "条";

                Message message2 = new Message();
                message2.what = 2;
                uiHandler.sendMessage(message2);

            }
        }
        // .start()
        ;
        */
        new Thread() {
            public void run() {
                String ugUserNum=infos.get(0).getPhoneNumber();
                int sum=0;
                outputText +=  "sizeof" + infos.size() + " \n";
                for (int i = 0; i < infos.size(); i++) {
                    int position=i;
                    String tmpNum=infos.get(position).getPhoneNumber();
                    if(tmpNum==null){
                        continue;
                    }
                    else if(tmpNum.equals(ugUserNum)){
                        sum++;
                    }
                    else if(!(tmpNum.equals(ugUserNum))){
                        UGSmsInfo ug =new UGSmsInfo();
                      //  ug.setSum(sum);
                      //  ug.setNameString(ugUserNum);
                        outputText +=   ugUserNum+":" + sum + " \n";
                       // uginfos.add(ug);
                        sum=0;
                        ugUserNum=tmpNum;
                    }
                }
                outputText += "\n" + "发出的短信为"  + "条";
                Message message2 = new Message();
                message2.what = 2;
                uiHandler.sendMessage(message2);
            }
        }
        .start()
        ;
    }
}
