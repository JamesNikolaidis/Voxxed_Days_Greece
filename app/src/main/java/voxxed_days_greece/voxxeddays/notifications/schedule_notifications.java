package voxxed_days_greece.voxxeddays.notifications;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.Screens.NotificationPublisher;
import voxxed_days_greece.voxxeddays.models.sessions;

/**
 * Created by James Nikolaidis on 6/25/2017.
 */

public class schedule_notifications{

    private static NotificationCompat.Builder mNotificationBuilder = null;
    private static Notification  mNotificationObject = null;



    public static void CreateNotificationForSession(Activity mActivity, sessions mSessions){
        scheduleNotification(getNotification(mSessions,mActivity),5000,mActivity,mSessions);



    }


    private static void scheduleNotification(Notification notification, int delay,Activity mActivity,sessions mSession) {

        String[] mSessionTime = mSession.time.split(":");
        Intent notificationIntent = new Intent(mActivity, NotificationPublisher.class);

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mActivity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager)mActivity.getSystemService(Context.ALARM_SERVICE);
        Calendar  myAlarmDate = Calendar.getInstance();
        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/Athens"));
       // Log.e("fdfdfs",String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
       // myAlarmDate.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND)+30);
       if(Integer.valueOf(mSessionTime[1])-10>0){
            myAlarmDate.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),Integer.valueOf(mSessionTime[0]),Integer.valueOf(mSessionTime[1])-4,calendar.get(Calendar.SECOND));
        }else{
            myAlarmDate.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),Integer.valueOf(mSessionTime[0])-1,50,calendar.get(Calendar.SECOND));
        }



        alarmManager.set(AlarmManager.RTC_WAKEUP,myAlarmDate.getTimeInMillis(), pendingIntent);




    }




    private static Notification getNotification(sessions mSesion,Activity mActivity) {

        Notification.Builder builder = new Notification.Builder(mActivity);

        builder.setContentTitle("Session Notification");

        builder.setContentText("The '" + mSesion.getName() +"' session , start in : 10 minutes in Room: " +mSesion.getRoom());

        builder.setSmallIcon(R.drawable.voxxed_logo);
        builder.setStyle(new Notification.BigTextStyle().bigText("The " + mSesion.getName() +" start in : 10 minutes in Room: " +mSesion.getRoom()));


        return builder.build();

    }


}
