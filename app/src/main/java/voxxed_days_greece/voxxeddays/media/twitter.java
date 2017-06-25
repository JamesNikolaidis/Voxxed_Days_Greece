package voxxed_days_greece.voxxeddays.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.Screens.main_screen;
import voxxed_days_greece.voxxeddays.alert_dialogs.simple_spinner;
import voxxed_days_greece.voxxeddays.general.delete_content;

/**
 * Created by James Nikolaidis on 6/19/2017.
 */

public class twitter {

        private Activity mActivity=null;
        private EditText tweetText=null;
        private Uri tweet_image_uri=null;
        private Context mContext=null;



    public twitter(Activity activity,EditText editText,Context context){
        this.mActivity = activity;
        this.tweetText = editText;
        this.mContext = context;

    }

    public void setOnPostTweetListener(Button button, final LinearLayout layout){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                final Intent intent = new ComposerActivity.Builder(mActivity)
                        .session(session)
                        .text(tweetText.getText().toString())
                        .image(main_screen.tweet_image_uri)
                        .hashtags("#vdthess")
                        .createIntent();
                simple_spinner.createSpinner(mActivity,mContext);
                mActivity.startActivity(intent);
                delete_content.DestroyFlipperAndTimerForSession(R.id.main_screen_relative,R.layout.content_main,layout,mActivity);
                mActivity.setTitle(main_screen.APPLICATION_TITLE);

            }
        });
    }


    public void SetTwitterLoginButtonCallback(TwitterLoginButton twitterLoginButton, final Context mContext){
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(mContext,"Login has been completed",Toast.LENGTH_SHORT).show();
            }@Override
            public void failure(TwitterException exception) {
                Toast.makeText(mContext,"Error: Login Failed.Please restart the application",Toast.LENGTH_SHORT).show();
            }
        });

    }











}
