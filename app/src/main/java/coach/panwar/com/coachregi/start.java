package coach.panwar.com.coachregi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class start extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */

    private View mContentView;
    String DOWN_URL = "http://www.whydoweplay.com/CoachData/getprofile.php";
    String DOWN_URL1 = "http://www.whydoweplay.com/CoachData/getnews.php";
    String DOWN_URL2="http://www.whydoweplay.com/CoachData/getquery.php";
    String DOWN_URL3="http://www.whydoweplay.com/CoachData/getTeams.php";
    String DOWN_URL4="http://www.whydoweplay.com/CoachData/getgallery.php";
    String DOWN_URL5="http://www.whydoweplay.com/CoachData/getsch.php";


    DBHelper dbHelper;
    private View mControlsView;

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);




        sessionManager = new SessionManager(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());

        // Set up the user interaction to manually show or hide the system UI.


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

        if(sessionManager.isLoggedIn()&&sessionManager.getUserDetails().get("uid")!=null)
        {

           HashMap<String,String> params =  sessionManager.getUserDetails();
            String uid = params.get("uid");
            setUpProfile(uid);
            setUpNews();
            setUpQuery(uid);
            setUpTeams();
            SetSch();
            setUpGallery();

  }
        else
        {

            startActivity(new Intent(start.this,LoginActivity.class));
            finish();

        }


    }




    public boolean setUpProfile(final String uid){

        //Showing the progress dialog
      //  final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Your Profile...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {




//                        loading.dismiss();
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
                                //startActivity(new Intent(start.this,ScrollingActivity.class));



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();

                        //Showing toast
                        Toast.makeText(start.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
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




    public boolean setUpNews(){

        //Showing the progress dialog
        //final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Your Profile...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        dbHelper.InitNews();
                        if (s!=null)
                        {


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("NEWS");
                                for(int i=0;i<data.length();i++) {
                                    JSONObject details = data.getJSONObject(i);

                                    Log.d("news data", s);
                                    dbHelper.InsertNews(details.getString("UID"),details.getString("TITLE"),details.getString("DATE"),details.getString("DES"),details.getString("PATH"));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }





                        //Disimissing the progress dialog
                        //loading.dismiss();
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                      //  loading.dismiss();

                        //Showing toast
                        Toast.makeText(start.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();



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



    public boolean setUpQuery(final String uid){

        //Showing the progress dialog
      //  final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Your Queries...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        dbHelper.InitQuery();
                        if (s!=null)
                        {


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("Query");
                                for(int i=0;i<data.length();i++) {
                                    JSONObject details = data.getJSONObject(i);

                                    Log.d("query data", s);
                                    dbHelper.InsertQuery(details.getString("REQUID"),details.getString("USERUID"),details.getString("QUERY"),details.getString("REPLY"),details.getString("REQDATE"));
                                    //dbHelper.InsertNews(details.getString("UID"),details.getString("TITLE"),details.getString("DATE"),details.getString("DESC"));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }





                        //Disimissing the progress dialog
                       // loading.dismiss();
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                       // loading.dismiss();

                        //Showing toast
                        Toast.makeText(start.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("uid",uid);



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



    public boolean setUpTeams(){

        //Showing the progress dialog
       // final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Your Teams...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        dbHelper.InitEvents();
                        if (s!=null)
                        {


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("TEAMS");
                                for(int i=0;i<data.length();i++) {
                                    JSONObject details = data.getJSONObject(i);

                                    Log.d("news data", s);
                                    dbHelper.InsertEvents(details.getString("UID"),details.getString("TEAM_TITLE"),details.getString("TEAM_DATE"),details.getString("DES"), details.getString("META"));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }





                        //Disimissing the progress dialog
                       // loading.dismiss();
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
//                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(start.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();



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

    public boolean setUpGallery(){

        //Showing the progress dialog
        // final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Your Teams...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        dbHelper.InitGallery();
                        if (s!=null)
                        {


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("Gallery");
                                for(int i=0;i<data.length();i++)
                                {
                                    JSONObject details = data.getJSONObject(i);

                                    Log.d("gallery data", s);
                                    dbHelper.InsertGallery(details.getString("PATH"),details.getString("CAPTION"),details.getString("DATE"),details.getString("UID"));
                                    //dbHelper.InsertEvents(details.getString("UID"),details.getString("TEAM_TITLE"),details.getString("TEAM_DATE"),details.getString("DES"));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(new Intent(start.this,OptionsDrawer.class));
                            finish();



                        }





                        //Disimissing the progress dialog
                        // loading.dismiss();
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
//                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(start.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();



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



    public boolean SetSch()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        dbHelper.InitGallery();
                        if (s!=null)
                        {


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("SCH");
                                for(int i=0;i<data.length();i++)
                                {
                                    JSONObject details = data.getJSONObject(i);

                                    Log.d("sch data", s);
                                    dbHelper.InsertSch(details.getString("PATH"),details.getString("UID"));
                                    //dbHelper.InsertGallery(details.getString("PATH"),details.getString("CAPTION"),details.getString("DATE"),details.getString("UID"));
                                    //dbHelper.InsertEvents(details.getString("UID"),details.getString("TEAM_TITLE"),details.getString("TEAM_DATE"),details.getString("DES"));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }





                        //Disimissing the progress dialog
                        // loading.dismiss();
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
//                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(start.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();



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
