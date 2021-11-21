package com.example.mp2021_2_9;

import com.google.firebase.database.IgnoreExtraProperties;
//등록페이지 입력한 데이터들을 담을 객체

@IgnoreExtraProperties
public class AddPromotionData {
    public String goodsid, location, goodsname, userid;
    public boolean soldout = false;
    public int price;

    public AddPromotionData(){}
    public AddPromotionData(String goodsid, String location, String goodsname,String userid, int price){
        this.goodsid = goodsid;
        this.location = location;
        this.goodsname =goodsname;
        this.userid = userid;
        this.price = price;
    }


}
