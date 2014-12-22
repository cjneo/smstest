package com.example.smskankan;

public class SmsInfo {
    private String nameString;
    private String _id;
    private String thread_id;
    /**
     * 短信内容
     */
    private String smsbody;
    /**
     * 发送短信的电话号码
     */
    private String phoneNumber;
    /**
     * 发送短信的日期和时间
     */
    private Long date;
    /**
     * 发送短信人的姓名这项不存在
     * person
     */
    private String person;
    /**
     * 
     * 短信类型1是接收到的，2是已发出
     */
    private int type;
    public String getNameString() {
        return nameString;
    }
    public void setNameString(String name) {
        this.nameString = name;
    } 
    public String getid() {
        return _id;
    }
    public void setid(String _id) {
        this._id = _id;
    } 
    public String getthread_id() {
        return thread_id;
    }
    public void setthread_id(String thread_id) {
        this.thread_id = thread_id;
    }
    public String getSmsbody() {
        return smsbody;
    }
    public void setSmsbody(String smsbody) {
        this.smsbody = smsbody;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.contains("+86")){
            phoneNumber=phoneNumber.substring(3);
        }
        this.phoneNumber = phoneNumber;
    }
    public Long getDate() {
        return date;
    }
    public void setDate(Long date) {
        this.date = date;
    }
    public String getperson() {
        return person;
    }
    public void setperson(String person) {
        this.person = person;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {

        this.type = type;

    }

}
