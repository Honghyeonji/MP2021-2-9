package com.example.mp2021_2_9;

public class GoodsInfo_list {
    // manager 빼고 모두 스트링으로 저장, 불러오기!
    private String userId;
    private String goodsName;
    private String goodsPrice;
    private String goodsImgurl;
    private boolean isSoldOut;
    private String goodsLocation;
    private String goodsTexturl;

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
    public boolean getSoldOut(){
        return isSoldOut;
    }
    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }
    public String getGoodsLocation(){
        return goodsLocation;
    }
    public void setGoodsLocation(String goodsLocation){
        this.goodsLocation = goodsLocation;
    }
    public String getGoodsTextUrl(){
        return goodsTexturl;
    }
    public void setGoodsTextUrl(String goodsTexturl){
        this.goodsTexturl = goodsTexturl;
    }

    /*public String getMapUrl(){
        return mapUrl;
    }
    //public void setMapUrl(String mapUrl){
        this.mapUrl = mapUrl;
    }

    */

    // 그룹 생성시 사용
    public GoodsInfo_list(String userId, String goodsName, String goodsPrice, String goodsImgurl, boolean isSoldOut,
                          String goodsLocation, String goodsTexturl){
        this.userId = userId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsImgurl = goodsImgurl;
        this.isSoldOut = isSoldOut;
        this.goodsLocation = goodsLocation;
        this.goodsTexturl = goodsTexturl;
        //this.mapUrl = mapUrl;
    }
}
