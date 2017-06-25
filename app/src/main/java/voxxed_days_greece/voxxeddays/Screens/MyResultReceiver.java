package voxxed_days_greece.voxxeddays.Screens;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

import voxxed_days_greece.voxxeddays.alert_dialogs.simple_spinner;

/**
 * Created by James Nikolaidis on 6/19/2017.
 */
public class MyResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success
            final Long tweetId =intent.getExtras().getLong(TweetUploadService.EXTRA_TWEET_ID);
            Log.e("DDDDD","Success");
            simple_spinner.destroyAlertDialog();
            Toast.makeText(context,"The twitter has been posted successfully!",Toast.LENGTH_SHORT).show();
        } else {
            // failure
            final Intent retryIntent = intent.getExtras().getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
        }
    }
}