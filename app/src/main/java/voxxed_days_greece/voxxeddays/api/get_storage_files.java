package voxxed_days_greece.voxxeddays.api;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by James Nikolaidis on 6/17/2017.
 */

public class get_storage_files {

    private StorageReference mStorageObject=null,mSpeakerPictures=null;
    private static get_storage_files storage_files = null;
    private  Bitmap bitmap;
    private  URL Url = null;
    private File localFile=null;
    private  InputStream inputStream;
    private HttpsURLConnection http;
    private static Activity activity;
    private static boolean mkDir = false;
    public static String VOXXED_FOLDER;
    private final static  File check = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece");
    public  static boolean FILE_EXIST = false;



    public static get_storage_files returnStorageObject(Activity mActivity,String State,String Year){
        activity = mActivity;




        if(storage_files==null && !check.exists()){
            File createVoxxeDays = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece");
            mkDir=createVoxxeDays.mkdir();
            createVoxxeDays = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+State);
            createVoxxeDays.mkdir();
            createVoxxeDays = new File(Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+State+"/"+Year);
            createVoxxeDays.mkdir();
            VOXXED_FOLDER = Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+State+"/"+Year+"/";
            storage_files= new get_storage_files();
            return storage_files;
        }else if(storage_files==null && check.exists()){
            VOXXED_FOLDER =  Environment.getExternalStorageDirectory().getPath()+"/VoxxedGreece/"+State+"/"+Year+"/";
            storage_files= new get_storage_files();
            FILE_EXIST = true;
            return  storage_files;
        }else{
            return  storage_files;
        }




    }

    public get_storage_files(){mStorageObject = FirebaseStorage.getInstance().getReference();

    }

    public void  getSpeakersPictures(final String State , final String Year, final String name) throws IOException {

            mSpeakerPictures = FirebaseStorage.getInstance().getReference().child(State).child(Year).child("Speakers").child(name);
            final long BYTES = 1024 * 1024;
            if (mkDir) {
                mSpeakerPictures.getBytes(BYTES).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                    @Override
                    public void onComplete(@NonNull Task<byte[]> task) {
                        byte[] picturesBytes = task.getResult();
                        String file_path = VOXXED_FOLDER + name;
                        File speaker_picture = new File(file_path);
                        BufferedOutputStream bufferedWriter = null;
                        try {
                            bufferedWriter = new BufferedOutputStream(new FileOutputStream(speaker_picture));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            bufferedWriter.write(picturesBytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            bufferedWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        }



}
