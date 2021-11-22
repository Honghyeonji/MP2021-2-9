package com.example.mp2021_2_9;

public class GoodsInfo_list {
    // manager 빼고 모두 스트링으로 저장, 불러오기!
    private String userId;
    private String name;
    private String price;
    private String imgUrl;
    private boolean isSoldOut;
    private String location;
    private String mapUrl;
    private String textUrl;

    public GoodsInfo_list() {}

    public String getUserId(){ return userId; }
    public void setUserId(String userId){ this.userId = userId;}
    public String getName(){
        return name;
    }
    public void setName(String name){ this.name = name; }
    public String getPrice(){ return price; }
    public void setPrice(String price){
        this.price = price;
    }
    public String getImgUrl(){
        return imgUrl;
    }
    public void setImgUrl(String imgUrl){ this.imgUrl = imgUrl; }
    public boolean getIsSoldOut(){ return isSoldOut; }
    public void setIsSoldOut(boolean isSoldOut){
        this.isSoldOut = isSoldOut;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getMapUrl(){
        return mapUrl;
    }
    public void setMapUrl(String mapUrl){
        this.mapUrl = mapUrl;
    }
    public String gettextUrl(){
        return textUrl;
    }
    public void setTextUrl(String textUrl){
        this.textUrl = textUrl;
    }


    // 그룹 생성시 사용
    public GoodsInfo_list(String userId, String name, String price, String imgUrl, boolean isSoldOut,
                          String location, String mapUrl, String textUrl){
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.isSoldOut = isSoldOut;
        this.location = location;
        this.mapUrl = mapUrl;
        this.textUrl = textUrl;
    }
}
