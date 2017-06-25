package voxxed_days_greece.voxxeddays.media;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

import static voxxed_days_greece.voxxeddays.Screens.main_screen.mImageBitmap;

/**
 * Created by James Nikolaidis on 6/19/2017.
 */

public class facebook {

    private Activity mActivity=null;
    private Context context;

    public facebook(Activity activity){
        this.mActivity = activity;
    }



    public void facebook_share(Button button) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharePhoto sharePhoto1 = new SharePhoto.Builder()
                        .setBitmap(mImageBitmap)
                        .build();
                ShareContent shareContent = new ShareMediaContent.Builder()
                        .addMedium(sharePhoto1)
                        .setShareHashtag(new ShareHashtag.Builder().setHashtag("#vdthess").build())
                        .build();
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);

            }
        });





    }


    public void SetLoginManagerLoginCallback(CallbackManager callbackManager, final Context mContext){
        context = mContext;
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(mContext,"Login has been completed"+loginResult.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }


    public void SetLoginButtonCallback(LoginButton loginButton,CallbackManager callbackManager){
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(context,"Login has been completed"+loginResult.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
            }
        });



    }



}
