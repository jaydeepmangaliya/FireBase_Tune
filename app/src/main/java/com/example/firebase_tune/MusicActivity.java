package com.example.firebase_tune;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    int position;
    ArrayList<String> list;
    // for playmusic
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
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
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

    }


}