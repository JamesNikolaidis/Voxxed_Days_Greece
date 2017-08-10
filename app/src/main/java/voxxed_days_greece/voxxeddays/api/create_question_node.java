package voxxed_days_greece.voxxeddays.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import voxxed_days_greece.voxxeddays.alert_dialogs.question_dialog;
import voxxed_days_greece.voxxeddays.models.question;

/**
 * Created by James Nikolaidis on 8/5/2017.
 */

public class create_question_node {

    private static DatabaseReference mFirebaseDatabase=null;
    private static Task<Void> mQuestionTask = null;
    public static boolean post_successful = false;
    private static  String mTime =null;

    public static void check_and_create_question_node(final String YEAR, final String STATE,final String Question,final String speach){

        mFirebaseDatabase = initialize_database.returnDatabaseObject();
        mTime  = String.valueOf(new GregorianCalendar(TimeZone.getDefault()).get(Calendar.HOUR_OF_DAY))+ ":"+String.valueOf(new GregorianCalendar(TimeZone.getDefault()).get(Calendar.MINUTE));

        mFirebaseDatabase.child(STATE).child(YEAR).child("Question_Panel").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                if(dataSnapshot.getValue()==null){
                     mFirebaseDatabase.child(STATE).child(YEAR).child("Question_Panel").push().setValue(new question(Question,mTime,speach));


                }else{
                     mFirebaseDatabase.child(STATE).child(YEAR).child("Question_Panel").push().setValue(new question(Question,mTime,speach));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mFirebaseDatabase.child(STATE).child(YEAR).child("Question_Panel").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue(question.class).mQuestionTime.matches(mTime)&&dataSnapshot.getValue(question.class).mQuestion.matches(Question)){
                    question_dialog.question_callback();
                }



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






//
    }



}
