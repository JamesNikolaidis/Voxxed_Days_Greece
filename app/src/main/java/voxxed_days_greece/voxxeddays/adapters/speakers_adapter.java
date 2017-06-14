package voxxed_days_greece.voxxeddays.adapters;

import android.app.Activity;
import android.content.Context;
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
        int id = mContext.getResources().getIdentifier(getItem(position), "drawable", mContext.getPackageName());
        mImageView.setImageResource(id);
        mTextView.setText(mSpeakerName.get(position));

        return view;
    }
}
