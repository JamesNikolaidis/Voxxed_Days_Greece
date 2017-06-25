package voxxed_days_greece.voxxeddays.Screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.adapters.session_adapter;
import voxxed_days_greece.voxxeddays.adapters.speakers_adapter;
import voxxed_days_greece.voxxeddays.alert_dialogs.speaker_informations_dialog;
import voxxed_days_greece.voxxeddays.api.get_sessions;
import voxxed_days_greece.voxxeddays.api.get_speakers;
import voxxed_days_greece.voxxeddays.general.delete_content;
import voxxed_days_greece.voxxeddays.media.facebook;
import voxxed_days_greece.voxxeddays.media.twitter;
import voxxed_days_greece.voxxeddays.models.speakers;
import voxxed_days_greece.voxxeddays.models.sessions;
public class main_screen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private ViewFlipper mViewFlipper;
    private ScrollView scrollView;
    private static Timer mTime = new Timer();
    private LinearLayout mRelative;
    private Button mFloatingButton;
    private TextView mWelcomeTitle=null,mWelcomeBody=null;
    public static Activity mActivity=null;
    private View theView;
    private Timer checkforchanges=new Timer();
    private LinearLayout mSpeakersLayout=null;
    private ListView mSpeaker1=null,mSpeaker2=null,sessions=null;
    private get_speakers get_speakers = null;
    private ArrayList<speakers> speakerses1=new ArrayList<>(),speakerses2=new ArrayList<>();
    private ArrayList<String> speakersPictures=new ArrayList<>(),speakersPictures2=new ArrayList<>();
    private ArrayList<String> speakersName=new ArrayList<>();
    private ArrayList<String> sessionsName=new ArrayList<>();
    private ArrayList<String> speakersPictures1=new ArrayList<>();
    private ArrayList<String> speakersName1=new ArrayList<>();
    private speakers_adapter adapter,adapter1;
    private session_adapter session_adapter=null;
    private  SharedPreferences.Editor editor;
    private static int STATE_NUMBER=-1;
    private get_sessions get_Sessions= null;
    View speaker;
    public static Uri tweet_image_uri=null;
    private static AlertDialog.Builder mBuilder;
    private EditText tweetText = null;
    private  ImageView user_pic;
    private String mCurrentPath;
    private View.OnClickListener add_images_onclick_listener=null;
    private ShareDialog shareDialog=null;
    public static  Bitmap  mImageBitmap;
    private twitter twitter;
    private facebook mFacebook;
    public static String APPLICATION_TITLE=null;
    private CallbackManager callbackManager;
    public static ArrayList<sessions> mScheduleSessions=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId("120326848562720");
        FacebookSdk.sdkInitialize(this);
        shareDialog = new ShareDialog(this);
        Twitter.initialize(this);
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
        STATE_NUMBER=sharedPreferences.getInt(First_Screen.STATE_NUMBER,-1);


       BackGroundStaff backGroundStaff = new BackGroundStaff();
       backGroundStaff.execute();


        scrollView = (ScrollView)findViewById(R.id.scrollView);
        get_speakers = get_speakers.get_speakersObject();
        get_Sessions= get_sessions.get_sessionObject();
        editor= sharedPreferences.edit();
        ImageOnclickListener();
        mScheduleSessions = new ArrayList<>();





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
            delete_content.DestroyFlipperAndTimer(mRelative,speaker,mActivity);
            setSpeakerListViews();
            Updates();
        } else if (id == R.id.sessions) {
            delete_content.DestroyFlipperAndTimerForSession(R.id.sessions_linear_layout,R.layout.sessions_activity,mRelative,mActivity);
            setSessionListViews();
        } else if (id == R.id.keynotes) {
            delete_content.DestroyFlipperAndTimer(mRelative,speaker,mActivity);
        } else if (id == R.id.voting) {
            delete_content.DestroyFlipperAndTimer(mRelative,speaker,mActivity);
        } else if (id == R.id.twitter) {
            delete_content.DestroyFlipperAndTimerForSession(R.id.twitter_log_in_dialog,R.layout.twitter_dialog,mRelative,mActivity);
            setTitle("Fill your Tweet");
            SetImageClickListener();
        } else if (id == R.id.facebook) {
            delete_content.DestroyFlipperAndTimerForSession(R.id.facebook_dialog_layout,R.layout.facebook_dialog,mRelative,mActivity);
            setTitle("Fill your facebook post");
            SetFacebookImage();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void SetTitle(TextView stateName,ImageView imageView){

        switch (sharedPreferences.getInt(First_Screen.STATE_NUMBER,-1)){

            case 0:
                setTitle(R.string.app_name_thessaloniki);
                APPLICATION_TITLE = getResources().getString(R.string.app_name_thessaloniki);
                break;
            case 1:
                setTitle(R.string.app_name_athens);
                APPLICATION_TITLE = getResources().getString(R.string.app_name_athens);
                break;
            case 2:
                setTitle(R.string.app_name_volos);
                APPLICATION_TITLE = getResources().getString(R.string.app_name_volos);
                break;
            case 3:
                setTitle(R.string.app_name_patra);
                APPLICATION_TITLE = getResources().getString(R.string.app_name_patra);
                break;
            case 4:
                setTitle(R.string.app_name_crete);
                APPLICATION_TITLE = getResources().getString(R.string.app_name_crete);
                break;
            case 5:
                setTitle(R.string.app_name_piraeus);
                APPLICATION_TITLE = getResources().getString(R.string.app_name_piraeus);
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


    //**************Delete Content

//**************************************



    public static void StopTime(){
        mTime.cancel();
    }
    public void ResumeTime(){mTime = new Timer();}

//**********Media functions ***********************//
//
//    ***************
    public void SetFacebookImage() {
        user_pic = (ImageView) findViewById(R.id.user_facebook_image);
        final LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login);
        loginButton.performClick();
        callbackManager = CallbackManager.Factory.create();
        mFacebook = new facebook(mActivity);
        mFacebook.SetLoginButtonCallback(loginButton,callbackManager);
        mFacebook.SetLoginManagerLoginCallback(callbackManager,getApplicationContext());
        mFacebook.facebook_share((Button)findViewById(R.id.facebook_share));
        user_pic.setOnClickListener(add_images_onclick_listener);
    }


    public void SetImageClickListener(){
        user_pic = (ImageView)findViewById(R.id.user_tweet_image);
        tweetText = (EditText)findViewById(R.id.twitter_text);
        twitter = new twitter(mActivity,tweetText,getApplicationContext());
        TwitterLoginButton twitterLoginButton = (TwitterLoginButton)findViewById(R.id.twitter_login);
        twitter.SetTwitterLoginButtonCallback(twitterLoginButton,getApplicationContext());
        twitter.setOnPostTweetListener((Button)findViewById(R.id.twitter_tweet),mRelative);
        twitterLoginButton.performClick();
        user_pic.setOnClickListener(add_images_onclick_listener);
    }

//**********End ***********************//
//
//    ***************


    public class BackGroundStaff extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            try {
                fetchData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GetSession();

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



    public void setSpeakerListViews(){


        mSpeaker1 = (ListView) findViewById(R.id.speakers_list_1);
        mSpeaker2 = (ListView) findViewById(R.id.speakers_list_2);
        adapter=new speakers_adapter(getApplicationContext(),speakersPictures,speakersName);
        adapter1=new speakers_adapter(getApplicationContext(),speakersPictures1,speakersName1);
        mSpeaker1.setAdapter(adapter);
        mSpeaker2.setAdapter(adapter1);

        mSpeaker1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                speaker_informations_dialog.create_speaker_dialog(getApplicationContext(),mActivity,((TextView)view.findViewById(R.id.speaker_name)).getText().toString());

            }
        });


        mSpeaker2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                speaker_informations_dialog.create_speaker_dialog(getApplicationContext(),mActivity,((TextView)view.findViewById(R.id.speaker_name)).getText().toString());
            }
        });
        this.setTitle("Our Amazing Speakers");

    }



    public void setSessionListViews(){
        sessions = (ListView) findViewById(R.id.sessions_list_view);

        session_adapter=new session_adapter(getApplicationContext(),speakersPictures2,sessionsName);
        sessions.setAdapter(session_adapter);

        sessions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToSessionActivity = new Intent(mActivity,session_screen.class);
                goToSessionActivity.putExtra("session_name",get_Sessions.returnSessionList().get(position).getName());
                goToSessionActivity.putExtra("session_brief",get_Sessions.returnSessionList().get(position).getBrief());
                goToSessionActivity.putExtra("session_time",get_Sessions.returnSessionList().get(position).getTime());
                goToSessionActivity.putExtra("session_room",get_Sessions.returnSessionList().get(position).getRoom());
                goToSessionActivity.putExtra("session_speaker",get_Sessions.returnSessionList().get(position).getSpeaker());
                goToSessionActivity.putExtra("session_id",get_Sessions.returnSessionList().get(position).getId());
                goToSessionActivity.putExtra("session_keynote",get_Sessions.returnSessionList().get(position).isKeynote());
                mActivity.startActivity(goToSessionActivity);


            }
        });
        this.setTitle("Our Amazing Sessions");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ClearArrays();
        if(mTime!=null){mTime.cancel();}

    }



    public void fetchData() throws InterruptedException {
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

        counter=0;
    }


    public void GetSession(){
        int counter=0;

        while(counter!=get_Sessions.returnSessionList().size()){
            sessionsName.add(get_Sessions.returnSessionList().get(counter).getName());
            counter++;
        }

        counter=0;
        while(counter!=get_Sessions.returnSessionList().size()){
            speakersPictures2.add(get_speakers.ReturnSpeakerUrl(get_Sessions.returnSessionList().get(counter).getSpeaker()));
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
                           try {
                               fetchData();
                               GetSession();
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                           setSpeakerListViews();
                           editor.putInt("CHANGE_COMMIT",0).commit();
                       }
                   });


                }else if(sharedPreferences.getInt("CHANGE_COMMIT",-1)==2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                fetchData();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClearArrays();
                                GetSession();
                            setSessionListViews();
                            editor.putInt("CHANGE_COMMIT", 0).commit();
                        }
                    });
                }
            }
        },0,10000);
    }

    public void ClearArrays() {
        speakersName.clear();
        speakersName1.clear();
        speakersPictures.clear();
        speakersPictures1.clear();
        sessionsName.clear();
        speakersPictures2.clear();
    }








    public void take_a_picture(){
        Intent take_picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile=null;
        if (take_picture.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("The tag", "IOException");
            }

        }

        take_picture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        if (take_picture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(take_picture,0);
        }

    }

    public void take_a_picture_from_storage(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto ,1);

    }





    public void create_media_dialog(Activity mActivity){
        mBuilder = new AlertDialog.Builder(mActivity);
        mBuilder.setTitle("Which method of import do you want?");
        mBuilder.setItems(new String[]{"From Camera?","From Storage?"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    take_a_picture();
                }else{
                    take_a_picture_from_storage();
                }

            }
        });
        mBuilder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    try {
                        mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPath));
                        tweet_image_uri = Uri.parse(mCurrentPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    user_pic.setImageBitmap(mImageBitmap);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    tweet_image_uri = data.getData();
                    Uri imageUri = data.getData();
                    try {
                        mImageBitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        user_pic.setImageBitmap(mImageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }




    }




    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPath = "file:" + image.getAbsolutePath();
        return image;
    }


    public void ImageOnclickListener(){
        add_images_onclick_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_media_dialog(mActivity);
            }

        };
    }



}
