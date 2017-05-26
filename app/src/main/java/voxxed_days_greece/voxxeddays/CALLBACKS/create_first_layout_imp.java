package voxxed_days_greece.voxxeddays.CALLBACKS;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by James Nikolaidis on 5/24/2017.
 */

public class create_first_layout_imp implements create_first_layout {


    @Override
    public void updateFirstScreenRecyclerView(RecyclerView mgivenRecycler, Activity appActivity, Context appContext) {

            if(mgivenRecycler!=null){

            }else{ Log.i("VoxxedDays Error","Error Parse the Recycler View in the first screen!!");}



    }



}
