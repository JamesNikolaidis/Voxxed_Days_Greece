package voxxed_days_greece.voxxeddays.Screens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.adapters.state_spinner;
import voxxed_days_greece.voxxeddays.api.get_speakers;

/**
 * Created by James Nikolaidis on 5/24/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class First_Screen extends AppCompatActivity {

    private  Intent nextScreen;
    private SimpleDateFormat DateFormat=null;
    private Date getCurrentDate=null;
    public static Activity mActivity=null;
    private SharedPreferences mSharedPreference=null;
    private SharedPreferences.Editor editor=null;
    private static ArrayList<Drawable> images = new ArrayList<>();
    public static String STATE_SELECTION="state_selection";
    public static String STATE_NUMBER="state_number";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_layout);
        ButterKnife.bind(this);
        getCurrentDate = new Date();
        mActivity = this;
        mSharedPreference = getSharedPreferences("state_selection",MODE_PRIVATE);
        editor = mSharedPreference.edit();

        BackgroundStaff backgroundStaff =new BackgroundStaff();
        backgroundStaff.execute();



    }



    @OnClick(R.id.StateSpinner)
    public void SpinnerTextViewClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.state_spinner_item);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ListView listView = (ListView) alertDialog.findViewById(R.id.SpinnerListView);
        listView.setAdapter(new state_spinner(getApplicationContext(), getResources().getStringArray(R.array.State_List), this, returnImagesId(this)));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("state_number", position);
                editor.commit();
                nextScreen = new Intent(getApplicationContext(), main_screen.class);
                startActivity(nextScreen);


            }
        });


    }



    public static ArrayList<Drawable> returnImagesId(Activity activity){

        images.add(activity.getDrawable(R.drawable.white_tower));
        images.add(activity.getDrawable(R.drawable.acropolis_ico));
        images.add(activity.getDrawable(R.drawable.volos3));
        images.add(activity.getDrawable(R.drawable.patra_ico));
        images.add(activity.getDrawable(R.drawable.crete_ico_v2));
        images.add(activity.getDrawable(R.drawable.piraeus_ico));
        return images;
    }




    public class BackgroundStaff extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            get_speakers speakers = get_speakers.get_speakersObject();
             speakers.getSpeakersListener();
            return null;
        }
    }



}
