package com.example.mp2021_2_9;

import com.google.firebase.database.IgnoreExtraProperties;
//등록페이지 입력한 데이터들을 담을 객체

@IgnoreExtraProperties
public class AddPromotionData {
    public String boothLocation,  boothName, boothOpenTime, boothImgurl, boothTxturl; //부스관련
    public String userid, key;
    public String goodsLocation, goodsName, goodsPrice,   goodsTxturl;
    public String goodsImgurl;
    public boolean goodsIsSoldOut;

    public AddPromotionData(){}
    //굿즈페이지에 작성된 정보들 저장용
    public AddPromotionData(String goodsImgurl,boolean goodsIsSoldOut, String goodsLocation, String goodsName, String goodsPrice, String key, String userid, String goodsTxturl){
        this.goodsImgurl = goodsImgurl;
        this.goodsLocation = goodsLocation;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.key = key;
        this.userid = userid;
        this.goodsIsSoldOut = goodsIsSoldOut;
        this.goodsTxturl = goodsTxturl;
    }
    //부스페이지에 작성된 정보 저장용
    public AddPromotionData(String boothImgurl,String boothLocation, String boothName,String boothOpenTime, String userid, String boothTxturl){
        this.boothImgurl = boothImgurl;
        this.boothLocation = boothLocation;
        this.boothName =boothName;
        this.boothOpenTime = boothOpenTime;
        this.userid = userid;
        this.boothTxturl = boothTxturl;

    }


}
