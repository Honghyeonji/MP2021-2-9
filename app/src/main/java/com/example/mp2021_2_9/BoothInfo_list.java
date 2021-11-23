package com.example.mp2021_2_9;

import java.io.Serializable;

public class BoothInfo_list implements Serializable {
    private String boothImgurl;
    private String boothLocation;
    private String boothName;
    private String boothOpenTime;
    private String boothTxturl;
    private String userId;

    public BoothInfo_list(){ }

    public void setBoothImgurl(String boothImgurl) {
        this.boothImgurl = boothImgurl;
    }
    public String getBoothImgurl() {
        return boothImgurl;
    }
    public void setBoothLocation(String boothLocation) {
        this.boothLocation = boothLocation;
    }
    public String getBoothLocation() {
        return boothLocation;
    }
    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }
    public String getBoothName() {
        return boothName;
    }
    public void setBoothOpenTime(String boothOpenTime) {
        this.boothOpenTime = boothOpenTime;
    }
    public String getBoothOpenTime() {
        return boothOpenTime;
    }
    public void setBoothTxturl(String boothTxturl) {
        this.boothTxturl = boothTxturl;
    }
    public String getBoothTxturl() {
        return boothTxturl;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void BoothInfo_list(String boothImgurl, String boothLocation, String boothName, String boothOpenTime, String boothTxturl, String userId){
        this.boothImgurl = boothImgurl;
        this.boothLocation = boothLocation;
        this.boothName = boothName;
        this.boothOpenTime = boothOpenTime;
        this.boothTxturl = boothTxturl;
        this.userId = userId;
    }
}
