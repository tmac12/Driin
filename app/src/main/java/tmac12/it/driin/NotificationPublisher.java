package tmac12.it.driin;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

/**
 * Created by marco on 24/05/16.
 */
public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        Log.e("MyActivity", "In the receiver with " + state);

        if (state!=null && state.equals("yes")) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notification = intent.getParcelableExtra(NOTIFICATION);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            notificationManager.notify(id, notification);
        }
        //fai partire la suoneria
        //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        //ringtone.play();



        Intent serviceIntent = new Intent(context,RingingPlayingService.class);
        serviceIntent.putExtra("extra", state);

        context.startService(serviceIntent);

        if (state!=null && state.equals("yes")) {
            Intent i = new Intent(context, RingingActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}