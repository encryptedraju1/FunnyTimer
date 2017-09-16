package me.raaju.funnytimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
     SeekBar mySeekBar;
     TextView myTextView;
     Button myButton;
     boolean timerIsActive = false;
    CountDownTimer myTimer;
    public void UpdateTimer(int timeleftInSeconds){

        int minutes = (int) timeleftInSeconds/60;
        int seconds = timeleftInSeconds - minutes * 60;
        String MinuteLeft = Integer.toString(minutes);
        String secondLeft = Integer.toString(seconds);
        if (seconds <= 9){

            secondLeft = "0" + secondLeft;
        }
        if (minutes <= 9){

            MinuteLeft = "0" + MinuteLeft;
        }


        myTextView.setText(MinuteLeft+":"+secondLeft);
    }

    public void ressetTimer(){

        myTimer.cancel();
        myButton.setText("GO!");
        myTextView.setText("00:30");
        mySeekBar.setProgress(30);
        timerIsActive = false;
        mySeekBar.setEnabled(true);


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySeekBar =(SeekBar) findViewById(R.id.seekBar);
        myTextView =(TextView) findViewById(R.id.textView);
        myButton =(Button) findViewById(R.id.button);

        mySeekBar.setMax(600);
        mySeekBar.setProgress(30);

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                UpdateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerIsActive == false) {
                     timerIsActive = true;
                     mySeekBar.setEnabled(false);
                     myButton.setText("STOP");

                  myTimer = new CountDownTimer(mySeekBar.getProgress() * 1000 + 100, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            UpdateTimer((int) millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            ressetTimer();
                            MediaPlayer myPlayer = MediaPlayer.create(getApplicationContext(), R.raw.party_horn);
                            myPlayer.start();

                        }
                    }.start();
                } else {
                    ressetTimer();
                }
            }
        });


    }
}
