package com.example.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NameStatusPair {
    private String name;
    private String status;
    private String usage;
    private String pos;
    private String price;
    private String prio;
    private String interval;
    private Date date;

    public NameStatusPair(String name, String status, String usage, String pos, String price, String prio)
    {
        this.name = name;
        this.status = status;
        this.usage = usage;
        this.pos = pos;
        this.price = price;
        this.prio = prio;
    }

    public NameStatusPair(String name, String status, String usage, String pos, String price, String prio, String method_interval, Date date)
    {
        this.name = name;
        this.status = status;
        this.usage = usage;
        this.pos = pos;
        this.price = price;
        this.prio = prio;
        this.interval = method_interval;
        this.date = date;
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
    public Date get_date(){return this.date;}
    public String get_interval(){return this.interval;}
    public String getStatus(){return this.status; }
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
    public void set_interval(String interval){this.interval = interval;}
    public void set_prio(String prio){this.prio = prio;}
}

