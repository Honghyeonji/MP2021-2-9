package com.example.mp2021_2_9;

public class GoodsInfo_list {

    private int userId;
    private String name;
    private int price;
    private String imgUrl;
    private boolean isSoldOut;
    private String location;
    private String mapUrl;
    private String textUrl;

    public GoodsInfo_list() {}

    public int getUserId(){ return userId; }
    public void setUserId(int userId){ this.userId = userId;}
    public String getName(){
        return name;
    }
    public void setName(String name){ this.name = name; }
    public int getPrice(){ return price; }
    public void setPrice(int price){
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
    public GoodsInfo_list(int userId, String name, int price, String imgUrl, boolean isSoldout,
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
