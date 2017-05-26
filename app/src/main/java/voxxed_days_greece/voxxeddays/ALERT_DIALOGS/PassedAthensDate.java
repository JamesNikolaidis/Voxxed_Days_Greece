package voxxed_days_greece.voxxeddays.ALERT_DIALOGS;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import voxxed_days_greece.voxxeddays.R;

/**
 * Created by James Nikolaidis on 5/25/2017.
 */

public class PassedAthensDate extends AlertDialog {

    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog mDialog;

    public  PassedAthensDate(@NonNull Context context) {
        super(context);
    }

    public void createDialog(Context mContext, Activity mActivity , String message){
         mDialogBuilder = new AlertDialog.Builder(mActivity);
         mDialog = mDialogBuilder.create();
         mDialog.show();
         mDialog.setContentView(R.layout.athens_alert_dialog);



    }



}
