package com.example.sampleapplication.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.sampleapplication.R;

import java.util.Arrays;
import java.util.List;


public class HintPlayer {
    private MediaPlayer mediaPlayer;
    private Context context;
    private final List<Integer> audioSourceList= Arrays.asList(R.raw.begin,R.raw.gut,R.raw.faster,R.raw.goon,R.raw.bad,R.raw.slower);
    private final int BEGIN=0;
    private final int GUT=1;
    private final int FASTER=2;
    private final int GOON=3;
    private final int BAD=4;
    private final int SLOWER=5;
    public HintPlayer(Context context){
        mediaPlayer = new MediaPlayer();
        this.context=context;
    }
    private void playAudio(int index){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(context,audioSourceList.get(index));
        mediaPlayer.start();
    }

    public void begin(){
        playAudio(BEGIN);
    }

    public void gut(){
        playAudio(GUT);
    }

    public void faster(){
        playAudio(FASTER);
    }

    public void goon(){
        playAudio(GOON);
    }

    public void bad(){
        playAudio(BAD);
    }

    public void slower(){
        playAudio(SLOWER);
    }

    public void release(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }



}
