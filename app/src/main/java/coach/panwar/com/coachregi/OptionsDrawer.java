package coach.panwar.com.coachregi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NewFragment.OnFragmentInteractionListener, EventsFragment.OnFragmentInteractionListener, TeamDetailsFragment.OnFragmentInteractionListener, QueryFragment.OnFragmentInteractionListener, SubmitQueryFragment.OnFragmentInteractionListener, GalleryFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, NewsDetailFragment.OnFragmentInteractionListener, ScheduleFragment.OnFragmentInteractionListener {


    SessionManager sessionManager;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Check();
        textView = (TextView)findViewById(R.id.activitylabel);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager transaction = getSupportFragmentManager();

                textView.setText(" SUBMIT REQUEST ");
                SubmitQueryFragment newFragment  = SubmitQueryFragment.newInstance("","");
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.nav_rep, newFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();


            }
        });



        sessionManager = new SessionManager(getApplicationContext());



        FragmentManager transaction = getSupportFragmentManager();






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {



            textView.setText(" NEWS AND UPDATES ");
            FragmentManager transaction = getSupportFragmentManager();

            NewFragment newFragment  = NewFragment.newInstance("","");
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.nav_rep, newFragment);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();


            // Handle the camera action
        } else if (id == R.id.nav_gallery) {


            textView.setText(" MY GALLERY ");
            FragmentManager transaction = getSupportFragmentManager();

            GalleryFragment newFragment  = GalleryFragment.newInstance("","");
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.nav_rep, newFragment);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();



        } else if (id == R.id.nav_events) {
            FragmentManager transaction = getSupportFragmentManager();


            textView.setText(" UPCOMING EVENTS ");
            EventsFragment newFragment  = EventsFragment.newInstance("","");
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.nav_rep, newFragment);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();




        } else if (id == R.id.nav_query) {


            textView.setText(" QUERIES ");
            FragmentManager transaction = getSupportFragmentManager();

            QueryFragment newFragment  = QueryFragment.newInstance("","");
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.nav_rep, newFragment);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();


        }

        else if (id==R.id.nav_log_out)
        {
            sessionManager.logoutUser();
            startActivity(new Intent(OptionsDrawer.this, LoginActivity.class));

        }

        else if (id==R.id.nav_calender)
        {

            FragmentManager transaction = getSupportFragmentManager();

            ScheduleFragment newFragment  = ScheduleFragment.newInstance("","");
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.nav_rep, newFragment);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();
        }


        else if (id==R.id.nav_manage)
        {


            FragmentManager transaction = getSupportFragmentManager();


            textView.setText(" MY PROFILE ");
            ProfileFragment newFragment  = ProfileFragment.newInstance("","");
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.nav_rep, newFragment);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public Boolean Check() {
        ConnectivityManager cn = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {
            return true;
        } else {

            Toast.makeText(getApplicationContext(), "No internet connection.!",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(OptionsDrawer.this, NoConnectionActivity.class));
            finish();


            return false;
        }
    }
}
