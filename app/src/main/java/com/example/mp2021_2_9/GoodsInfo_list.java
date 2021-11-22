package com.example.mp2021_2_9;

public class GoodsInfo_list {
    // manager 빼고 모두 스트링으로 저장, 불러오기!
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
    public String getName(){
        return goodsName;
    }
    public void setName(String name){ this.goodsName = name; }
    public String getPrice(){ return goodsPrice; }
    public void setPrice(String price){
        this.goodsPrice = price;
    }
    public String getImgUrl(){
        return goodsImgurl;
    }
    public void setImgUrl(String imgUrl){ this.goodsImgurl = imgUrl; }
    public boolean getIsSoldOut(){ return goodsIsSoldOut; }
    public void setIsSoldOut(boolean isSoldOut){
        this.goodsIsSoldOut = isSoldOut;
    }
    public String getLocation(){ return goodsLocation; }
    public void setLocation(String location){
        this.goodsLocation = location;
    }
    public String getTxtUrl(){ return goodsTxturl; }
    public void setTextUrl(String txtUrl){
        this.goodsTxturl = txtUrl;
    }


    // 그룹 생성시 사용
    public GoodsInfo_list(String userId, String name, String price, String imgUrl, boolean isSoldOut,
                          String location, String txtUrl){
        this.userId = userId;
        this.goodsName = name;
        this.goodsPrice = price;
        this.goodsImgurl = imgUrl;
        this.goodsIsSoldOut = isSoldOut;
        this.goodsLocation = location;
        this.goodsTxturl = txtUrl;
    }
}
