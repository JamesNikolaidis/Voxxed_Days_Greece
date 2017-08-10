package voxxed_days_greece.voxxeddays.general;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import voxxed_days_greece.voxxeddays.R;

import static voxxed_days_greece.voxxeddays.Screens.main_screen.StopTime;

/**
 * Created by James Nikolaidis on 6/19/2017.
 */

public class delete_content {


    public static  void DestroyFlipperAndTimer(LinearLayout mRelative,View speaker,Activity mActivity){
        mRelative.removeAllViews();
        mRelative.setBackgroundColor(Color.parseColor("#867979"));
        LayoutInflater inflater1 = LayoutInflater.from(mActivity);
        speaker = inflater1.inflate(R.layout.speakers,null);
        mRelative.addView((LinearLayout)speaker.findViewById(R.id.speakers_linear_layout));
        StopTime();
    }
    public static void DestroyFlipperAndTimerForSession(int id,int layout,LinearLayout mRelative,Activity mActivity){
        mRelative.removeAllViews();
        LayoutInflater inflater1 = LayoutInflater.from(mActivity);
        mRelative.setBackgroundColor(Color.parseColor("#FFFFFF"));
        View speaker = inflater1.inflate(layout,null);
        mRelative.addView((LinearLayout)speaker.findViewById(id));
        StopTime();
    }
    public static void DestroyFlipperAndTimerForSession_vol2(int id,int layout,LinearLayout mRelative,Activity mActivity){
        mRelative.removeAllViews();
        LayoutInflater inflater1 = LayoutInflater.from(mActivity);
        mRelative.setBackgroundColor(Color.parseColor("#FFFFFF"));
        View speaker = inflater1.inflate(layout,null);
        mRelative.addView((LinearLayout)speaker.findViewById(id));
        StopTime();
    }


}
