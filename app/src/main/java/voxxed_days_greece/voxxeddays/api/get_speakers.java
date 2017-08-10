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
import voxxed_days_greece.voxxeddays.models.speakers;

/**
 * Created by James Nikolaidis on 6/8/2017.
 */

public class get_speakers {

    private DatabaseReference mFirebaseDatabase=null;
    private ArrayList<speakers> mSpeakersList = null;
    private Query getSpeakers=null,getSpeakersPhoto=null;
    private static get_speakers get_speakers = null;
    private ChildEventListener mSpeakersEventListener=null,mSessionEventListener=null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int counter=0;
    public static boolean downloadFinished=false;
    private String SpeakerPhoto=null;
    private boolean photo_founde_flag=false;

    public  get_speakers(){
        this.mFirebaseDatabase = initialize_database.returnDatabaseObject();
        mSpeakersList = new ArrayList<>();
        mSpeakersEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mSpeakersList.add(dataSnapshot.getValue(speakers.class));
                editor.putInt("CHANGE_COMMIT",1).commit();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                while(mSpeakersList.get(counter).id!=dataSnapshot.getValue(speakers.class).id){
                    counter++;
                }
                mSpeakersList.remove(counter);
                mSpeakersList.add(counter,dataSnapshot.getValue(speakers.class));
                counter=0;
                editor.putInt("CHANGE_COMMIT",1).commit();
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                while(mSpeakersList.get(counter).id!=dataSnapshot.getValue(speakers.class).id){
                    counter++;
                }
                mSpeakersList.remove(counter);
                counter=0;
                editor.putInt("CHANGE_COMMIT",1).commit();
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


    public static get_speakers get_speakersObject(){

        if(get_speakers==null){
            get_speakers = new get_speakers();
            return get_speakers;
        }else{return  get_speakers;}
    }

    public void getSpeakersListener(String state,String year){
        getSpeakers = FirebaseDatabase.getInstance().getReference().child(state).child(year).child("Speakers").orderByChild("id");
        getSpeakers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              downloadFinished=!downloadFinished;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getSpeakers.addChildEventListener(mSpeakersEventListener);

    }



    public ArrayList<speakers> returnSpeakersList(){
        if(!mSpeakersList.isEmpty()){return mSpeakersList;}else{return null;}
    }
    public  void clearSpeakersList(){
        mSpeakersList.clear();
        getSpeakers.removeEventListener(mSpeakersEventListener);
    }



    public String ReturnSpeakerUrl(long id){
        int counter=0;
        while(counter!=mSpeakersList.size()){
            if(mSpeakersList.get(counter).id==id){
                return mSpeakersList.get(counter).getPhoto_url();
            }else{
                counter++;
            }
        }

        return null;
    }


    public speakers getSpeakerById(int id){
        int counter=0;
        while(counter!=mSpeakersList.size()){
            if(mSpeakersList.get(counter).id==id){
                return mSpeakersList.get(counter);
            }else{
                counter++;
            }
        }

        return null;
    }



}
