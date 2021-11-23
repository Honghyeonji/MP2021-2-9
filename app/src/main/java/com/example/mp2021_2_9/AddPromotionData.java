package com.example.mp2021_2_9;

import com.google.firebase.database.IgnoreExtraProperties;
//등록페이지 입력한 데이터들을 담을 객체

@IgnoreExtraProperties
public class AddPromotionData {
    public String boothLocation,  boothName, boothOpenTime, boothImgurl, boothTxturl; //부스관련
    public String userid;
    public String goodsIsSoldout, goodsLocation, goodsName, goodsPrice, goodsImgurl,  goodsTxturl;

    public AddPromotionData(){}
    public AddPromotionData(String goodsLocation, String goodsName, String goodsPrice, String userid , String goodsIsSoldout){
        this.goodsLocation = goodsLocation;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this. userid = userid;
        this. goodsIsSoldout = goodsIsSoldout;
    }
    public AddPromotionData(String boothLocation, String boothName,String boothOpenTime, String userid){
        this.boothLocation = boothLocation;
        this.boothName =boothName;
        this.boothOpenTime = boothOpenTime;
        this.userid = userid;

    }


}
