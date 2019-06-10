package com.elfan.mytask.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elfan.mytask.DetailFIlmActivity;
import com.elfan.mytask.R;
import com.elfan.mytask.model.ResultsItem;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {
    public static final String DATA_MOVIE = "datamovie";
    public static final String DATA_EXTRA = "dataextra";
    private Context context;
    private List<ResultsItem> data = new ArrayList<>();

    public FilmAdapter(Context context, List<ResultsItem> data) {
        this.context = context;
        this.data = data;
    }

    //menyambungkan layout
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_film, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    //set data
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvJudul.setText(data.get(i).getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+data.get(i).getPosterPath()).into(myViewHolder.ivPoster);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailFIlmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(DATA_MOVIE, Parcels.wrap(data.get(i)));
                intent.putExtra(DATA_EXTRA, bundle);
                context.startActivity(intent);
            }
        });
    }

    //jumlah data
    @Override
    public int getItemCount() {
        return data.size();
    }


    //mengenalkan komponen dalam item
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul;
        ImageView ivPoster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tv_item_judul);
            ivPoster = itemView.findViewById(R.id.iv_item_gambar);
        }
    }
}
