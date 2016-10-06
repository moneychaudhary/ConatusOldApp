package com.example.money.conatusapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.money.conatusapp.Gallery.OneImage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by #money on 10/5/2016.
 */

public class GalleryDatabase extends SQLiteOpenHelper {
    private final static String DATBASE_NAME = "gallery.db";
    private final static String TABLE_NAME = "gallery_table";
    private final static String COL_IMAGE = "image";

    public GalleryDatabase(Context context) {
        super(context, DATBASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "( " + COL_IMAGE + " )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<OneImage> getData() {
        List<OneImage> oneImageList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select *  from " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                OneImage oneImage = new OneImage(image);
                oneImageList.add(oneImage);
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return oneImageList;
    }

    public void insertData(List<OneImage> oneImageList) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete  from " + TABLE_NAME);

        for (int i = 0; i < oneImageList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_IMAGE, oneImageList.get(i).getImage());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        }
        sqLiteDatabase.close();
    }
}

