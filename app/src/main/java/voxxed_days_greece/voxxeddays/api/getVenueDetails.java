package voxxed_days_greece.voxxeddays.api;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import voxxed_days_greece.voxxeddays.models.venue;

/**
 * Created by James Nikolaidis on 7/9/2017.
 */

public class getVenueDetails {

    private DatabaseReference mFirebaseDatabase=null;
    private static  getVenueDetails mGetVenuObject = null;
    private static venue mVenueObject=null;
    private  getVenueDetails(){


    }


    public void fetchVenueData(String state,String year){
        Query mVenueQuery = FirebaseDatabase.getInstance().getReference().child(state).child(year).child("Venue");
        mVenueQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mVenueObject = dataSnapshot.getValue(venue.class);
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


    public static  getVenueDetails getVenuObject(){
        if(mGetVenuObject==null){
            return new getVenueDetails();
        }else{return  mGetVenuObject;}
    }

    public venue returnVenueObject(){ if(mVenueObject!=null){return  mVenueObject;}else{return  null;}}


}
