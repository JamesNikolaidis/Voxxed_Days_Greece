package voxxed_days_greece.voxxeddays.alert_dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import voxxed_days_greece.voxxeddays.R;

/**
 * Created by James Nikolaidis on 6/17/2017.
 */

public class twitter_dialog {

    private static AlertDialog twitterLoginDialog=null,twitterPostDialog=null;
    private static AlertDialog.Builder mBuilder =null;
    private static TwitterLoginButton twitterLoginButton=null;

    public static void create_login_twitter_dialog(Activity mActivity){
        mBuilder = new AlertDialog.Builder(mActivity);
        twitterLoginDialog = mBuilder.create();
        twitterLoginDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        twitterLoginDialog.setContentView(R.layout.twitter_dialog);
        twitterLoginDialog.show();
        twitterLoginButton = (TwitterLoginButton)twitterLoginDialog.findViewById(R.id.twitter_login);

        twitterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("KKKK","Clicked");
            }
        });


        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.e("KKKK","Success");
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("KKKK","failure");
            }
        });



    }


    public static void destroy_twitter_login_dialog(){
        twitterLoginDialog.cancel();
    }


}
