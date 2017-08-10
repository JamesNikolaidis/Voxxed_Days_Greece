package voxxed_days_greece.voxxeddays.Screens;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.alert_dialogs.simple_spinner;
import voxxed_days_greece.voxxeddays.api.get_sessions;
import voxxed_days_greece.voxxeddays.api.get_speakers;
import voxxed_days_greece.voxxeddays.models.sessions;
import voxxed_days_greece.voxxeddays.models.speakers;
import voxxed_days_greece.voxxeddays.notifications.schedule_notifications;

/**
 * Created by James Nikolaidis on 6/23/2017.
 */

public class session_screen extends AppCompatActivity {
    private get_sessions mGet_sessions=null;
    private TextView mTitle=null,mDescription=null,mSpeaker=null,mTime=null,mRoom=null;
    private ArrayList<sessions> mSessions=null;
    private ArrayList<speakers> mSpeakers = null;
    private sessions mSessionModel=null;
    private String SessionTitle=null;
    private get_speakers get_speakers=null;
    private speakers mSpeakerObject =null;
    private boolean isKeynote=false;
    private boolean lockAddToSchedule=false;
    private Activity mActivity=null;
    private Button mKeynoteButton =null;
    private String mCurrentPath = null;
    private InputStream instream;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_page);
        setTitle("");
        mGet_sessions = get_sessions.get_sessionObject();
        mTitle = (TextView) findViewById(R.id.session_title);
        mDescription = (TextView) findViewById(R.id.session_brief);
        mSpeaker = (TextView) findViewById(R.id.session_speaker);
        mTime = (TextView) findViewById(R.id.session_time);
        mRoom = (TextView) findViewById(R.id.session_room);
        mActivity = this;
        mKeynoteButton = (Button) findViewById(R.id.keynote_btn);
        get_speakers = voxxed_days_greece.voxxeddays.api.get_speakers.get_speakersObject();
        InitializeData initializeData = new InitializeData();
        initializeData.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.session_menu, menu);
            MenuItem keynote = menu.findItem(R.id.keynote);
            keynote.setVisible(isKeynote);

        return true;

    }

    public void NavigateBack(View view) {
            this.finish();
    }

    public void addSessionToSchedule(View view) {
         boolean TextExistion = checkUserSessionPreference(mSessionModel);
      if(!lockAddToSchedule || TextExistion){
        if(TextExistion){
            Toast.makeText(getApplicationContext(),"You have add it already to your schedule.",Toast.LENGTH_SHORT).show();
        }else {
              final int sizeBefore = main_screen.mScheduleSessions.size();
//            main_screen.mScheduleSessions.add(mSessionModel);
            try {
                saveuserSessionPreference(mSessionModel);
            } catch (IOException e) {
                e.printStackTrace();
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setMessage("Do would want to get a notification reminder about this session?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            schedule_notifications.CreateNotificationForSession(mActivity, mSessionModel);
                            if (checkSesssionSaveOnText(mSessionModel)) {

                                Toast.makeText(getApplicationContext(), "The Session has been added to your schedule.", Toast.LENGTH_SHORT).show();



                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (checkSesssionSaveOnText(mSessionModel)) {
                                Toast.makeText(getApplicationContext(), "The Session has been added to your schedule.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            builder.create();
            builder.show();
            lockAddToSchedule = !lockAddToSchedule;
        }}
        else {
          Toast.makeText(getApplicationContext(), "You have add it already to your schedule.", Toast.LENGTH_SHORT).show();
         }





    }

    public void isKeynote(View view) {
        Toast.makeText(getApplicationContext(),"The Speech is Keynote",Toast.LENGTH_SHORT).show();
    }

    public class InitializeData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            mSpeakers = new ArrayList<>();
            mSpeakers = get_speakers.returnSpeakersList();
            int counter = 0;
            while(mSpeakers.get(counter).getId()!=getIntent().getLongExtra("session_speaker",-1)){
                counter++;
            }
            mSpeakerObject = mSpeakers.get(counter);
            setData();
            return null;
        }


        public void setData(){
            mTitle.setText(getIntent().getStringExtra("session_name"));
            mSpeaker.setText(mSpeakerObject.getName());
            mRoom.setText(getIntent().getStringExtra("session_room"));
            mTime.setText(getIntent().getStringExtra("session_time"));
            mDescription.setText(getIntent().getStringExtra("session_brief"));
            if(getIntent().getBooleanExtra("session_keynote",false)){
                isKeynote=true;
            }
            if(!isKeynote){
            mKeynoteButton.setVisibility(View.INVISIBLE);
            }


            mSessionModel = new sessions();
            mSessionModel.setBrief(getIntent().getStringExtra("session_brief"));
            mSessionModel.setId(getIntent().getLongExtra("session_id",-1));
            mSessionModel.setSpeaker(getIntent().getLongExtra("session_speaker",-1));
            mSessionModel.setRoom(getIntent().getStringExtra("session_room"));
            mSessionModel.setName(getIntent().getStringExtra("session_name"));
            mSessionModel.setTime(getIntent().getStringExtra("session_time"));
            mSessionModel.setKeynote(getIntent().getBooleanExtra("session_keynote",false));


        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(item.getItemId()==R.id.keynote){
//            Toast.makeText(getApplicationContext(),"The Speech is Keynote",Toast.LENGTH_SHORT).show();
//        }else if(item.getItemId()==R.id.add_to_schedule && !lockAddToSchedule){
//
//            if(check_Speach_Existion()){
//                Toast.makeText(getApplicationContext(),"You have add it already to your schedule.",Toast.LENGTH_SHORT).show();
//            }else{
//                int sizeBefore = main_screen.mScheduleSessions.size();
//                main_screen.mScheduleSessions.add(mSessionModel);
//                // create the dialog for activate the notifications
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Notification Activation")
//                        .setMessage("Do would want to get a notification reminder about this session?")
//                        .setPositiveButton("Yes", new OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                schedule_notifications.CreateNotificationForSession(mActivity, main_screen.mScheduleSessions.get(0));
//                            }
//                        })
//                        .setNegativeButton("No", new OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        }).show();
//                if (sizeBefore < main_screen.mScheduleSessions.size()) {
//                    Toast.makeText(getApplicationContext(), "The Session has been added to your schedule.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                }
//
//                lockAddToSchedule = !lockAddToSchedule;
//            }
//
//
//        }else if(item.getItemId()==R.id.add_to_schedule && lockAddToSchedule){
//            Toast.makeText(getApplicationContext(),"You have add it already to your schedule.",Toast.LENGTH_SHORT).show();
//        }else if(item.getItemId()==R.id.back_button_png){
//            this.finish();
//        }
//
//
       return true;
    }

    public boolean check_Speach_Existion(){
        int counter=0;
        while(counter!=main_screen.mScheduleSessions.size()){
            if(mSessionModel.getId()==main_screen.mScheduleSessions.get(counter).getId()){
                return true;
            }else{
                counter++;
            }
        }


        return false;
    }




    public void saveuserSessionPreference (sessions mSession) throws IOException {

            // Create an image file name
            File storageDir = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt");
            if(storageDir.exists()){
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt",true));
                out.append("\n"+mSession.getName());
                out.flush();
                out.close();
            }else{
                File mPreference  = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt");
                FileWriter writer = new FileWriter(mPreference);
                writer.append("\n"+mSession.getName());
                writer.flush();
                writer.close();
            }
    }




    public boolean checkUserSessionPreference(sessions mSessionName) {

        File theFile = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+ First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt");


    if(theFile.exists()){
        try {
            Log.e("SessionText" , "Step 1");
            instream = new FileInputStream(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+ First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputreader = new InputStreamReader(instream);
        BufferedReader buffreader = new BufferedReader(inputreader);

        try {
            String line = null;
            while ((line = buffreader.readLine()) !=null){
               if(line.contains(mSessionName.getName())){
                   return true;
               }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }else{
        return false;
    }

    }

    public boolean checkSesssionSaveOnText(sessions mSessionModel){
        simple_spinner.createSpinner(this,getApplicationContext());
        simple_spinner.ChangeText("Wait to save your session");
        while(!checkUserSessionPreference(mSessionModel)){
            if(checkUserSessionPreference(mSessionModel)){
                simple_spinner.destroyAlertDialog();
                return true;
            }
        }


        if(checkUserSessionPreference(mSessionModel)){
            simple_spinner.destroyAlertDialog();
            return true;
        }
        return false;
    }




}
