package com.example.smstest;  
  
public class ContactInfo {  
  
    private int contactId; //id  
    private String desplayName;//–’√˚  
    private String phoneNum; // µÁª∞∫≈¬Î  
    private String sortKey; // ≈≈–Ú”√µƒ  
    private Long photoId; // Õº∆¨id  
    private String lookUpKey;   
    private int selected = 0;  
    private String formattedNumber;  
    private String pinyin; // –’√˚∆¥“Ù  
  
    public int getContactId() {  
        return contactId;  
    }  
  
    public void setContactId(int contactId) {  
        this.contactId = contactId;  
    }  
  
    public String getDesplayName() {  
        return desplayName;  
    }  
  
    public void setDesplayName(String desplayName) {  
        this.desplayName = desplayName;  
    }  
  
    public String getPhoneNum() {  
        return phoneNum;  
    }  
  
    public void setPhoneNum(String phoneNum) {
        if(phoneNum.contains("+86")){
            phoneNum=phoneNum.substring(3);
        }
        this.phoneNum = phoneNum;  
    }  
  
    public String getSortKey() {  
        return sortKey;  
    }  
  
    public void setSortKey(String sortKey) {  
        this.sortKey = sortKey;  
    }  
  
    public Long getPhotoId() {  
        return photoId;  
    }  
  
    public void setPhotoId(Long photoId) {  
        this.photoId = photoId;  
    }  
  
    public String getLookUpKey() {  
        return lookUpKey;  
    }  
  
    public void setLookUpKey(String lookUpKey) {  
        this.lookUpKey = lookUpKey;  
    }  
  
    public int getSelected() {  
        return selected;  
    }  
  
    public void setSelected(int selected) {  
        this.selected = selected;  
    }  
  
    public String getFormattedNumber() {  
        return formattedNumber;  
    }  
  
    public void setFormattedNumber(String formattedNumber) {  
        this.formattedNumber = formattedNumber;  
    }  
  
    public String getPinyin() {  
        return pinyin;  
    }  
  
    public void setPinyin(String pinyin) {  
        this.pinyin = pinyin;  
    }  
  
}  