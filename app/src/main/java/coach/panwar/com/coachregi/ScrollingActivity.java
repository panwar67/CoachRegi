package coach.panwar.com.coachregi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ScrollingActivity extends AppCompatActivity {

    Button mypro, calender, events,gallery, edit ;
    EditText ageid;
    String DOWN_URL = "http://www.whydoweplay.com/CoachData/getprofile.php";
    DBHelper dbHelper;
    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());
        mypro = (Button)findViewById(R.id.button6);
        calender = (Button)findViewById(R.id.button7);
        events = (Button)findViewById(R.id.button8);
        edit = (Button)findViewById(R.id.button9);
        gallery = (Button)findViewById(R.id.button10);




        mypro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(ScrollingActivity.this,profile.class);


                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                  in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition (0, 0);


                startActivity(in);
                }
        });
  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.logoutinfo){
            sessionManager.logoutUser();


        }

        return super.onOptionsItemSelected(item);
    }


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
            int age = diffDays/365;
            int days=diffDays%365;
            ageid.setText((int) age);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean setUpProfile(final String uid){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Your Profile...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("Registration");
                                JSONObject details = data.getJSONObject(0);
                                String uid = details.getString("UID");
                                String fname = details.getString("FNAME");
                                String lname = details.getString("LNAME");
                                String fatname = details.getString("FATNAME");
                                String motname = details.getString("MOTNAME");
                                String dob = details.getString("DOB");
                                String pob = details.getString("POB");
                                String add = details.getString("ADDR");
                                String city = details.getString("CITY");
                                String dist = details.getString("DISTRICT");
                                String state = details.getString("STATE");
                                String email = details.getString("EMAIL");
                                String mob = details.getString("MOB");
                                String tel = details.getString("TEL");
                                String bat = details.getString("BAT_TYPE");
                                String bwl = details.getString("BWL_TYPE");
                                String bwlatt = details.getString("BWL_ATT");
                                String wck = details.getString("WK");
                                String blood = details.getString("BLDGRP");
                                String image = details.getString("IMAGE");
                                Log.d("profile data", s);
                                dbHelper.InsertLeagueTable(uid,fname,lname,fatname,motname,dob,pob,add,city,dist,state,mob,tel,email,bat,bwl,bwlatt,wck,blood,image);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }





                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(ScrollingActivity.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("uid",uid);

                Log.d("uid", uid);


                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);
        return false;
    }









}
