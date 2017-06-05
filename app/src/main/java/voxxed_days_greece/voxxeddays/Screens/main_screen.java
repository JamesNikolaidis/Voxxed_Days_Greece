package voxxed_days_greece.voxxeddays.Screens;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import voxxed_days_greece.voxxeddays.R;

public class main_screen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView mStateName;
    private SharedPreferences sharedPreferences;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(First_Screen.STATE_SELECTION,MODE_PRIVATE);
        setTitle(R.string.app_name);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                SetTitle((TextView)drawerView.findViewById(R.id.voxxed_state_name),(ImageView)drawerView.findViewById(R.id.stateImageView));

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.nav_header_main, null); //log.xml is your file.



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
        getMenuInflater().inflate(R.menu.main, menu);
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

        /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void SetTitle(TextView stateName,ImageView imageView){

        switch (sharedPreferences.getInt(First_Screen.STATE_NUMBER,-1)){

            case 0:
                imageView.setImageResource(R.drawable.voxxed_days_thessaloniki_menu_icon);
                stateName.setText(R.string.app_name_thessaloniki);
                setTitle(R.string.app_name_thessaloniki);
                break;
            case 1:
                imageView.setImageResource(R.drawable.acropolis_ico);
                stateName.setText(R.string.app_name_athens);
                setTitle(R.string.app_name_athens);
                break;
            case 2:
                imageView.setImageResource(R.drawable.volos3);
                stateName.setText(R.string.app_name_volos);
                setTitle(R.string.app_name_volos);
                break;
            case 3:
                imageView.setImageResource(R.drawable.patra_ico);
                stateName.setText(R.string.app_name_patra);
                setTitle(R.string.app_name_patra);
                break;
            case 4:
                imageView.setImageResource(R.drawable.crete_ico_v2);
                stateName.setText(R.string.app_name_crete);
                setTitle(R.string.app_name_crete);
                break;
            case 5:
                imageView.setImageResource(R.drawable.piraeus_ico);
                stateName.setText(R.string.app_name_piraeus);
                setTitle(R.string.app_name_piraeus);
                break;
            default:
                setTitle("Error");
                break;


        }
    }





}
