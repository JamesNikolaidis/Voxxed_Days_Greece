package voxxed_days_greece.voxxeddays.alert_dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;


/**
 * Created by James Nikolaidis on 6/17/2017.
 */

public class media_chooser {

    private static AlertDialog alertDialog=null;
    private static AlertDialog.Builder mBuilder;
    private static int user_choice =0;


    public static int create_media_dialog(Activity mActivity){
        mBuilder = new AlertDialog.Builder(mActivity);
        mBuilder.setTitle("Which method of impoer do you want?");
        mBuilder.setItems(new String[]{"From Camera?","From Storage?"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    user_choice=which;

                    DestroyDialog();

            }
        });
       return 0;
    }


    public static void DestroyDialog(){
        alertDialog.cancel();
    }

}
