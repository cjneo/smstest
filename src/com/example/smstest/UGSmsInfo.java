package com.example.smstest;

public class UGSmsInfo {


        private String nameString;
        private String _id;
        private String thread_id;
        /**
         * ��������
         */
        private String smsbody;
        /**
         * ���Ͷ��ŵĵ绰����
         */
        private String phoneNumber;
        /**
         * ���Ͷ��ŵ����ں�ʱ��
         */
        private String person;
        
        private int sum=0;
        public int getSum() {
            return sum;
        }
        public void setSum(int num) {
            sum=num;
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


    


}
