package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "items.db";
    private static final int DATABASE_VERSION = 11;
    // Table and Columns
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_FOOD = "food_recepies";
    private static final String TABLE_DAY_FOOD = "day_food";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_POS = "position";
    private static final String COLUMN_USAGE_COUNT = "usage_count";
    private static final String COLUMN_STATUS = "item_status";
    private static final String COLUMN_PRIORITY = "item_priority";
    private static final String COLUMN_DATE = "purchase_date";
    private static final String COLUMN_INTERVAL = "purchase_interval" ;
    private static final String COLUMN_DURATION = "item_duration" ;
    private static final String COLUMN_INGREDIENTS = "Ingrediences" ;
    private static final String COLUMN_DAY = "day_of_week";
    private static final String COLUMN_FOOD = "food_name";
    private static final String DAY_TO_COOK = "food_day";
    private static final String USED_LAST_WEEK = "used_last_week";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_DAY_FOOD + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DAY + " TEXT NOT NULL, " +
                COLUMN_FOOD + " TEXT)";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        if(oldVersion < newVersion ) {

            String CREATE_ITEMS_TABLE = "ALTER TABLE " + TABLE_FOOD + " ADD " +
                    USED_LAST_WEEK + " INTEGER ";
            db.execSQL(CREATE_ITEMS_TABLE);

        }
        else {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }

        // Create table again
    }    // Add a new item
    public void add_food(String name, String ingredients, String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_INGREDIENTS, ingredients);// Initial usage count is 0
        values.put(DAY_TO_COOK, day);
        db.insert(TABLE_FOOD, null, values);
        db.close();
    }
    // Add a new item

    public void add_item(String name, int pos, int duration) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_POS, pos);
        values.put(COLUMN_STATUS, 0);
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_USAGE_COUNT, 0);
        values.put(COLUMN_DATE, date);// Initial usage count is 0
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }

    // Remove an item by name
    public void removeItem(String name) {
        String [] item_to_delete = name.split(System.lineSeparator(), -2);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ITEMS + " WHERE " + COLUMN_NAME + " = ?" ;
        db.execSQL(query, new String []{item_to_delete[0]} );
        db.execSQL(query);
        db.close();
    }

    // Increment the usage count of an item by name
    public void increment_usage_count(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_USAGE_COUNT + " = " + COLUMN_USAGE_COUNT + " + 1 WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{name});
        db.close();
    }

    public void reset_status()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_STATUS + " = \"0\"" ;
        db.execSQL(query);
        db.close();
    }


    public void reset_usage(String name, String usage)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_USAGE_COUNT + " = \"0\"  WHERE " +COLUMN_NAME + " = ?" ;
        db.execSQL(query, new String []{name} );
        db.execSQL(query);
        db.close();
    }
    public void update_status(int status, String name)
    {
        String string_status = ""+status;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_STATUS + " = " + string_status + " WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{name});
        db.close();
    }
    public void update_date(String date , String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_DATE + " = \"" + date + "\" WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{name});
        db.close();
    }
    public void update_position(String pos, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_POS + " = " + pos + " WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{name});
        db.close();
    }
    public void update_item_name(String new_name, String old_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_NAME + " = \"" + new_name + "\" WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{old_name});
        db.close();

    }  // Get items sorted by most used (descending order of usage_count)
    public List<NameStatusPair> getItemsSortedByUsage() throws ParseException {
        List<NameStatusPair> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, new String[]{COLUMN_NAME, COLUMN_USAGE_COUNT, COLUMN_STATUS, COLUMN_POS, COLUMN_DURATION, COLUMN_PRIORITY, COLUMN_INTERVAL, COLUMN_DATE},
                null, null, null, null, COLUMN_PRIORITY + " ASC, " + COLUMN_STATUS + " ASC, "+ COLUMN_USAGE_COUNT + " ASC");//, "+COLUMN_USAGE_COUNT+" DESC");

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(0);
                String itemUsage = cursor.getString(1);
                String item_status = cursor.getString(2);
                String item_pos = cursor.getString(3);
                String item_duration = cursor.getString(4);
                String item_prio = cursor.getString((5));
                String interval = cursor.getString((6));
                String date_string = cursor.getString((7));
                if(date_string != null && date_string.contains("d"))
                {
                    date_string = null;
                }
                SimpleDateFormat sfd = new SimpleDateFormat("yy-MM-dd");
                /* Date date;
                if(date_string != null && (date_string.length() > 4))
                {
                    date = sfd.parse(date_string);
                }
                else
                {

                    date = null;
                }*/
                itemList.add(new NameStatusPair(itemName, item_status, itemUsage, item_pos, item_duration, item_prio, interval, date_string));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }
    public List<NameStatusPair> getItemsSortedByPosition() throws ParseException {
        List<NameStatusPair> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, new String[]{COLUMN_NAME, COLUMN_USAGE_COUNT, COLUMN_STATUS, COLUMN_POS, COLUMN_DURATION, COLUMN_PRIORITY, COLUMN_INTERVAL, COLUMN_DATE},
                null, null, null, null, COLUMN_PRIORITY + " ASC, " + COLUMN_STATUS + " ASC, "+ COLUMN_POS + " ASC");//, "+COLUMN_USAGE_COUNT+" DESC");

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(0);
                String itemUsage = cursor.getString(1);
                String item_status = cursor.getString(2);
                String item_pos = cursor.getString(3);
                String item_duration = cursor.getString(4);
                String item_prio = cursor.getString((5));
                String interval = cursor.getString((6));
                String date_string = cursor.getString((7));
               /*if(date_string != null && date_string.contains("d"))
                {
                    date_string = null;
                }
                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
                Date date;
                if(date_string != null && (date_string.length() > 4))
                {
                    date = sfd.parse(date_string);
                }
                else
                {

                    date = null;
                }*/
                itemList.add(new NameStatusPair(itemName, item_status, itemUsage, item_pos, item_duration, item_prio, interval, date_string));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }

    public void set_priority(String name, int i)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_PRIORITY + " = \"" + i + "\" WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{name});
        db.close();
    }

    public void update_interval(int i, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_INTERVAL + " = " + i + " WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{name});
        db.close();
    }

    public void update_all_date(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_DATE + " = \"" + date + "\"";
        db.execSQL(query);
        db.close();
    }

    public int get_number_of_foods() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_FOOD;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int sum = cursor.getInt(0);
        cursor.close();
        return sum;

    }
    public List<FoodDay> get_food_names() {
        List<FoodDay> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOOD, new String[]{COLUMN_NAME, DAY_TO_COOK, USED_LAST_WEEK},
                null, null, null, null, COLUMN_NAME + " DESC");//, "+COLUMN_USAGE_COUNT+" DESC");

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(0);
                String item_day = cursor.getString(1);
                int item_usage = cursor.getString(2) == null? 0 : Integer.parseInt(cursor.getString(2));
                itemList.add(new FoodDay(itemName, item_day, item_usage));
            } while (cursor.moveToNext());
            cursor.close();

        }
        return itemList;
    }

    public void set_food_to_day(String food_name, String day) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, day);
        values.put(COLUMN_FOOD, food_name);// Initial usage count is 0
        db.insert(TABLE_DAY_FOOD, null, values);
        update_last_week_usage(food_name, 1);
        db.close();
    }

    public void update_last_week_usage(String foodName, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_FOOD + " SET " + USED_LAST_WEEK + " = \"" + status + "\" WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{foodName});
        db.close();
    }

    public List<String> get_days_food(String pos)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String  food_name = "";
        List<String> food_of_day = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_DAY_FOOD + " WHERE " + COLUMN_DAY + " = \"" + pos + "\"" ;
        int i = 0;
        Cursor cursor = db.rawQuery(query, null);// new String[]{pos});
        if(cursor.moveToFirst())
        {
            do
            {
                food_name = cursor.getString(2);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        query = "SELECT * FROM " + TABLE_FOOD + " WHERE " + COLUMN_NAME + " = ?";
        cursor = db.rawQuery(query, new String[]{food_name});
        if(cursor.moveToFirst()) {
            do {
                food_name = cursor.getString(1);
                String ingredients = cursor.getString(2);
                food_of_day.add(food_name);
                food_of_day.add(ingredients);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return (food_of_day.size()==0) ? null : food_of_day;


    }

    public void remove_from_food_table(CharSequence text) {
        String food_name = text.toString();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_FOOD + " WHERE "+ COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{food_name});
        db.close();
    }

    public String get_ingred_for_food(String string) {
        SQLiteDatabase db = this.getReadableDatabase();
        String food_ingred = "";
        String query = "Select " + COLUMN_INGREDIENTS + " FROM " + TABLE_FOOD + " Where " + COLUMN_NAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{string});
        if(cursor.moveToFirst())
        {
            do {
                food_ingred = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        return food_ingred;
    }

    public void add_ingredients_to_food(String string, String day) {

        String food_name = "";
        if(string.contains(":"))
        {
            food_name = string.split(":")[1].trim();
        }
        else {
            food_name = string;
        }
        String ingredients = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENTS + " FROM "  + TABLE_FOOD + " WHERE " + COLUMN_NAME + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{food_name});
        if(cursor.moveToFirst())
        {
            do {
                ingredients = cursor.getString(0);
            }while(cursor.moveToNext());
        }

        StringBuilder ingredients_to_add = new StringBuilder();
        cursor.close();
        query = "SELECT " + COLUMN_NAME + " FROM "  + TABLE_ITEMS + " WHERE " + COLUMN_STATUS + " = \"1\"";
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst())
        {
            do {
                ingredients_to_add.append("-").append(cursor.getString(0));// = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        ingredients_to_add.append("-").append(ingredients);
        db = getWritableDatabase();
        query = "UPDATE "+ TABLE_FOOD + " SET " + COLUMN_INGREDIENTS + " = \"" + ingredients_to_add + "\" , " + DAY_TO_COOK + " = \"" + day +"\""+ " WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{food_name});
        db.close();
    }

    public void update_food_name(String food_name, String old_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_FOOD + " SET " +COLUMN_NAME + " = \"" + food_name + " \"" + " WHERE " + COLUMN_NAME + " =?";
        db.execSQL(query, new String[]{old_name});
        db.close();
    }

}
