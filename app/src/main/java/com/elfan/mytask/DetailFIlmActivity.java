package com.elfan.mytask;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elfan.mytask.adapter.FilmAdapter;
import com.elfan.mytask.model.ResultsItem;

import org.parceler.Parcels;

public class DetailFIlmActivity extends AppCompatActivity {
    ResultsItem datamovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getBundleExtra(FilmAdapter.DATA_EXTRA);
        datamovie = Parcels.unwrap(bundle.getParcelable(FilmAdapter.DATA_MOVIE));

        getSupportActionBar().setTitle(datamovie.getTitle());

        ImageView ivBackdrop = findViewById(R.id.iv_detail_backdrop);
        TextView tvDesc = findViewById(R.id.detail_desc);

        Glide.with(DetailFIlmActivity.this).load("https://image.tmdb.org/t/p/w500"+datamovie.getBackdropPath()).into(ivBackdrop);
        tvDesc.setText(datamovie.getOverview());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
