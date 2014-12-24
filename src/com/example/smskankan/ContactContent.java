package com.example.smskankan;

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
import android.provider.ContactsContract;

public class ContactContent {
    private Activity activity;// 这里有个activity对象，不知道为啥以前好像不要，现在就要了。自己试试吧。
    private Uri uri;
    List<ContactInfo> infos;

    public ContactContent(Activity activity, Uri uri) {
        infos = new ArrayList<ContactInfo>();
        this.activity = activity;
        this.uri = uri;
    }
    public List<ContactInfo> getAllContact(){
        Cursor cursor=  getAllContactCursor();
        return getContactInfo(cursor);
      }
    @SuppressWarnings("deprecation")
    public List<ContactInfo> getContactInfo(Cursor cursor) {
        
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);  
                String number = cursor.getString(2);  
                String sortKey = cursor.getString(3);  
                int contactId = cursor.getInt(4);  
                Long photoId = cursor.getLong(5);  
                String lookUpKey = cursor.getString(6);  

                    // 创建联系人对象  
                    ContactInfo contact = new ContactInfo();  
                    contact.setDesplayName(name);  
                    contact.setPhoneNum(number);  
                    contact.setSortKey(sortKey);  
                    contact.setPhotoId(photoId);  
                    contact.setLookUpKey(lookUpKey);  
                    contact.setContactId(contactId);
                    infos.add(contact);
            }
            cursor.close();
        }
           // contactIdMap.put(contactId, contact);
        return infos;
    }
    
    
    public Cursor getAllContactCursor(){
        /*
        String[] projection = new String[] { "_id", "thread_id", "address",
                "person", "body", "date", "type" };
                */
        String[] projection = { 
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,  
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,  
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
                
        
        };  
        Cursor cusor = null;
        ContentResolver cr = activity.getContentResolver();
         cusor = cr.query(uri, projection, null,null,null);
        
        return cusor;
    }
    
    public String findNameByPhoneNum(String phoneNumber){
    for(int i=0;i<infos.size();i++){
        if(infos.get(i).getPhoneNum().equals(phoneNumber))
        {
            String name=infos.get(i).getDesplayName();
            return name;
        }
    }
    return null;
    }
    
}