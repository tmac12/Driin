package tmac12.it.driin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by marco on 15/08/16.
 */
public class RingingPlayingService extends Service {

    // Time period between two vibration events
    private final static int VIBRATE_DELAY_TIME = 2000;
    // Vibrate for 1000 milliseconds
    private final static int DURATION_OF_VIBRATION = 1000;
    // Increase alarm volume gradually every 600ms
    private final static int VOLUME_INCREASE_DELAY = 600;
    // Volume level increasing step
    private final static float VOLUME_INCREASE_STEP = 0.01f;
    // Max player volume level
    private final static float MAX_VOLUME = 1.0f;

    private Vibrator mVibrator;
    private float mVolumeLevel = 0;

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.e("MyActivity", "onBind");
        return null;
    }


    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String state = intent.getExtras().getString("extra");

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }
        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), notification);
            mMediaPlayer.start();

            //start vibrate
            mHandler.post(mVibrationRunnable);

            //increase volume
            mHandler.postDelayed(mVolumeRunnable, VOLUME_INCREASE_DELAY);

            this.isRunning = true;
            this.startId = 0;
        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }
        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;

    }

    private Handler mHandler = new Handler();
    private Runnable mVibrationRunnable = new Runnable() {
        @Override
        public void run() {
            mVibrator.vibrate(DURATION_OF_VIBRATION);
            // Provide loop for vibration
            mHandler.postDelayed(mVibrationRunnable,
                    DURATION_OF_VIBRATION + VIBRATE_DELAY_TIME);
        }
    };

    private Runnable mVolumeRunnable = new Runnable() {
        @Override
        public void run() {
            // increase volume level until reach max value
            if (mMediaPlayer != null && mVolumeLevel < MAX_VOLUME) {
                mVolumeLevel += VOLUME_INCREASE_STEP;
                mMediaPlayer.setVolume(mVolumeLevel, mVolumeLevel);
                // set next increase in 600ms
                mHandler.postDelayed(mVolumeRunnable, VOLUME_INCREASE_DELAY);
            }
        }
    };

}
