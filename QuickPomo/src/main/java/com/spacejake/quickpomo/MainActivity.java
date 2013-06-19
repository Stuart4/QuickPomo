package com.spacejake.quickpomo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    int timeToEvent = 25;
    String state = "idle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

 }
    public void buttonPressed(View view) {
        ImageButton conButton =(ImageButton) findViewById(R.id.imageView);
        if (state == "idle"){
            Toast.makeText(MainActivity.this, "Work hard for twenty-five minutes!", Toast.LENGTH_LONG).show();
            conButton.setImageResource(getResources().getIdentifier("@android:drawable/ic_media_pause", null, null));
            timeToEvent = 25;
            state = "work";
            work();
        }
        else if (state == "work"){
            conButton.setImageResource(getResources().getIdentifier("@android:drawable/ic_media_play", null, null));
            state = "paused";

        }
        else if (state == "paused"){
            state="work";
            conButton.setImageResource(getResources().getIdentifier("@android:drawable/ic_media_pause", null, null));
            work();
        }

}

    public void progressClicked (View view){
        final SeekBar seek = (SeekBar) findViewById(R.id.seekBar);
        seek.setVisibility(View.VISIBLE);

            toastIt("Got This FAR!!!");
        Runnable task = new Runnable() {
            public void run() {
                makeInvisible();
            }
        };
        worker.schedule(task, 3, TimeUnit.SECONDS);

    }

    public void makeInvisible() {
        runOnUiThread(new Runnable() {
            public void run() {
                SeekBar seek = (SeekBar) findViewById(R.id.seekBar);
                seek.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void work() {
        updateUI();
        if (state == "paused"){return;}
        else if(state == "work" && timeToEvent == 0){
            state = "play";
            timeToEvent = 5;
            updateUI();

        }
        else if(state == "play" && timeToEvent == 0){
            state = "work";
            timeToEvent = 25;
            updateUI();
        }
        runInMinute();
}

    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    void runInMinute() {
        Runnable task = new Runnable() {
            public void run() {
                timeToEvent -=1;
                work();
            }
        };
        worker.schedule(task, 1, TimeUnit.SECONDS);
    }

    public void toastIt(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                TextView counter =(TextView) findViewById(R.id.textView);
                counter.setText(String.valueOf(timeToEvent) + " Minutes");
                ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
                progress.setProgress(timeToEvent);
            }
        });
    }
 }
