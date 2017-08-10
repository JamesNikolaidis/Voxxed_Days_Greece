package voxxed_days_greece.voxxeddays.alert_dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.api.create_question_node;
import voxxed_days_greece.voxxeddays.api.get_sessions;
import voxxed_days_greece.voxxeddays.models.sessions;

import static voxxed_days_greece.voxxeddays.Screens.First_Screen.STATE;
import static voxxed_days_greece.voxxeddays.Screens.First_Screen.YEAR;

/**
 * Created by James Nikolaidis on 7/28/2017.
 */

public class question_dialog  {
    public static AlertDialog mQuestionDialog = null;
    private Button mBackButton=null,mSendQuestion=null;
    private Spinner mSpeachSpinner = null;
    private  EditText mQuestionEditText =null;
    private  get_sessions mSessions = null;
    private  ArrayList<sessions> mSessionObjects= null;
    private ArrayList<String> mSessionsList=null;
    private String mSpinnerOption =null;
    private static Context mApplicationContext = null;
    private static Timer mTimer;
    private static int counter= 0;

    public question_dialog(){
        mSessionsList = new ArrayList<String>();
        mSessions = get_sessions.get_sessionObject();
        mSessionObjects = mSessions.returnSessionList();
    }



    public  void createQuestionDialog(final Activity mActivity , final Context mContext){
        mApplicationContext = mContext;
       AlertDialog.Builder mBuilder =  new AlertDialog.Builder(new ContextThemeWrapper(mActivity,R.style.AlertDialogCustom));
       //fill the Session list
       for(sessions mSessionDummy : mSessionObjects){
           mSessionsList.add(mSessionDummy.getName());
       }
       mQuestionDialog = mBuilder.create();
       mQuestionDialog.getWindow().getAttributes().windowAnimations = R.style.question_panel_anime;
       mQuestionDialog.show();
       mQuestionDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
       mQuestionDialog.setTitle("Set your Question");
       mQuestionDialog.setContentView(R.layout.question_activity);
        mQuestionDialog.setCancelable(false);
        mQuestionEditText = (EditText)mQuestionDialog.findViewById(R.id.mQuestionBody);
        mBackButton = (Button) mQuestionDialog.findViewById(R.id.questionBackBtn);
        mSendQuestion = (Button) mQuestionDialog.findViewById(R.id.mSendQuestionBtn);
       //set back button listener
       mBackButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mQuestionDialog.cancel();
           }
       });

       //set send question click listener
       mSendQuestion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mActivity.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       simple_spinner.createSpinner(mActivity,mContext);
                       simple_spinner.ChangeText("Send your question...");
                   }
               });
               if(!mQuestionEditText.getText().toString().matches("")){
                   mTimer = new Timer();
                   mTimer.schedule(new TimerTask() {
                       @Override
                       public void run() {
                           Log.e("TIMER",String.valueOf(counter));
                           if(counter==10){
                               mActivity.runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(mContext,"Because of the low internet your question saved localy.If you establish internet connection then it will store in the server.",Toast.LENGTH_LONG).show();
                                       simple_spinner.destroyAlertDialog();
                                       mTimer.cancel();
                                       counter = 0;
                                   }
                               });
                           }else{counter++;}

                       }
                   },0,1000);
                   create_question_node.check_and_create_question_node(YEAR,STATE,mQuestionEditText.getText().toString(),mSpinnerOption);
               }else{
                   Toast.makeText(mContext,"You must set a question before click send button!",Toast.LENGTH_SHORT).show();
               }
           }


       });


        mSpeachSpinner = (Spinner) mQuestionDialog.findViewById(R.id.mQuestionSpeachSpinner);
        mSpeachSpinner.setAdapter(new ArrayAdapter<String>(mContext,R.layout.spinner_text_view,mSessionsList));
        mSpeachSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerOption = mSessionsList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






    }

    public static void destroyDialog(){
        mQuestionDialog.cancel();
    }

    public static void  question_callback(){
        simple_spinner.destroyAlertDialog();
        mQuestionDialog.cancel();
        mTimer.cancel();
        counter=0;
        Toast.makeText(mApplicationContext,"Your question has been send.",Toast.LENGTH_SHORT).show();

    }






}
