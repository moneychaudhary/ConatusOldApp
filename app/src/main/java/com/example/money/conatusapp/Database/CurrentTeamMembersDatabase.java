package com.example.money.conatusapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.money.conatusapp.TeamMembers.currentTeam.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by #money on 10/5/2016.
 */

public class CurrentTeamMembersDatabase extends SQLiteOpenHelper {
    private final static String DATBASE_NAME = "currentteam.db";
    private final static String TABLE_NAME = "current_team_table";
    private final static String COl_NAME = "name";
    private final static String COL_ID = "id";
    private final static String COL_DOMAIN = "domain";
    private final static String COL_BRANCH = "branch";
    private final static String COL_YEAR = "year";
    private final static String COL_IMAGE = "image";

    public CurrentTeamMembersDatabase(Context context) {
        super(context, DATBASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "" + COl_NAME + " ," + COL_DOMAIN + " ," + COL_BRANCH + " ," + COL_YEAR + ", " + COL_IMAGE + " )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<Member> getData() {
        List<Member> memberList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select *  from " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(COl_NAME));
                String branch = cursor.getString(cursor.getColumnIndex(COL_BRANCH));
                String year = cursor.getString(cursor.getColumnIndex(COL_YEAR));
                String domain = cursor.getString(cursor.getColumnIndex(COL_DOMAIN));
                String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
                Member member = new Member(name, image, domain, year, branch);
                memberList.add(member);
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return memberList;
    }

    public void insertData(List<Member> memberList) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete  from " + TABLE_NAME);

        for (int i = 0; i < memberList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COl_NAME, memberList.get(i).getName());
            contentValues.put(COL_DOMAIN, memberList.get(i).getDomain());
            contentValues.put(COL_BRANCH, memberList.get(i).getBranch());
            contentValues.put(COL_IMAGE, memberList.get(i).getImage());
            contentValues.put(COL_YEAR, memberList.get(i).getYear());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        }
        sqLiteDatabase.close();
    }
}
