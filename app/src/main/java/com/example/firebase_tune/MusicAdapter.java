package com.example.firebase_tune;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private List<MusicFile> musicFiles;
    ArrayList<String>allpath = new ArrayList<>();


    public MusicAdapter(List<MusicFile> musicFiles) {
        this.musicFiles = musicFiles;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MusicFile musicFile = musicFiles.get(position);
        allpath.add(musicFile.getFilePath());

        holder.textView.setText(musicFile.getFilePath());

        holder.itemView.setOnClickListener(new View.OnClickListener() {


            String title  = musicFile.getFilePath();


            @Override

            public void onClick(View v) {

                Context context = holder.itemView.getContext();
                Toast.makeText(context, ""+title, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MusicActivity.class);
                intent.putExtra("list",allpath);
                intent.putExtra("title",title);
                intent.putExtra("position" , position);
                //intent.putExtra("music_list", (ArrayList<MusicFile>) musicFiles);
              ;


                context.startActivity(intent);


                // ...

            }

        });
    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.CARD_ADUONAMW);
            cardView = itemView.findViewById(R.id.musiccardd);


        }
    }
}






