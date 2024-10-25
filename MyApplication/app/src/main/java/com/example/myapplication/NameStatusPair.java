package com.example.myapplication;

public class NameStatusPair {
    private String name;
    private String status;
    private String usage;
    private String pos;
    private String price;
    private String prio;
    public NameStatusPair(String name, String status, String usage, String pos, String price, String prio)
    {
        this.name = name;
        this.status = status;
        this.usage = usage;
        this.pos = pos;
        this.price = price;
        this.prio = prio;
    }

    public String getPrio()
    {
        return this.prio;
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
    public void set_name(String name)
    {
        this.name = name;
    }
    public void set_prio(String prio)
    {
        this.prio = prio;
    }
}

