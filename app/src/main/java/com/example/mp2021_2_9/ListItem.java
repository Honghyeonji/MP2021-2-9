package com.example.mp2021_2_9;

public class ListItem {
    private String key;
    private String name;
    private boolean isSoldOut;

    public ListItem(String name, boolean isSoldOut, String key){
        this.name = name;
        this.isSoldOut = isSoldOut;
        this.key = key;
    }

    public String getKey() { return this.key; }

    public String getName()
    {
        return this.name;
    }

    public boolean getIsSoldOut()
    {
        return this.isSoldOut;
    }

    public void setKey(String key) { this.key = key; }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setIsSoldOut(boolean isSoldOut)
    {
        this.isSoldOut = isSoldOut;
    }
}
