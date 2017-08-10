package voxxed_days_greece.voxxeddays.alert_dialogs;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import voxxed_days_greece.voxxeddays.R;

/**
 * Created by James Nikolaidis on 6/8/2017.
 */

public class simple_spinner {

        private static AlertDialog alertDialog=null;
        private static TextView mSpinnerText=null;


    public static void createSpinner(Activity activity, Context context){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
       // alertDialog.setCancelable(false);
        alertDialog.setContentView(R.layout.spinner_layout);
        mSpinnerText = (TextView)alertDialog.findViewById(R.id.textView);


    }

    public static void ChangeText(String text){
      mSpinnerText.setText(text);
    }


    public static void destroyAlertDialog(){
        alertDialog.dismiss();
    }

}
