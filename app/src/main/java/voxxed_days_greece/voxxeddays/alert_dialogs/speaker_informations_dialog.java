package voxxed_days_greece.voxxeddays.alert_dialogs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.api.get_speakers;
import voxxed_days_greece.voxxeddays.api.get_storage_files;
import voxxed_days_greece.voxxeddays.models.speakers;

/**
 * Created by James Nikolaidis on 6/15/2017.
 */

public class speaker_informations_dialog {
    private static AlertDialog speaker_alert_dialog=null;
    private static AlertDialog.Builder mBuilder=null;
    private static speakers mSpeaker=null;
    private static ArrayList<speakers> mSpeakers=null,mSpeakerArray=null;
    private static get_speakers mGetSpeakers=null;
    private static ImageView mSpeaker_image=null;
    private static TextView  mSpeakerName=null,mSpeakerBio=null,mSpeakerSpeach=null;


    public static void create_speaker_dialog(Context context, Activity activity,String SpeakerName){
            mBuilder = new AlertDialog.Builder(activity);
            speaker_alert_dialog = mBuilder.create();
            mSpeakers = new ArrayList<>();
            mGetSpeakers = get_speakers.get_speakersObject();
            mSpeakers = mGetSpeakers.returnSpeakersList();
            mSpeaker= getSpeakerObject(mSpeakers,SpeakerName);
            speaker_alert_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            speaker_alert_dialog.show();
            speaker_alert_dialog.setContentView(R.layout.speaker_profile);
            //speaker_alert_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mSpeaker_image = (ImageView)speaker_alert_dialog.findViewById(R.id.speaker_profile_image);
            mSpeakerName = (TextView) speaker_alert_dialog.findViewById(R.id.speaker_name);
            mSpeakerBio = (TextView) speaker_alert_dialog.findViewById(R.id.speaker_biography);
            mSpeakerSpeach = (TextView) speaker_alert_dialog.findViewById(R.id.speaker_speach);
            Drawable d = Drawable.createFromPath(get_storage_files.VOXXED_FOLDER+mSpeaker.getPhoto_url()+".jpg");
            mSpeaker_image.setImageDrawable(d);
            mSpeakerName.setText(mSpeaker.getName());
            mSpeakerBio.setText(mSpeaker.getBio());
            mSpeakerSpeach.setPaintFlags(mSpeakerSpeach.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            mSpeakerSpeach.setText(mSpeaker.getTalk());

    }

    public static speakers getSpeakerObject(ArrayList<speakers> the_speaker,String speaker_name){
        int counter=0;

        do{
            Log.e("THENAME",(the_speaker.get(counter).getName()));
            if(the_speaker.get(counter).getName().matches(speaker_name)){

                return the_speaker.get(counter);
            }else{counter++;}

        }while(counter!=the_speaker.size());

        return null;
    }



}
