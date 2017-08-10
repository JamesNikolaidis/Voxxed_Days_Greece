package voxxed_days_greece.voxxeddays.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.api.get_speakers;
import voxxed_days_greece.voxxeddays.api.get_storage_files;
import voxxed_days_greece.voxxeddays.models.workshops;

/**
 * Created by James Nikolaidis on 7/10/2017.
 */

public class mWorkshopAdapter extends ArrayAdapter<workshops> {
    TextView mWorkshopName=null,mWorkshopTime=null,mWorkshopRoom=null,mWorkshopSpeaker=null,mWorkshopValue=null,mWorkshopBrief=null;
    ImageView mWorkshopImage = null;
    SharedPreferences sharedPreferences=null;
    SharedPreferences.Editor mEditor = null;


    public mWorkshopAdapter(@NonNull Context context, ArrayList<workshops> mWorkshops, SharedPreferences.Editor Editor) {
        super(context,R.layout.workshops_adapter_item,mWorkshops);
        this.mEditor = Editor;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mView= LayoutInflater.from(getContext()).inflate(R.layout.workshops_adapter_item,parent,false);

        mWorkshopName = (TextView) mView.findViewById(R.id.workshop_name);
        mWorkshopTime = (TextView)mView.findViewById(R.id.my_workshop_time);
        mWorkshopRoom = (TextView)mView.findViewById(R.id.my_workshop_room);
        mWorkshopSpeaker = (TextView)mView.findViewById(R.id.my_teacher);
        mWorkshopValue = (TextView)mView.findViewById(R.id.m_workshop_value);
        mWorkshopBrief = (TextView) mView.findViewById(R.id.workshop_brief);
        mWorkshopImage =(ImageView) mView.findViewById(R.id.my_workshop_speaker_photo);
        Drawable d = Drawable.createFromPath(get_storage_files.VOXXED_FOLDER+getItem(position).getWorkshop_photo_url()+".jpg");
        mWorkshopImage.setImageDrawable(d);
        mWorkshopName.setText(getItem(position).getWorkshop_name());
        mWorkshopTime.setText(getItem(position).getWorkshop_time());
        mWorkshopRoom.setText(getItem(position).getWorkshop_room());
        mWorkshopValue.setText(getItem(position).getValue());


//        mWorkshopBrief.setText(getItem(position).getWorkshop_brief());
        mWorkshopSpeaker.setText(get_speakers.get_speakersObject().getSpeakerById(getItem(position).getSpeaker()).getName());
        mEditor.putString(getItem(position).getWorkshop_name()+mWorkshopSpeaker.getText().toString(),getItem(position).getWorkshop_brief()).commit();
        return mView;
    }
}
