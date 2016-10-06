package com.example.money.conatusapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.money.conatusapp.Home.Post;

import java.util.ArrayList;
import java.util.List;


public class HomeDatabase extends SQLiteOpenHelper {
    private final static String DATBASE_NAME = "home.db";
    private final static String TABLE_NAME = "home_table";
    private final static String COl_TITLE = "title";
    private final static String COL_DATE = "date";
    private final static String COL_TIME = "time";
    private final static String COL_SUBHEAD = "subhead";
    private final static String COL_DESC = "desc";
    private final static String COL_IMAGE = "image";

    public HomeDatabase(Context context) {
        super(context, DATBASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COL_DATE + " ," + COl_TITLE + " ," + COL_TIME + " ," + COL_SUBHEAD + " ," + COL_DESC + ", " + COL_IMAGE + " )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<Post> getData() {
        List<Post> postList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select *  from " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndex(COl_TITLE));
                String subhead = cursor.getString(cursor.getColumnIndex(COL_SUBHEAD));
                String desc = cursor.getString(cursor.getColumnIndex(COL_DESC));
                String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                String date = cursor.getString(cursor.getColumnIndex(COL_DATE));
                Post post = new Post(title, desc, image, subhead, time, date);
                postList.add(post);
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return postList;
    }

    public void insertData(List<Post> postList) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete  from " + TABLE_NAME);

        for (int i = 0; i < postList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COl_TITLE, postList.get(i).getTitle());
            contentValues.put(COL_TIME, postList.get(i).getTime());
            contentValues.put(COL_SUBHEAD, postList.get(i).getSubhead());
            contentValues.put(COL_IMAGE, postList.get(i).getImage());
            contentValues.put(COL_DESC, postList.get(i).getDesc());
            contentValues.put(COL_DATE, postList.get(i).getDate());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        }
        sqLiteDatabase.close();
    }
}

