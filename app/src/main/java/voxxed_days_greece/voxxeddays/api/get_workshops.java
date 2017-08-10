package voxxed_days_greece.voxxeddays.api;

import android.content.SharedPreferences;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.Screens.First_Screen;
import voxxed_days_greece.voxxeddays.models.workshops;

/**
 * Created by James Nikolaidis on 7/10/2017.
 */

public class get_workshops {

    private static get_workshops mget_workshop_object=null;
    private  ArrayList<workshops> mWorkshopsList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public boolean hasMoreDays= false;
    public int WorkshopDaysCounter = 0;
    public  static  boolean downloadFinished=false;

    public  get_workshops(){
        mWorkshopsList = new ArrayList<>();
        sharedPreferences= First_Screen.mActivity.getSharedPreferences(First_Screen.STATE_SELECTION,First_Screen.mActivity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void fetchData(String state,String year){
        Query mFetchWorkshops = FirebaseDatabase.getInstance().getReference().child(state).child(year).child("Workshops").orderByChild("workshop_id");


        mFetchWorkshops.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downloadFinished=!downloadFinished;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mFetchWorkshops.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mWorkshopsList.add(dataSnapshot.getValue(workshops.class));
                editor.putInt("CHANGE_COMMIT",3).commit();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



    public void clearArray(){
        if(mWorkshopsList!=null){this.mWorkshopsList.clear();}

    }

    public ArrayList<workshops> returnWorkShopsList(){
        return  mWorkshopsList;
    }


    public static get_workshops getWorkShopObject(){

        if(mget_workshop_object==null){
            mget_workshop_object = new get_workshops();
            return mget_workshop_object;
        }else{return  mget_workshop_object;}
    }


    public int  CheckTheCountOfDates(){
        int maxnumber =0;
        for (workshops mworkshops:mWorkshopsList) {
                 maxnumber = mworkshops.day>maxnumber ? mworkshops.day : maxnumber;
                 this.hasMoreDays =!hasMoreDays;
        }
        return this.WorkshopDaysCounter = maxnumber;
    }





}
