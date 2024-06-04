package com.example.firebase_tune;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {

    Button btnnext , btnprevies , btnpushplay;
    SeekBar seekBarvolume , seekbarmusicplay;
    TextView adiofilename,starttimetext,endtimetext;
    ArrayList<String> allpath;
    String title , filepath;
    ImageView musicacimg;
    int position;
    ArrayList<String> list;
    Animation musicimganim, musicnameanim;
    // for playmusic
    MediaPlayer player;
 Runnable runnable;
 Handler handler;
 int totaltime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
        musicacimg = findViewById(R.id.musicACimg);
        btnnext = findViewById(R.id.nextbtn);
        btnprevies = findViewById(R.id.previosbtn);
        btnpushplay = findViewById(R.id.musicplaypushbtn);
        adiofilename = findViewById(R.id.audionamefiletext);
        starttimetext = findViewById(R.id.starttimetext);
        endtimetext = findViewById(R.id.endtimetext);
        seekBarvolume = findViewById(R.id.seekbarvolume);
        seekbarmusicplay = findViewById(R.id.musicpalyseekbar);
        title = getIntent().getStringExtra("title");
        position = getIntent().getIntExtra("position",0);
        //musicList = (ArrayList<MusicFile>) getIntent().getSerializableExtra("music_list");
        allpath = getIntent().getStringArrayListExtra("list");
        adiofilename.setText(title);

        musicimganim = AnimationUtils.loadAnimation(this,R.anim.anim);
        musicacimg.setAnimation(musicimganim);
        musicnameanim = AnimationUtils.loadAnimation(this,R.anim.muusicnametraslate);
        adiofilename.setAnimation(musicnameanim);



        // for play music
        player = new MediaPlayer();
        try {
            player.setDataSource(title);
            player.prepare();
            player.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        btnpushplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(player.isPlaying()){
                    player.pause();
                    btnpushplay.setBackgroundResource(R.drawable.musicpalyicon);
                }
                else {
                    player.start();
                    btnpushplay.setBackgroundResource(R.drawable.musicstopicon);
                }
            }
        });




        btnprevies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(position>0){
                position--;

                try {
                    player.reset();
                    player.setDataSource(allpath.get(position));
                    player.prepare();
                    player.start();
                    adiofilename.setText(allpath.get(position));
                    adiofilename.clearAnimation();
                    adiofilename.startAnimation(musicnameanim);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position<allpath.size()-1){
                    position++;

                    try {
                        player.reset();
                        player.setDataSource(allpath.get(position));
                        player.prepare();
                        player.start();
                        adiofilename.setText(allpath.get(position));
                        adiofilename.clearAnimation();
                        adiofilename.startAnimation(musicnameanim);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

        seekBarvolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
              if(b){
                   seekBarvolume.setProgress(i);
                   float volumelevel = i/100f;
                   player.setVolume(volumelevel,volumelevel);
              }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbarmusicplay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    player.seekTo(i);
                    seekbarmusicplay.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
               totaltime = player.getDuration();
               seekbarmusicplay.setMax(totaltime);
               int currentposition = player.getCurrentPosition();
               seekbarmusicplay.setProgress(currentposition);
               handler.postDelayed(runnable,1000);
               String elapsedtime = createTimeLable(currentposition);
               String lasttime = createTimeLable(totaltime);
               starttimetext.setText(elapsedtime);
               endtimetext.setText(lasttime);
               if(elapsedtime.equals(lasttime)){
                   if(position<allpath.size()-1){
                       position++;

                       try {
                           player.reset();
                           player.setDataSource(allpath.get(position));
                           player.prepare();
                           player.start();
                           adiofilename.setText(allpath.get(position));
                           adiofilename.clearAnimation();
                           adiofilename.startAnimation(musicnameanim);
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }

                   }

               }



            }
        };
        handler.post(runnable);

    }

    public String createTimeLable(int currentposition){
        // 1 mit == 60secod
        //1 sc = 1000 milisecond
        String timelable;
        int minit,second;
        minit = currentposition/1000/60;
        second = currentposition/1000%60;
        if(second <10){
            timelable = minit +":0"+second;

        }
        else {
            timelable = minit+":"+second;
        }
        return  timelable;
    }


}