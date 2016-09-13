package coach.panwar.com.coachregi;

/**
 * Created by Panwar on 10-Mar-16.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nostra13.universalimageloader.utils.L;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "coach.db";



    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE PROFILE (UID text, FNAME text, LNAME text, DOB text, POB text, FATNAME text, MOTNAME text, ADDR text, CITY text, DIST text, STATE text, MOB text, EMAIL text ,TEL text, BLOODGRP text, BAT text, BOWL text, BOWL_TYPE text, WK text, IMAGE text)");
        db.execSQL("CREATE TABLE "+NewsStruct.Table_Name+" ( "+NewsStruct.UID+" text, "+NewsStruct.TITLE+" text, "+NewsStruct.DESC+" text, "+NewsStruct.DATE+" text)");
        db.execSQL("CREATE TABLE "+EventStruct.table_name+" ( "+EventStruct.eventuid+" text, "+EventStruct.eventtitle+" text, "+EventStruct.eventdes+" text, "+EventStruct.eventdate+" text)");
        db.execSQL("CREATE TABLE "+QueryStruct.table_name+" ( "+QueryStruct.requid+" text, "+QueryStruct.query+" text, "+QueryStruct.reply+" text, "+QueryStruct.reqdate+" text, "+QueryStruct.useruid+" text)");



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS PROFILE");
        onCreate(db);
    }




    public boolean InitLogin()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM PROFILE");
        Log.d("sql","Deleted");




        return true;
    }

    public boolean InitNews()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("DELETE FROM "+NewsStruct.Table_Name);
        Log.d("deleted news","deleted");
        return  true;
    }

    public boolean InitQuery()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+QueryStruct.table_name);
        Log.d("events","deleted");
        return true;

    }
    public boolean InitEvents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+EventStruct.table_name);
        Log.d("events","deleted");
        return true;
    }





    public boolean InsertLeagueTable  (String uid,String fname, String lname, String fat,String mot,String dob, String pob, String add, String city, String dist, String state, String mob, String tel, String email, String bathand, String bwlhand, String bwlpro, String wck, String blood, String image)
    {
        InitLogin();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("UID", uid);

        contentValues.put("FNAME", fname);
        contentValues.put("LNAME", lname);
        contentValues.put("FATNAME", fat);

        contentValues.put("MOTNAME", mot);
        contentValues.put("DOB", dob);

        contentValues.put("POB", pob);
        contentValues.put("ADDR", add);
        contentValues.put("CITY", city);
        contentValues.put("DIST", dist);


        contentValues.put("STATE", state);

        contentValues.put("MOB", mob);

        contentValues.put("TEL", tel);

        contentValues.put("EMAIL", email);


        contentValues.put("BAT", bathand);

        contentValues.put("BOWL", bwlhand);

        contentValues.put("BOWL_TYPE", bwlpro);

        contentValues.put("WK", wck);

        contentValues.put("IMAGE", image );

        contentValues.put("BLOODGRP", blood);
        long row = db.insertWithOnConflict("PROFILE", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("PROFILE", String.valueOf(row)+"inserted");


        return true;
    }


    public boolean InsertNews(String uid, String title, String date, String desc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NewsStruct.UID,uid);
        contentValues.put(NewsStruct.TITLE,title);
        contentValues.put(NewsStruct.DATE,date);
        contentValues.put(NewsStruct.DESC,desc);
        long row = db.insertWithOnConflict(NewsStruct.Table_Name,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("news", String.valueOf(row));

        return true;
    }



    public boolean InsertQuery(String uid, String useruid, String query, String reply, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QueryStruct.requid,uid);
        contentValues.put(QueryStruct.useruid,useruid);
        contentValues.put(QueryStruct.query,query);
        contentValues.put(QueryStruct.reply,reply);
        contentValues.put(QueryStruct.reqdate,date);
        long row = db.insertWithOnConflict(QueryStruct.table_name,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("QUERY", String.valueOf(row));

        return true;
    }

    public boolean InsertEvents(String uid, String title, String date, String desc)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues   contentValues   =   new ContentValues();
        contentValues.put(EventStruct.eventuid,uid);
        contentValues.put(EventStruct.eventtitle,title);
        contentValues.put(EventStruct.eventdate,date);
        contentValues.put(EventStruct.eventdes,desc);
        long row = db.insertWithOnConflict(EventStruct.table_name,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("Events",String.valueOf(row));
        return true;

    }


    public ArrayList<HashMap<String,String>> GetEventsMap()
    {
        SQLiteDatabase db  =    this.getReadableDatabase();
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        Cursor res = db.rawQuery("select * from "+NewsStruct.Table_Name, null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("title",res.getString(res.getColumnIndex(EventStruct.eventtitle)));
            map.put("data",res.getString(res.getColumnIndex(EventStruct.eventdate)));
            map.put("uid",res.getString(res.getColumnIndex(EventStruct.eventuid)));
            map.put("desc",res.getString(res.getColumnIndex(EventStruct.eventdes)));
            data.add(map);
            res.moveToNext();
        }
        return data;
    }

    public ArrayList<HashMap<String,String>> GetNewsMap()
    {
        SQLiteDatabase db  =    this.getReadableDatabase();
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        Cursor res = db.rawQuery("select * from "+NewsStruct.Table_Name, null);
        res.moveToFirst();
        while (!res.isAfterLast())
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("uid",res.getString(res.getColumnIndex(NewsStruct.UID)));
            map.put("title",res.getString(res.getColumnIndex(NewsStruct.TITLE)));
            map.put("desc",res.getString(res.getColumnIndex(NewsStruct.DESC)));
            map.put("date",res.getString(res.getColumnIndex(NewsStruct.DATE)));
            data.add(map);
            res.moveToNext();

        }

        return data;
    }






    public String getimageData() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from PROFILE ", null);

        res.moveToFirst();
        String pass = null;
        while (res.isAfterLast() == false) {
            pass = res.getString(res.getColumnIndex("IMAGE"));


            Log.d("getting passwords", res.getString(res.getColumnIndex("PASS")));
            res.moveToNext();
        }

        return pass;
    }



    public Cursor Getprofiledata(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from PROFILE", null);

        res.moveToFirst();
//        cursor.moveToFirst();
        return  res;
    }


}