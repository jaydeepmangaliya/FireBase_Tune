package com.example.firebase_tune;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewMusic;
    MusicAdapter musicAdapter ;

    ArrayList<MusicFile> musicFiles = new ArrayList<>();
    String Media_path =Environment.getExternalStorageDirectory().getPath()+"/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recyclerViewMusic = findViewById(R.id.recycleViewmusic);
        recyclerViewMusic.setLayoutManager(new LinearLayoutManager(this));


//        musicAdapter = new MusicAdapter(songlist, MainActivity.this);
//        recyclerViewMusic.setAdapter(musicAdapter);

        Toast.makeText(this, ""+Media_path, Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO}, 1);
        } else {
            // Permission already granted, get music files and display in RecyclerView
            getMusicFiles();
        }


    }
    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Permission granted, get music files and display in RecyclerView

                getMusicFiles();

            } else {

                // Permission denied, show error message

                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

            }

        }

    }


    // Get music files from device
    private void getMusicFiles() {
        ContentResolver resolver = getContentResolver();
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(audioUri, null, null, null, null);


        if (cursor!= null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                @SuppressLint("Range") String fileType = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                MusicFile musicFile = new MusicFile(filePath, fileType);
                musicFiles.add(musicFile);
            }
            cursor.close();
        }

        // Display music files in RecyclerView
        musicAdapter = new MusicAdapter(musicFiles);
        recyclerViewMusic.setAdapter(musicAdapter);


    }
}