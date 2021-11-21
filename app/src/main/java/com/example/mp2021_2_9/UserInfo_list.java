package com.example.mp2021_2_9;

import java.util.IdentityHashMap;

// 로그인 정보 snapshot으로 받아올 클래스
public class UserInfo_list {

    private String id;
    private String passward;
    private String userName;
    private String phoneNum;
    private boolean isManager;

    public UserInfo_list() {}

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getPassward(){
        return passward;
    }
    public void setPassward(String passward){
        this.passward = passward;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String name){
        this.userName = name;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }
    public boolean getIsManager(){
        return isManager;
    }
    public void setManager(boolean isManager){
        this.isManager = isManager;
    }

    // 그룹 생성시 사용
    public UserInfo_list(String id, String passward, String userName, String phoneNum, boolean isManager){
        this.id = id;
        this.passward = passward;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.isManager = isManager;
    }
}
