package com.example.mp2021_2_9;

// 로그인 정보 snapshot으로 받아올 클래스
public class UserInfo_list {

    private String id;
    private String password;
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
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
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
    public void setManager(Boolean isManager){
        this.isManager = isManager;
    }

    // 그룹 생성시 사용
    public UserInfo_list(String id, String password, String userName, String phoneNum, Boolean isManager){
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.isManager = isManager;
    }
}
