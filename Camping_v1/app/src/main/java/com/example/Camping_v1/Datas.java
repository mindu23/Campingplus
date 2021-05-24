package com.example.Camping_v1;

import android.provider.ContactsContract;

class UserData {
    String UserNum;
    String UserName;
    String UserId;
    String UserPassword;
    //String UserPasswordCheck;
    String USerEmail;
    String UserPhoneNum;
    Boolean Admin;


    //put
    public void putUserData(UserData user){
        putUserName(user.UserName);
        putUserId(user.UserId);
        putUserPassword(user.UserPassword);
        //putUserPasswordCheck(user.UserPasswordCheck);
        putUserEmail(user.USerEmail);
        putUserPhoneNum(user.UserPhoneNum);
        putAdmin(user.Admin);
    }
    public void putUserName(String Name){
        UserName=Name;
    }
    public  void putUserNum(String Num){
        UserNum = Num;
    }
    public  void putUserId(String Id){
        UserId = Id;
    }
    public  void putUserPassword(String Password){
        UserPassword = Password;
    }
    public void putAdmin(Boolean admin){Admin = admin;}
    //public  void putUserPasswordCheck(String PasswordCheck){
    //    UserPasswordCheck = PasswordCheck;
    //}
    public  void putUserEmail(String Email){
        USerEmail = Email;
    }
    public  void putUserPhoneNum(String PhoneNumber){
        UserPhoneNum = PhoneNumber;
    }

    //get
    public UserData getUserData(){
        return this;
    }
    public String getUserNum(){
        return UserNum;
    }
    public String getUserName(){
        return UserName;
    }
    public String getUserId(){
        return UserId;
    }
    public String getUserPassword(){
        return UserPassword;
    }
    public String getUSerEmail(){
        return USerEmail;
    }

}

class ReservationData {
    String UserNum;
    String UserEmail;
    String UserPhoneNum;
}
