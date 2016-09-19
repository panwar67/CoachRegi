package coach.panwar.com.coachregi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import java.util.Locale;
import java.util.Map;

public class RegistrationFinal extends AppCompatActivity {

    ImageView dp;
    //UploadImage uploadImage;
    String  UPLOAD_URL="http://www.whydoweplay.com/CoachData/regusr.php";
    String UPLOAD_URL1 = "http://www.whydoweplay.com/CoachData/signup.php";
    String DOWN_URL = "http://www.whydoweplay.com/CoachData/getprofile.php";

    SessionManager sessionManager;
    EditText datedit, firstname, middlename, lastname,pob,fatname,motname,add1,city,dist,state,mob,email, tel, pass, conpass;
    Calendar myCalendar = Calendar.getInstance();
    Button register;
    TextView ageid, uidfield;
    Spinner bloodgrp, bat,bwl,bwlpro,wk;
    ImageLoader imageLoader;
    HashMap<String,String> Keyvalue=new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_final);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Init();
       // email.setText(user);
        //uidfield.setText(uid);
        String uid = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        uidfield.setText(uid);


        sessionManager = new SessionManager(getApplicationContext());
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
        final Intent intent = new Intent(this, ImagePickerActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(intent, 67);

                }
            });
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        datedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(RegistrationFinal.this, datePickerDialog, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });

    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GetFieldsData();

        }
    });





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration_final, menu);
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

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == 67 && resuleCode == Activity.RESULT_OK ) {


            ArrayList<Uri> image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

            int size = image_uris.size();
            Uri img = image_uris.get(0);


            String urlimg = "file://"+img.getPath();
            imageLoader.displayImage( urlimg, dp);





        }
    }


    public void Init(){
        register = (Button)findViewById(R.id.button3);
        datedit = (EditText)findViewById(R.id.editText18);
        dp =(ImageView)findViewById(R.id.imageView1);
        firstname = (EditText)findViewById(R.id.editText14);
        middlename = (EditText)findViewById(R.id.editText2);
        lastname = (EditText)findViewById(R.id.editText17);
        pob=(EditText)findViewById(R.id.editText19);
        fatname = (EditText)findViewById(R.id.editText20);
        motname = (EditText)findViewById(R.id.editText21);
        add1 = (EditText)findViewById(R.id.editText22);
        ageid = (TextView)findViewById(R.id.textView21);
        city = (EditText)findViewById(R.id.editText23);
        dist = (EditText)findViewById(R.id.editText24);
        state = (EditText)findViewById(R.id.editText25);
        mob = (EditText)findViewById(R.id.editText26);
        tel = (EditText)findViewById(R.id.editText27);
        email = (EditText)findViewById(R.id.editText28);
        bloodgrp = (Spinner) findViewById(R.id.bld);
        bat = (Spinner) findViewById(R.id.bat);
        bwl = (Spinner) findViewById(R.id.bwl);
        bwlpro = (Spinner) findViewById(R.id.bwlpro);
        wk = (Spinner) findViewById(R.id.wck);
        uidfield = (TextView)findViewById(R.id.uidfield);
        pass = (EditText)findViewById(R.id.passuser);
        conpass = (EditText)findViewById(R.id.conpassuser);



    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    public void GetFieldsData(){

        String first_name = String.valueOf(firstname.getText());
        String last_name = String.valueOf(lastname.getText());
        String dob = String.valueOf(datedit.getText());
        String Place = String.valueOf(pob.getText());
        String father = String.valueOf(fatname.getText());
        String mother = String.valueOf(motname.getText());
        String age = String.valueOf(ageid.getText());
        String emailadd = String.valueOf(email.getText());
        String bat_hand = bat.getSelectedItem().toString();
        String bwl_hand = bwl.getSelectedItem().toString();
        String bwl_type = bwlpro.getSelectedItem().toString();
        String wckp = wk.getSelectedItem().toString();
        String add_1 = String.valueOf(add1.getText());
        String City = String.valueOf(city.getText());
        String district = String.valueOf(dist.getText());
        String State = String.valueOf(state.getText());
        String mobile = String.valueOf(mob.getText());
        String tele = String.valueOf(tel.getText());
        String blood = bloodgrp.getSelectedItem().toString();
        String passw = pass.getText().toString();
        String conpassw = conpass.getText().toString();
        if(first_name.isEmpty()||last_name.isEmpty()||dob.isEmpty()||Place.isEmpty()||
                father.isEmpty()||mother.isEmpty()||age.isEmpty()||bat_hand.isEmpty()||
                bwl_hand.isEmpty()||bwl_type.isEmpty()||wckp.isEmpty()||add_1.isEmpty()||
                City.isEmpty()||district.isEmpty()||State.isEmpty()||mobile.isEmpty()||
                tele.isEmpty()||blood.isEmpty()||emailadd.isEmpty()||passw.isEmpty()||conpassw.isEmpty())

        {
            Toast.makeText(RegistrationFinal.this, "All the fields are mandatory", Toast.LENGTH_SHORT).show();

        }
        else
        if(!Patterns.EMAIL_ADDRESS.matcher(emailadd).matches())
        {
            email.setError("enter valid email address");
        }

        else if(!passw.equals(conpassw)){
            conpass.setError("Passwords do not match");
            Toast.makeText(RegistrationFinal.this, "Passwords mismatch", Toast.LENGTH_SHORT).show();


        }
        else
        {
            //uploadImage = new UploadImage(Keyvalue);
            //uploadImage.execute();
           // SignUpCredentials(email.getText().toString(), pass.getText().toString(),uidfield.getText().toString());

            uploadImage();
        }


    }

  private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        String dob = sdf.format(myCalendar.getTime());
        datedit.setText(dob);
        getDateDifference(dob);
    }
    DatePickerDialog.OnDateSetListener datePickerDialog = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    view.setSpinnersShown(true);
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };

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
            int thres = age/4;

            int days=diffDays%365;
            days = days-thres;
            ageid.setText(age+" Years & days "+days);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    private void uploadImage()


    {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        loading.dismiss();
                        if (s != null)
                        {

                            if(s.equals("Uploaded"))
                            {

                                sessionManager.createLoginSession(email.getText().toString(),uidfield.getText().toString());
                                startActivity(new Intent(RegistrationFinal.this,start.class));

                                finish();


                            }else if(s.equals("exists"))
                            {
                                Toast.makeText(RegistrationFinal.this, "Email Already Registered" , Toast.LENGTH_LONG).show();


                            }
                        }





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                        Toast.makeText(RegistrationFinal.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(RegistrationFinal.this, "error", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                BitmapDrawable drawable = (BitmapDrawable) dp.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                String imageDP = getStringImage(bitmap);
               String uid = uidfield.getText().toString();
                String first_name = String.valueOf(firstname.getText());
                String last_name = String.valueOf(lastname.getText());
                String dob = String.valueOf(datedit.getText());
                String Place = String.valueOf(pob.getText());
                String father = String.valueOf(fatname.getText());
                String mother = String.valueOf(motname.getText());
                String age = String.valueOf(ageid.getText());
                String emailadd = String.valueOf(email.getText());
                String bat_hand = bat.getSelectedItem().toString();
                String bwl_hand = bwl.getSelectedItem().toString();
                String bwl_type = bwlpro.getSelectedItem().toString();
                String wckp = wk.getSelectedItem().toString();
                String add_1 = String.valueOf(add1.getText());
                String City = String.valueOf(city.getText());
                String district = String.valueOf(dist.getText());
                String State = String.valueOf(state.getText());
                String mobile = String.valueOf(mob.getText());
                String tele = String.valueOf(tel.getText());
                String blood = bloodgrp.getSelectedItem().toString();
                String passs  =  pass.getText().toString();


                Keyvalue.put("fname",first_name);
                Log.d("Data",first_name);
                Keyvalue.put("lname",last_name);
                Keyvalue.put("father",father);
                Keyvalue.put("mother",mother);
                Keyvalue.put("dob",dob);
                Keyvalue.put("pob",Place);
                Keyvalue.put("age",age);
                Keyvalue.put("email",emailadd);
                Keyvalue.put("mob",mobile);
                Keyvalue.put("tel",tele);
                Keyvalue.put("add",add_1);
                Keyvalue.put("city",City);
                Keyvalue.put("dist",district);
                Keyvalue.put("state",State);
                Keyvalue.put("uid",uid);
                Log.d("uid",uid);
                Keyvalue.put("bat_hand",bat_hand);
                Keyvalue.put("bwl_hand",bwl_hand);
                Keyvalue.put("bwl_pro",bwl_type);
                Keyvalue.put("wckp",wckp);
                Keyvalue.put("blood_grp",blood);
                Keyvalue.put("image",imageDP);
                Keyvalue.put("pass",passs);
                Log.d("image",imageDP.toString());


                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);



        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public boolean SignUpCredentials(final String emailid, final String pass, final String uid){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Checking credentials...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        loading.dismiss();
                        if(s==null){
                            Toast.makeText(getApplicationContext(), "Please Try Again Later" , Toast.LENGTH_LONG).show();


                        }else if(s.equals("Sucess")){

                            uploadImage();



                        }else if (s.equals("Exists"))
                        {

                            email.setError("Alredy in use");
                            Toast.makeText(getApplicationContext(), "User Already Exists Change Email" , Toast.LENGTH_LONG).show();


                        }
                        else
                            Toast.makeText(getApplicationContext(), "Some Error Occured Please Try After Sometime.." , Toast.LENGTH_LONG).show();


                        //Disimissing the progress dialog

                        //Showing toast message of the response
                        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getApplicationContext(), "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<>();
                Keyvalue.put("email",emailid);
                Log.d("login email", emailid);
                Keyvalue.put("pass",pass);
                Keyvalue.put("uid", uid);

                Log.d("pass", pass);


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
