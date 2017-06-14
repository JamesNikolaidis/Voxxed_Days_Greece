package voxxed_days_greece.voxxeddays.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by James Nikolaidis on 6/8/2017.
 */

public class initialize_database {


    private static DatabaseReference mFirebaseDatabase=null;

    public static DatabaseReference returnDatabaseObject(){
        if(mFirebaseDatabase==null){
            mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
            return mFirebaseDatabase;
        }else{
           return  mFirebaseDatabase;
        }
    }








}
