package coach.panwar.com.coachregi;

/**
 * Created by Panwar on 10-Mar-16.
 */
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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