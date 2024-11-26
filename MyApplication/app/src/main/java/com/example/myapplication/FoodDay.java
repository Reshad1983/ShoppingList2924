package com.example.myapplication;

public class FoodDay {
    String food;
    String day;
    int used_last_week;
    public FoodDay(String food_name, String week_day, int usage )
    {
        this.food = food_name;
        this.day = week_day;
        this.used_last_week = usage;
    }

    public String getFoodName()
    {
        return this.food;
    }
       public String getDay()
    {
        return this.day;
    }
   public int getUsage()
    {
        return this.used_last_week;
    }

    public void setFood(String myFood)
    {
        this.food = myFood;
    }
    public void setDay(String myDay)
    {
        this.day = myDay;
    }
    public void setUsage(int i)
    {
        this.used_last_week = i;
    }
}
