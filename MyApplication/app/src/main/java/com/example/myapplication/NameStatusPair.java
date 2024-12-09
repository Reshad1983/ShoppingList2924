package com.example.myapplication;

import java.util.Date;

public class NameStatusPair {
    private String name;
    private String status;
    private String usage;
    private String pos;
    private String duration;
    private String prio;
    private String interval;
    private String date;

    public NameStatusPair(String name, String status, String usage, String pos, String duration, String prio)
    {
        this.name = name;
        this.status = status;
        this.usage = usage;
        this.pos = pos;
        this.duration = duration;
        this.prio = prio;
    }

    public NameStatusPair(String name, String status, String usage, String pos, String duration, String prio, String method_interval, String date)
    {
        this.name = name;
        this.status = status;
        this.usage = usage;
        this.pos = pos;
        this.duration = duration;
        this.prio = prio;
        this.interval = method_interval;
        this.date = date;
    }

    public String getPrio()
    {
        return this.prio;
    }
    public String getDuration()
    {
        return this.duration;
    }
    public String getName()
    {
        return this.name;
    }
    public String get_date(){return this.date;}
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
    public String getDate(){return this.date;}
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
    public void setDuration(String duration)
    {
        this.duration = duration;
    }
    public void set_name(String name)
    {
        this.name = name;
    }
    public void set_interval(String interval){this.interval = interval;}
    public void set_prio(String prio){this.prio = prio;}
    public void set_date(String mDate){this.date = mDate;}
}

