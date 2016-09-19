package coach.panwar.com.coachregi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    DBHelper dbHelper;
    ProgressDialog loading;
    TextView name, fatmot,dobpob, age, blood, add, citydist,state, email,numbers, bat, bwl, bwlpro,weep;
    ImageView imageView;
    ImageLoader imageLoader;
    DisplayImageOptions options;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Config config = new Config();        config.setSelectionMin(1);
        config.setSelectionLimit(1);
        ImagePickerActivity.setConfig(config);
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config1.threadPriority(Thread.NORM_PRIORITY - 2);
        config1.denyCacheImageMultipleSizesInMemory();
        config1.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config1.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config1.tasksProcessingOrder(QueueProcessingType.LIFO);
        config1.writeDebugLogs();
        imageLoader= ImageLoader.getInstance();
        imageLoader.init(config1.build());



        imageView = (ImageView)findViewById(R.id.lodudp);
        name = (TextView) findViewById(R.id.name);
        fatmot = (TextView)findViewById(R.id.motfat);
        dobpob = (TextView)findViewById(R.id.dobpob);
        age = (TextView)findViewById(R.id.age);
        blood = (TextView)findViewById(R.id.blood);
        add = (TextView)findViewById(R.id.address);
        citydist = (TextView)findViewById(R.id.citydist);
        state = (TextView)findViewById(R.id.state);
        email = (TextView)findViewById(R.id.emailid);
        numbers =(TextView) findViewById(R.id.numbers);
        bat = (TextView)findViewById(R.id.bathand);
        bwl = (TextView)findViewById(R.id.bwlhand);
        bwlpro = (TextView)findViewById(R.id.bwlpro);
        weep =(TextView) findViewById(R.id.weep);
        dbHelper = new DBHelper(getApplicationContext());

        boolean ch = SetUpProfile();
        if(ch){

           loading.dismiss();

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

public boolean SetUpProfile(){
    loading = ProgressDialog.show(this,"Setting Up Your Profile...","Please wait...",false,false);
    Cursor cursor= dbHelper.Getprofiledata();
    while (cursor.isFirst()){

        String fname = cursor.getString(cursor.getColumnIndex("FNAME"));
        Log.d("profile",fname);
        String lname = cursor.getString(cursor.getColumnIndex("LNAME"));
        String fat = cursor.getString(cursor.getColumnIndex("FATNAME"));
        String mot = cursor.getString(cursor.getColumnIndex("MOTNAME"));
        String dob = cursor.getString(cursor.getColumnIndex("DOB"));
        String pob = cursor.getString(cursor.getColumnIndex("POB"));
        String addr = cursor.getString(cursor.getColumnIndex("ADDR"));
        String city = cursor.getString(cursor.getColumnIndex("CITY"));
        String dist = cursor.getString(cursor.getColumnIndex("DIST"));
        String sta = cursor.getString(cursor.getColumnIndex("STATE"));
        String mob = cursor.getString(cursor.getColumnIndex("MOB"));
        String tel = cursor.getString(cursor.getColumnIndex("TEL"));
        String emailid = cursor.getString(cursor.getColumnIndex("EMAIL"));
        Log.d("profile",emailid);
        String bld = cursor.getString(cursor.getColumnIndex("BLOODGRP"));
        String bathnd = cursor.getString(cursor.getColumnIndex("BAT"));
        String bwlhnd = cursor.getString(cursor.getColumnIndex("BOWL"));
        String bwlpros = cursor.getString(cursor.getColumnIndex("BOWL_TYPE"));
        String wck = cursor.getString(cursor.getColumnIndex("WK"));
        String image = cursor.getString(cursor.getColumnIndex("IMAGE"));


        name.setText(fname.toString()+" Last Name : "+lname);
        fatmot.setText("Father's Name: "+fat+ " Mother's Name :"+mot);
        dobpob.setText("DOB: "+dob+" POB: "+pob);
        blood.setText("Blood Group"+bld);
        add.setText(addr);
        citydist.setText("City: "+city+" District: "+dist);
        state.setText("State: "+sta);
        email.setText("Email Id: "+emailid);
        numbers.setText("Mobile: "+mob+" Tel: "+tel);
        bat.setText("Batting Hand: "+bathnd);
        bwl.setText("Bowling hand: "+bwlhnd);
        bwlpro.setText("Bowling Proficiency: "+bwlpros);
        weep.setText("Wicket Keeping: "+wck);
        getDateDifference(dob);
        imageLoader.displayImage(image,imageView);


        cursor.moveToNext();


    }




return  true;}


    public String getCurrentDate(){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return  df.format(Calendar.getInstance().getTime());
    }

    public void getDateDifference(String dob){

        String dateOfBirth = dob;
        String dateCurrent = getCurrentDate();

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateOfBirth);
            d2 = format.parse(dateCurrent);


            //in milliseconds

            long diff = d2.getTime() - d1.getTime();

            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
            int agee = diffDays/365;
            int thres = agee/4;

            int days=diffDays%365;
            days = days-thres;
            age.setText(agee+" Years & days "+days);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
