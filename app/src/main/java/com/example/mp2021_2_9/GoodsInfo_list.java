package com.example.mp2021_2_9;

public class GoodsInfo_list {
    private String userId;
    private String goodsName;
    private String goodsPrice;
    private String goodsImgurl;
    private boolean goodsIsSoldOut;
    private String goodsLocation;
    private String goodsTxturl;

    public GoodsInfo_list() {}

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId;}
    public String getGoodsName(){
        return goodsName;
    }
    public void setGoodsName(String goodsName){ this.goodsName = goodsName; }
    public String getGoodsPrice(){ return goodsPrice; }
    public void setGoodsPrice(String price){
        this.goodsPrice = price;
    }
    public String getGoodsImgUrl(){
        return goodsImgurl;
    }
    public void setGoodsImgUrl(String imgUrl){ this.goodsImgurl = imgUrl; }
    public boolean getGoodsIsSoldOut(){ return goodsIsSoldOut; }
    public void setGoodsIsSoldOut(boolean isSoldOut){
        this.goodsIsSoldOut = isSoldOut;
    }
    public String getGoodsLocation(){ return goodsLocation; }
    public void setGoodsLocation(String location){
        this.goodsLocation = location;
    }
    public String getGoodsTxtUrl(){ return goodsTxturl; }
    public void setGoodsTxtUrl(String txtUrl){
        this.goodsTxturl = txtUrl;
    }


    // 그룹 생성시 사용
    public GoodsInfo_list(String goodsImgurl,  boolean goodsIsSoldOut, String goodsLocation, String goodsName, String goodsPrice,
                          String goodsTxturl, String userId){
        this.goodsImgurl = goodsImgurl;
        this.goodsIsSoldOut = goodsIsSoldOut;
        this.goodsLocation = goodsLocation;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsTxturl = goodsTxturl;
        this.userId = userId;

    }
}
