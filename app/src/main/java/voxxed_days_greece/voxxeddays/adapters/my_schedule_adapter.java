package voxxed_days_greece.voxxeddays.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.Screens.First_Screen;
import voxxed_days_greece.voxxeddays.Screens.main_screen;
import voxxed_days_greece.voxxeddays.api.get_storage_files;
import voxxed_days_greece.voxxeddays.models.sessions;

/**
 * Created by James Nikolaidis on 7/2/2017.
 */

public class my_schedule_adapter extends ArrayAdapter{

    private ArrayList<String> myScheduleSpeakerImage=null;
    private Context mContext;
    private ImageView mSpeakerUrl;
    private TextView mTitle=null,mTime=null,mRoom=null;
    private ArrayList<sessions> mSessions =null;
    private static int counter=0;
    private Button mDeleteScheduledSession = null;
    private InputStream instream;
    private OutputStreamWriter out;
    private String mString = new String();

    public my_schedule_adapter(@NonNull Context context, ArrayList<String> speakersUrl,ArrayList<sessions> Sessions) {
        super(context, R.layout.my_schedule_item,speakersUrl);
        this.myScheduleSpeakerImage = speakersUrl;
        this.mContext = context;
        mSessions = Sessions;

    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.my_schedule_item,parent,false);
        mSpeakerUrl = (ImageView) mView.findViewById(R.id.my_schedule_speaker_photo);
        mTitle = (TextView) mView.findViewById(R.id.session_name_my_schedule);
        mTime = (TextView) mView.findViewById(R.id.my_schedule_time);
        mRoom = (TextView) mView.findViewById(R.id.my_schedule_room);
        mDeleteScheduledSession = (Button) mView.findViewById(R.id.delete_scheduled_session_btn);
        //set speaker image
        Drawable d = Drawable.createFromPath(get_storage_files.VOXXED_FOLDER+getItem(position)+".jpg");
        mSpeakerUrl.setImageDrawable(d);
        //set title
        mTitle.setText(mSessions.get(position).getName());
        //set time
        mTime.setText(mSessions.get(position).getTime());
        //set room
        mRoom.setText(mSessions.get(position).getRoom());


        mDeleteScheduledSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContent(mSessions.get(position));
                main_screen.makeStaff();
            }
        });




        return mView;
    }


    public void deleteContent(sessions mSessionName) {

        File theFile = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+ First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt");

        if(theFile.exists()){
            try {
                instream = new FileInputStream(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+ First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            try {
                out = new OutputStreamWriter(new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt",true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                String line = null;
                int counter =0;
                while ((line = buffreader.readLine()) !=null){
                    counter++;
                    if(!line.matches(mSessionName.getName())){
                        mString+="\n"+line;
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }



        theFile.delete();
        try {
            theFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File storageDir = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt");

        if(storageDir.exists()){
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+ First_Screen.STATE+"/"+First_Screen.YEAR+"/userPreference.txt",true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                out.write(mString.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }



    }


}
