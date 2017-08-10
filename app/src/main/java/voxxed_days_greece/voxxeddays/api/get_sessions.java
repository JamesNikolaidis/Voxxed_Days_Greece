package voxxed_days_greece.voxxeddays.api;

import android.content.SharedPreferences;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.Screens.First_Screen;
import voxxed_days_greece.voxxeddays.models.sessions;
import voxxed_days_greece.voxxeddays.models.speakers;

/**
 * Created by James Nikolaidis on 6/16/2017.
 */

public class get_sessions {

    private DatabaseReference mFirebaseDatabase=null;
    private ArrayList<sessions> sessionList = null,mKeynotesSessions=null;
    private Query getSession=null;
    private static get_sessions get_sessions = null;
    private ChildEventListener mSessionEventListener=null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int counter=0;
    public static boolean downloadFinished=false;


    public  get_sessions(){
        this.mFirebaseDatabase = initialize_database.returnDatabaseObject();
        sessionList = new ArrayList<>();
        mKeynotesSessions = new ArrayList<>();
        mSessionEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue(sessions.class).isKeynote()){
                    mKeynotesSessions.add(dataSnapshot.getValue(sessions.class));
                    editor.putInt("CHANGE_COMMIT",2).commit();
                }else{
                    sessionList.add(dataSnapshot.getValue(sessions.class));
                    editor.putInt("CHANGE_COMMIT",2).commit();
                }


            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                while(sessionList.get(counter).id!=dataSnapshot.getValue(speakers.class).id){
                    counter++;
                }
                sessionList.remove(counter);
                counter=0;
                editor.putInt("CHANGE_COMMIT",2).commit();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        sharedPreferences= First_Screen.mActivity.getSharedPreferences(First_Screen.STATE_SELECTION,First_Screen.mActivity.MODE_PRIVATE);
        editor = sharedPreferences.edit();



    }


    public static get_sessions get_sessionObject(){

        if(get_sessions==null){
            get_sessions = new get_sessions();
            return get_sessions;
        }else{return  get_sessions;}
    }



    public void setSessionListener(String state,String year){
        getSession = FirebaseDatabase.getInstance().getReference().child(state).child(year).child("Sessions").orderByChild("id");
        getSession.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downloadFinished=!downloadFinished;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getSession.addChildEventListener(mSessionEventListener);

    }




    public ArrayList<sessions> returnSessionList(){
        if(!sessionList.isEmpty()){return sessionList;}else{return null;}
    }

    public ArrayList<sessions> returnKeynotesSessions(){
        if(!mKeynotesSessions.isEmpty()){return mKeynotesSessions;}else{return null;}
    }


    public  void clearSessionList(){
        mKeynotesSessions.clear();
        sessionList.clear();
        getSession.removeEventListener(mSessionEventListener);
    }





}
