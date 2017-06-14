package voxxed_days_greece.voxxeddays.Screens;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.adapters.speakers_adapter;
import voxxed_days_greece.voxxeddays.api.get_speakers;
import voxxed_days_greece.voxxeddays.models.speakers;

public class main_screen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView mStateName;
    private SharedPreferences sharedPreferences;
    private ViewFlipper mViewFlipper;
    private ScrollView scrollView;
    private Timer mTime = new Timer();
    private LinearLayout mRelative;
    private Button mFloatingButton;
    private TextView mWelcomeTitle=null,mWelcomeBody=null;
    public static Activity mActivity=null;
    private View theView;
    private Timer checkforchanges=new Timer();
    private LinearLayout mSpeakersLayout=null;
    private ListView mSpeaker1=null,mSpeaker2=null;
    private get_speakers get_speakers = null;
    private ArrayList<speakers> speakerses1=new ArrayList<>(),speakerses2=new ArrayList<>();
    private ArrayList<String> speakersPictures=new ArrayList<>();
    private ArrayList<String> speakersName=new ArrayList<>();
    private ArrayList<String> speakersPictures1=new ArrayList<>();
    private ArrayList<String> speakersName1=new ArrayList<>();
    private speakers_adapter adapter,adapter1;
    private  SharedPreferences.Editor editor;
    View speaker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(First_Screen.STATE_SELECTION,MODE_PRIVATE);
        setTitle(R.string.app_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        LayoutInflater inflater = getLayoutInflater();
        theView = inflater.inflate(R.layout.nav_header_main,null);


        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                SetNavTitle((TextView)drawerView.findViewById(R.id.voxxed_state_name),(ImageView)drawerView.findViewById(R.id.stateImageView));

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        mViewFlipper = (ViewFlipper)findViewById(R.id.imageSlider);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mRelative = (LinearLayout)findViewById(R.id.main_screen_relative);
        mFloatingButton = (Button)findViewById(R.id.fab);

        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        changeImage();
        mWelcomeTitle = (TextView)findViewById(R.id.message_title);
        mWelcomeBody = (TextView)findViewById(R.id.message_body);



       BackGroundStaff backGroundStaff = new BackGroundStaff();
       backGroundStaff.execute();


        scrollView = (ScrollView)findViewById(R.id.scrollView);
        get_speakers = get_speakers.get_speakersObject();
        editor= sharedPreferences.edit();
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


        if (id == R.id.speakers) {
            DestroyFlipperAndTimer();
            setSpeakerListViews();
            Updates();
        } else if (id == R.id.sessions) {
            DestroyFlipperAndTimer();
        } else if (id == R.id.keynotes) {
            DestroyFlipperAndTimer();
        } else if (id == R.id.voting) {
            DestroyFlipperAndTimer();
        } else if (id == R.id.nav_share) {
            DestroyFlipperAndTimer();
        } else if (id == R.id.nav_send) {
            DestroyFlipperAndTimer();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void SetTitle(TextView stateName,ImageView imageView){

        switch (sharedPreferences.getInt(First_Screen.STATE_NUMBER,-1)){

            case 0:
                setTitle(R.string.app_name_thessaloniki);
                break;
            case 1:
                setTitle(R.string.app_name_athens);
                break;
            case 2:
                setTitle(R.string.app_name_volos);
                break;
            case 3:
                setTitle(R.string.app_name_patra);
                break;
            case 4:
                setTitle(R.string.app_name_crete);
                break;
            case 5:
                setTitle(R.string.app_name_piraeus);
                break;
            default:
                setTitle("Error");
                break;


        }
    }

    public void SetNavTitle(TextView stateName,ImageView imageView){

        switch (sharedPreferences.getInt(First_Screen.STATE_NUMBER,-1)){

            case 0:
                imageView.setImageResource(R.drawable.voxxed_days_thessaloniki_menu_icon);
                stateName.setText(R.string.app_name_thessaloniki);
                break;
            case 1:
                imageView.setImageResource(R.drawable.acropolis_ico);
                stateName.setText(R.string.app_name_athens);
                break;
            case 2:
                imageView.setImageResource(R.drawable.volos3);
                stateName.setText(R.string.app_name_volos);
                break;
            case 3:
                imageView.setImageResource(R.drawable.patra_ico);
                stateName.setText(R.string.app_name_patra);
                break;
            case 4:
                imageView.setImageResource(R.drawable.crete_ico_v2);
                stateName.setText(R.string.app_name_crete);
                break;
            case 5:
                imageView.setImageResource(R.drawable.piraeus_ico);
                stateName.setText(R.string.app_name_piraeus);
                break;
            default:
                setTitle("Error");
                break;


        }
    }



    public void changeImage(){
            mTime.schedule(new TimerTask() {
                @Override
                public void run() {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           mViewFlipper.showNext();
                       }
                   });
                }
            },0,10000);
    }

    public void DestroyFlipperAndTimer(){
            mRelative.removeAllViews();
            mRelative.setBackgroundColor(Color.parseColor("#867979"));
            LayoutInflater inflater1 = LayoutInflater.from(this);
            speaker = inflater1.inflate(R.layout.speakers,null);
            mRelative.addView((LinearLayout)speaker.findViewById(R.id.speakers_linear_layout));
            StopTime();
    }

    public void StopTime(){
        mTime.cancel();
    }
    public void ResumeTime(){mTime = new Timer();}






    public class BackGroundStaff extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            fetchData();


            if(sharedPreferences.getInt(First_Screen.STATE_NUMBER,-1)==0){
                //is thessaloniki
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWelcomeTitle.setText(mActivity.getResources().getString(R.string.state_thessaloniki_welocome));
                        mWelcomeBody.setText(mActivity.getResources().getString(R.string.thessaloniki_welcome_message));
                    }
                });

            }else if(sharedPreferences.getInt(First_Screen.STATE_NUMBER,-1)==1){
                //is Athens
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWelcomeTitle.setText(mActivity.getResources().getString(R.string.state_athens_welcome));
                        mWelcomeBody.setText(mActivity.getResources().getString(R.string.athens_welcome_message));
                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWelcomeTitle.setText("You do not find information about this state");
                        mWelcomeBody.setText("You do not find information about this state");
                    }
                });
            }



            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SetTitle((TextView)theView.findViewById(R.id.voxxed_state_name),(ImageView)theView.findViewById(R.id.stateImageView));
                }
            });



            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        StopTime();
    }


    @Override
    protected void onResume() {
        super.onResume();
        ResumeTime();
        changeImage();
    }


    public void check_orientation(){

       // Configuration.ORIENTATION_LANDSCAPE;
    }



    public void setSpeakerListViews(){


        mSpeaker1 = (ListView) findViewById(R.id.speakers_list_1);
        mSpeaker2 = (ListView) findViewById(R.id.speakers_list_2);
        adapter=new speakers_adapter(getApplicationContext(),speakersPictures,speakersName);
        adapter1=new speakers_adapter(getApplicationContext(),speakersPictures1,speakersName1);
        mSpeaker1.setAdapter(adapter);
        mSpeaker2.setAdapter(adapter1);
        this.setTitle("Our Amazing Speakers");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        speakersName.clear();
        speakersName1.clear();
        speakersPictures.clear();
        speakersPictures1.clear();
        get_speakers.clearSpeakersList();
        checkforchanges.cancel();
    }

    public  void fetchData(){
        ArrayList<speakers> speakerses = get_speakers.returnSpeakersList();
        int counter=0;

        while(counter!=(speakerses.size()/2)){
            speakerses1.add(speakerses.get(counter));
            speakersPictures.add(speakerses.get(counter).getPhoto_url());
            speakersName.add(speakerses.get(counter).getName());
            counter++;
        }

        while(counter!=(speakerses.size())){
            speakerses2.add(speakerses.get(counter));
            speakersPictures1.add(speakerses.get(counter).getPhoto_url());
            speakersName1.add(speakerses.get(counter).getName());
            counter++;
        }
    }



    public void Updates(){
        checkforchanges.schedule(new TimerTask() {
            @Override
            public void run() {
                if(sharedPreferences.getInt("CHANGE_COMMIT",-1)==1){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           ClearArrays();
                           fetchData();
                           setSpeakerListViews();
                           editor.putInt("CHANGE_COMMIT",0).commit();
                       }
                   });

                }
            }
        },0,10000);
    }

    public void ClearArrays(){
        speakersName.clear();
        speakersName1.clear();
        speakersPictures.clear();
        speakersPictures1.clear();
    }

}
