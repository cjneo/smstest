package com.example.smskankan;

public class UGSmsInfo implements Comparable<UGSmsInfo> {


        private String nameString;
        private String _id;
        private String thread_id;
        
        private String smsbody;
        
        private String phoneNumber;

        private String person;
        
        private int sumSms=0;
        public int getSum() {
            return sumSms;
        }
        public void setSum(int num) {
            sumSms=num;
        }
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
        @Override
        public int compareTo(UGSmsInfo another) {
            // TODO Auto-generated method stub
            int result=-(getSum()-another.getSum());
            return result;
        }


    


}
