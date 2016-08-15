package tmac12.it.driin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by marco on 15/08/16.
 */
public class RingingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);
        Button stopAlarm = (Button) findViewById(R.id.stop_alarm);
        if (stopAlarm!=null) {
            stopAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent myIntent = new Intent(RingingActivity.this, NotificationPublisher.class);
                    //stop the ring
                    myIntent.putExtra("extra", "no");
                    sendBroadcast(myIntent);

                    //close activity
                    finish();
                }
            });
        }
    }
}
