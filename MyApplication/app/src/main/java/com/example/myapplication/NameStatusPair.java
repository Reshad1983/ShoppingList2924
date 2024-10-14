package com.example.myapplication;

public class NameStatusPair {
    private String name;
    private String status;
    private String usage;
    private String pos;
    private String price;
    public NameStatusPair(String name, String status, String usage, String pos, String price)
    {
        this.name = name;
        this.status = status;
        this.usage = usage;
        this.pos = pos;
        this.price = price;
    }

    public String getPrice()
    {
        return this.price;
    }
    public String getName()
    {
        return this.name;
    }

    public String getStatus()
    {
        return this.status;

    }
    public String getUsage()
    {
        return this.usage;
    }
    public String getPos()
    {
        return this.pos;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public void setPos(String pos)
    {
        this.pos = pos;
    }
    public void setUsage(String usage)
    {
        this.usage = usage;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }
}

