package com.elfan.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.elfan.mytask.adapter.NoteAdapter;
import com.elfan.mytask.helper.RealmHelper;
import com.elfan.mytask.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    List<NoteModel> noteModels = new ArrayList<>();
    RecyclerView recycler;
    RealmHelper realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteActivity.this, AddNoteActivity.class));
            }
        });
        realm = new RealmHelper(NoteActivity.this);
        //get data realm
        noteModels = realm.showData();
        recycler = findViewById(R.id.rv_note);
        recycler.setAdapter(new NoteAdapter(NoteActivity.this, noteModels));
        recycler.setLayoutManager(new LinearLayoutManager(NoteActivity.this));
        recycler.setHasFixedSize(true);
        recycler.addItemDecoration(new DividerItemDecoration(NoteActivity.this, 1));




    }

    @Override
    protected void onResume() {
        super.onResume();
        noteModels = realm.showData();
        recycler.setAdapter(new NoteAdapter(NoteActivity.this, noteModels));
    }
}
