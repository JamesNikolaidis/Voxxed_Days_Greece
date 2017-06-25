package voxxed_days_greece.voxxeddays.Screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;
import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.adapters.state_spinner;
import voxxed_days_greece.voxxeddays.alert_dialogs.simple_spinner;
import voxxed_days_greece.voxxeddays.api.get_sessions;
import voxxed_days_greece.voxxeddays.api.get_speakers;
import voxxed_days_greece.voxxeddays.api.get_storage_files;
import voxxed_days_greece.voxxeddays.models.speakers;

/**
 * Created by James Nikolaidis on 5/24/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class First_Screen extends AppCompatActivity {

    private  static Intent nextScreen;
    private SimpleDateFormat DateFormat=null;
    private Date getCurrentDate=null;
    public static Activity mActivity=null;
    private SharedPreferences mSharedPreference=null;
    private SharedPreferences.Editor editor=null;
    private static ArrayList<Drawable> images = new ArrayList<>();
    public static String STATE_SELECTION="state_selection",STATE="",YEAR="";
    public static String STATE_NUMBER="state_number";
    private static Context mContext=null;
    private GregorianCalendar gregorianCalendar;
    private get_sessions get_sessions=null;
    private static int make_lock=0;
    private get_storage_files get_storage_files;
    private boolean downloadFiles=false;
    private ArrayList<speakers> speakersArray = new ArrayList<>();
    private   get_speakers speakers ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_layout);
        ButterKnife.bind(this);
        getCurrentDate = new Date();
        mActivity = this;
        mSharedPreference = getSharedPreferences("state_selection",MODE_PRIVATE);
        editor = mSharedPreference.edit();
        mContext=getApplicationContext();
        FirebaseAuth.getInstance().signInWithEmailAndPassword("dimitriosnikolaidis@hotmail.com","2310708904");

    }



    @OnClick(R.id.StateSpinner)
    public void SpinnerTextViewClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
            alertDialog.show();
        alertDialog.setContentView(R.layout.state_spinner_item);
        ListView listView = (ListView) alertDialog.findViewById(R.id.SpinnerListView);
        listView.setAdapter(new state_spinner(getApplicationContext(), getResources().getStringArray(R.array.State_List), this, returnImagesId(this)));
        gregorianCalendar= new GregorianCalendar(TimeZone.getDefault());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("state_number", position);
                editor.commit();
                if(position==0){
                    STATE = "Thessaloniki";
                    YEAR = String.valueOf(gregorianCalendar.get(Calendar.YEAR));
                    get_storage_files = voxxed_days_greece.voxxeddays.api.get_storage_files.returnStorageObject(mActivity,STATE,YEAR);
                }
                if(make_lock==0){
                make_lock=1;
                BackgroundStaff backgroundStaff =new BackgroundStaff();
                backgroundStaff.execute();
                simple_spinner.createSpinner(mActivity,getApplicationContext());
                simple_spinner.ChangeText("Download important data....");
                }else{
                    nextScreen = new Intent(getApplicationContext(), main_screen.class);
                    startActivity(nextScreen);
                }


            }
        });
    }



    public  ArrayList<Drawable> returnImagesId(Activity activity){
        images.add(mActivity.getResources().getDrawable(R.drawable.white_tower));
        images.add(mActivity.getResources().getDrawable(R.drawable.acropolis_ico));
        images.add(mActivity.getResources().getDrawable(R.drawable.volos3));
        images.add(mActivity.getResources().getDrawable(R.drawable.patra_ico));
        images.add(mActivity.getResources().getDrawable(R.drawable.crete_ico_v2));
        images.add(mActivity.getResources().getDrawable(R.drawable.piraeus_ico));
        return images;
    }

    public void downloadSpeaker(String state,String year,String Speaker) throws IOException {
        get_storage_files.getSpeakersPictures(state,year,Speaker+".jpg");
    }


    public class BackgroundStaff extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
             speakers = get_speakers.get_speakersObject();
             get_sessions = voxxed_days_greece.voxxeddays.api.get_sessions.get_sessionObject();
                    setTimer();
                    speakers.getSpeakersListener(STATE,YEAR);
                    get_sessions.setSessionListener(STATE,YEAR);






            return null;
        }
    }

    public void setTimer(){
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(get_speakers.downloadFinished==true&& voxxed_days_greece.voxxeddays.api.get_sessions.downloadFinished==true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            simple_spinner.ChangeText("Download Photos(it wont take many bytes)....");
                        }
                    });
                    speakersArray = speakers.returnSpeakersList();
                    int counter=0;
                    do{
                        try {
                            downloadSpeaker(STATE,YEAR,speakersArray.get(counter).getPhoto_url());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        counter++;
                    }while (counter<speakersArray.size());
                    nextScreen = new Intent(getApplicationContext(), main_screen.class);
                    startActivity(nextScreen);
                    simple_spinner.destroyAlertDialog();
                    timer.cancel();
                }



            }
        },0,1000);
    }



        static public boolean deleteDirectory(File path) {
            if( path.exists() ) {
                File[] files = path.listFiles();
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
            return( path.delete() );
        }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        File f = new  File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/");
        deleteDirectory(f);
    }
}
