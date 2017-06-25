package voxxed_days_greece.voxxeddays.Screens;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("session_name"));
        mGet_sessions = get_sessions.get_sessionObject();
        mTitle = (TextView) findViewById(R.id.session_title);
        mDescription = (TextView) findViewById(R.id.session_brief);
        mSpeaker = (TextView) findViewById(R.id.session_speaker);
        mTime = (TextView) findViewById(R.id.session_time);
        mRoom = (TextView) findViewById(R.id.session_room);
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

        if(item.getItemId()==R.id.keynote){
            Toast.makeText(getApplicationContext(),"The Speech is Keynote",Toast.LENGTH_SHORT).show();
        }else if(item.getItemId()==R.id.add_to_schedule && !lockAddToSchedule){
            int sizeBefore = main_screen.mScheduleSessions.size();
            main_screen.mScheduleSessions.add(mSessionModel);
            schedule_notifications.CreateNotificationForSession(this,main_screen.mScheduleSessions.get(0));
            if(sizeBefore<main_screen.mScheduleSessions.size()){
                Toast.makeText(getApplicationContext(),"The Session has been added to your schedule.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }

            lockAddToSchedule=!lockAddToSchedule;
        }else if(item.getItemId()==R.id.add_to_schedule && lockAddToSchedule){
            Toast.makeText(getApplicationContext(),"You have add it already to your schedule.",Toast.LENGTH_SHORT).show();
        }


        return true;
    }







}
