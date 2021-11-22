package com.example.mp2021_2_9;

public class ListItem {
    private String name;
    private boolean isSoldOut;

    public ListItem(String name, boolean isSoldOut){
        this.name = name;
        this.isSoldOut = isSoldOut;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean getIsSoldOut()
    {
        return this.isSoldOut;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setIsSoldOut(boolean isSoldOut)
    {
        this.isSoldOut = isSoldOut;
    }
}
