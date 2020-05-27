package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar seekBar;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;
    Button playButton;

    public void resetTimer(){

        timerTextView.setText("0:00");
        seekBar.setProgress(0);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        playButton.setText("Start");
        counterIsActive = false;

    }

    public void clickFunction(View view) {

        if (counterIsActive) {

           resetTimer();

        } else {
            counterIsActive = true;
            seekBar.setEnabled(false);
            playButton.setText("Stop");

            Log.i("Info", "Button Pressed");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                public void onTick(long milliSeconds) {

                    updateTimer((int) milliSeconds / 1000);

                }

                public void onFinish() {
                    Log.i("Finished", "Timer Complete");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mplayer.start();
                    resetTimer();


                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        timerTextView = findViewById(R.id.countdownTextView);
        playButton = findViewById(R.id.playButton);

        seekBar.setMax(600);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);


            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
            }
        });
    }


}
