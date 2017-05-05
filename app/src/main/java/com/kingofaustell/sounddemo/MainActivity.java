package com.kingofaustell.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    //Starts the media player
    public void Play (View view){
        mediaPlayer.start();
    }
    //Pauses media player
    public void Pause(View view){
        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets audio from raw folder and put it in a variable to use throughout app
        mediaPlayer = MediaPlayer.create(this, R.raw.halfCrazy);

        //Sets up audio manager to work with the device this audio is working on
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Sets the max of the audio manager to the max of the device
        int maxVolume= audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //Gets the max of the audio
        int maxLength= mediaPlayer.getDuration();

        //Sets the audio manager from app to get current volume that the device is in
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Creates variable for the seek bar widget and funtion
        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);

        //Sets the max of seekbar to max of device
        volumeControl.setMax(maxVolume);

        //Sets the cureent volume of seek bar to current volume of the device
        volumeControl.setProgress(currentVolume);

        //Creates variable for play bar widget and function
        final SeekBar audioControl = (SeekBar) findViewById(R.id.playBar);

        //Sets the max length of the seek bar to the max length of the audio
        audioControl.setMax(maxLength);

        //Sets the current position of the seek bar to the current position of the audio
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioControl.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0, 10);

        //Catches the event that the user drags and changes anything on seekbar
        audioControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            //When it starts changes
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Pause audio on change
                mediaPlayer.pause();
            }
            //The changes made
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mediaPlayer.seekTo(i);
            }
            //when the changes stops
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //starts audio after change
                mediaPlayer.start();
            }
        });

        //Catches the event that the user drags and changes anything on seekbar
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            //When starts changing
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            //The changes
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Changes the audio
                audioManager.setStreamVolume(audioManager.STREAM_MUSIC, i,0);
            }
            //When stops being changed
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
}
