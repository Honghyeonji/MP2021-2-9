package com.example.mp2021_2_9;

public class GoodsInfo_list {
    // soldout 빼고 모두 스트링으로 저장, 불러오기!
    private String userId;
    private String goodsName;
    private String goodsPrice;
    private String goodsImgurl;
    private String goodsLocation;
    private String goodsTxturl;
    private boolean goodsIsSoldOut;
    //private String goodsMapUrl;

    public GoodsInfo_list() {}

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId;}
    public String getGoodsName(){
        return goodsName;
    }
    public void setGoodsName(String goodsName){ this.goodsName = goodsName; }
    public String getGoodsPrice(){ return goodsPrice; }
    public void setGoodsPrice(String goodsPrice){
        this.goodsPrice = goodsPrice;
    }
    public String getGoodsImgUrl(){
        return goodsImgurl;
    }
    public void setGoodsImgUrl(String goodsImgurl){ this.goodsImgurl = goodsImgurl; }
    public boolean getGoodsIsSoldOut(){
        return goodsIsSoldOut;
    }
    public void setGoodsIsSoldOut(boolean goodsIsSoldOut) {
        goodsIsSoldOut = goodsIsSoldOut;
    }
    public String getGoodsLocation(){
        return goodsLocation;
    }
    public void setGoodsLocation(String goodsLocation){
        this.goodsLocation = goodsLocation;
    }
    public String getGoodsTxtUrl(){
        return goodsTxturl;
    }
    public void setGoodsTxtUrl(String goodsTxturl){
        this.goodsTxturl = goodsTxturl;
    }

    /*public String getMapUrl(){
        return mapUrl;
    }
    //public void setMapUrl(String mapUrl){
        this.mapUrl = mapUrl;
    }

    */

    // 그룹 생성시 사용
    public GoodsInfo_list(String userId, String goodsName, String goodsPrice, String goodsImgurl, boolean goodsIsSoldOut,
                          String goodsLocation, String goodsTxturl){
        this.userId = userId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsImgurl = goodsImgurl;
        this.goodsIsSoldOut = goodsIsSoldOut;
        this.goodsLocation = goodsLocation;
        this.goodsTxturl = goodsTxturl;
        //this.mapUrl = mapUrl;
    }
}