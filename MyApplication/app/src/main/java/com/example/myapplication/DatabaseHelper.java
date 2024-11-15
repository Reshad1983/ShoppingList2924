package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "items.db";
    private static final int DATABASE_VERSION = 5;
    // Table and Columns
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_POS = "position";
    private static final String COLUMN_USAGE_COUNT = "usage_count";
    private static final String COLUMN_STATUS = "item_status";
    private static final String COLUMN_PRICE = "item_price";
    private static final String COLUMN_PRIORITY = "item_priority";
    private static final String COLUMN_DATE = "purchase_date";
    private static final String COLUMN_INTERVAL = "purchase_interval" ;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_POS + " INTEGER DEFAULT 0,"+
                COLUMN_STATUS + " INTEGER DEFAULT 0,"+
                COLUMN_USAGE_COUNT + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        if(oldVersion < newVersion )
        {

            String query = "ALTER TABLE " + TABLE_ITEMS + " ADD " + COLUMN_INTERVAL + " INTEGER DEFAULT 7 ";
            db.execSQL(query);
            query = "ALTER TABLE " + TABLE_ITEMS + " ADD " + COLUMN_DATE + " TEXT ";
            db.execSQL(query);
        }
        else {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }

        // Create table again
    }
    // Add a new item
    public void addItem(String name, int pos) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_POS, pos);
        values.put(COLUMN_STATUS, 0);
        values.put(COLUMN_USAGE_COUNT, 0);  // Initial usage count is 0
        values.put(COLUMN_DATE, date);
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }
    // Add a new item
    public void add_item_price(String name, int pos, int price) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_POS, pos);
        values.put(COLUMN_STATUS, 0);
        values.put(COLUMN_PRICE, price);
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
    public void incrementUsageCount(String name) {
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


    public void reset_usage()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_USAGE_COUNT + " = \"0\"" ;
        db.execSQL(query);
        db.close();
    }
    public void updatePrice(int price, String name)
    {
        String string_price = ""+price;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ITEMS + " SET " + COLUMN_PRICE + " = " + string_price + " WHERE " + COLUMN_NAME + " = ?";
        db.execSQL(query, new String[]{name});
        db.close();
    }
    public void updateStatus(int status, String name)
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
    public void updatePos(String pos, String name)
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
        Cursor cursor = db.query(TABLE_ITEMS, new String[]{COLUMN_NAME, COLUMN_USAGE_COUNT, COLUMN_STATUS, COLUMN_POS, COLUMN_PRICE, COLUMN_PRIORITY, COLUMN_INTERVAL, COLUMN_DATE},
                null, null, null, null, COLUMN_PRIORITY + " DESC, " + COLUMN_STATUS + " DESC, "+ COLUMN_USAGE_COUNT + " DESC");//, "+COLUMN_USAGE_COUNT+" DESC");

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(0);
                String itemUsage = cursor.getString(1);
                String item_status = cursor.getString(2);
                String item_pos = cursor.getString(3);
                String item_price = cursor.getString(4);
                String item_prio = cursor.getString((5));
                String interval = cursor.getString((6));
                String date_string = cursor.getString((7));
                if(date_string != null && date_string.contains("d"))
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
                }
                itemList.add(new NameStatusPair(itemName, item_status, itemUsage, item_pos, item_price, item_prio, interval, date));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }
    public List<NameStatusPair> getItemsSortedByPosition() throws ParseException {
        List<NameStatusPair> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, new String[]{COLUMN_NAME, COLUMN_USAGE_COUNT, COLUMN_STATUS, COLUMN_POS, COLUMN_PRICE, COLUMN_PRIORITY, COLUMN_INTERVAL, COLUMN_DATE},
                null, null, null, null, COLUMN_PRIORITY + " DESC, " + COLUMN_STATUS + " DESC, "+ COLUMN_POS + " DESC");//, "+COLUMN_USAGE_COUNT+" DESC");

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(0);
                String itemUsage = cursor.getString(1);
                String item_status = cursor.getString(2);
                String item_pos = cursor.getString(3);
                String item_price = cursor.getString(4);
                String item_prio = cursor.getString((5));
                String interval = cursor.getString((6));
                String date_string = cursor.getString((7));
                if(date_string != null && date_string.contains("d"))
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
                }
                itemList.add(new NameStatusPair(itemName, item_status, itemUsage, item_pos, item_price, item_prio, interval, date));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }
    // Get items sorted by most used (descending order of usage_count)
    public Cursor search (String searchString) {
        SQLiteDatabase mReadableDB = this.getReadableDatabase();
        String[] columns = new String[]{COLUMN_NAME};
        searchString = "%" + searchString + "%";
        String where = COLUMN_NAME + " LIKE ?";
        String[]whereArgs = new String[]{searchString};

        Cursor cursor = null;

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.query(TABLE_ITEMS, columns, where, whereArgs, null, null, null);
        } catch (Exception e) {
            String test = "Here";
        }

        String test = cursor.getString(0);
        return cursor;
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
}
