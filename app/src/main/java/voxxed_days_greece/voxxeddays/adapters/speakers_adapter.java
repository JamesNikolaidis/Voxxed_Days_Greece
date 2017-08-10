package voxxed_days_greece.voxxeddays.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.api.get_storage_files;

/**
 * Created by James Nikolaidis on 6/8/2017.
 */

public class speakers_adapter extends ArrayAdapter<String> {

    private ImageView mImageView=null;
    private TextView  mTextView=null;
    private ArrayList<String> mSpeakerName=new ArrayList<>();
    private Activity mActivity;
    private Context mContext;

    public speakers_adapter(@NonNull Context context,ArrayList<String> speakersUrl,ArrayList<String> speakersName) {
        super(context,R.layout.speaker_item,speakersUrl);
        this.mSpeakerName = speakersName;
        mContext=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.speaker_item,parent,false);
        mTextView = (TextView)view.findViewById(R.id.speaker_name);
        mImageView = (ImageView)view.findViewById(R.id.speaker_picture);
        Log.e("data","Here");
        Drawable d = Drawable.createFromPath(get_storage_files.VOXXED_FOLDER+getItem(position)+".jpg");
        mImageView.setImageDrawable(d);
        mTextView.setText(mSpeakerName.get(position));

        return view;
    }




}
