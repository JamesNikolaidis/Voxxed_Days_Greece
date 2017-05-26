package voxxed_days_greece.voxxeddays.Screens;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import voxxed_days_greece.voxxeddays.ALERT_DIALOGS.PassedAthensDate;
import voxxed_days_greece.voxxeddays.R;

/**
 * Created by James Nikolaidis on 5/24/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class First_Screen extends AppCompatActivity {

    @BindView(R.id.VoxxedDaysAthens)
    Button mVoxxedAthensButton;

    @BindView(R.id.VoxxedDaysThessaloniki)
    Button mVoxxedThessalonikiButton;


    private  Intent nextScreen;
    private SimpleDateFormat DateFormat=null;
    private Date getCurrentDate=null;
    private GregorianCalendar gregorianCalendar;

    Time today;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_layout);

        ButterKnife.bind(this);

        getCurrentDate = new Date();
        gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/Athens"));
        today= new Time(Time.getCurrentTimezone());
        today.setToNow();
    }


    //create on click listener for the button on this layout
    @OnClick({R.id.VoxxedDaysAthens,R.id.VoxxedDaysThessaloniki})
    public void sent_on_the_voxxed_days_screens(View view){


            //check if the clicked button is the thessaloniki button
            if(view.getId()==R.id.VoxxedDaysThessaloniki){
                     nextScreen  = new Intent(this,thessaloniki_main_screen.class);
                     this.startActivity(nextScreen);
            }else{
                //check if the clicked button is the Athens button
                        //check if its in the Athens Voxxed Period or else it will show a dialog to choose past events
                     if(getCurrentDate.getMonth()==5){
                         nextScreen  = new Intent(this,athens_main_screen.class);
                         this.startActivity(nextScreen);
                     }else{

                         PassedAthensDate athens_main_screen = new PassedAthensDate(getApplicationContext());
                         athens_main_screen.createDialog(getApplicationContext(),this,"");
                     }
            }

    }







}
