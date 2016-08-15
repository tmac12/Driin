package tmac12.it.driin;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by marco on 15/08/16.
 */
public class DriinBootReceiver extends BroadcastReceiver
{
    private static final String TAG = "MyBootReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Calendar cal = this.getMyCalendar();
        this.scheduleAlarms(context, cal);
    }

    private Calendar getMyCalendar() {
        // get your calendar object
        Calendar cal = Calendar.getInstance();
        return cal;
    }

    private void scheduleAlarms(Context ctxt, Calendar c) {
        scheduleNotificationSpecificTime(ctxt,getNotification(ctxt,"ook"));

        /*
        AlarmManager alarManager = (AlarmManager) ctxt.getSystemService(Context.ALARM_SERVICE);
        //notification servise
        Intent i = new Intent(ctxt, ScheduledService.class);
        i.putExtra(ALARM_ID, 1);
        i.putExtra(NOTIFICATION_ID, 1);

        PendingIntent pi = PendingIntent.getService(ctxt, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        alarManager.setRepeating(AlarmManager.RTC,c.getTimeInMillis(),PERIOD, pi);
        */
    }


    private void scheduleNotificationSpecificTime(Context ctxt, Notification notification){
        AlarmManager alarmManager = (AlarmManager) ctxt.getSystemService(Context.ALARM_SERVICE);

        /*
        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
*/

        Intent notificationIntent = new Intent(ctxt, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.SECOND, 15);
        //cal.set(2016,5,24,19,4);
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 20);
        cal.set(Calendar.DAY_OF_MONTH,15);
        cal.set(Calendar.MONTH,7); //I mesi partono da 0!!!
        //cal.set(Calendar.YEAR,2016);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    private Notification getNotification(Context ctx, String content) {
        Notification.Builder builder = new Notification.Builder(ctx);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }
}