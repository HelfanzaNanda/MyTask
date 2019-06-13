package com.elfan.mytask;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.elfan.mytask.adapter.BuahAdapter;
import com.elfan.mytask.constant.ConstantFruit;

public class FruitActivity extends AppCompatActivity {
    ListView list;
    String[] namaBuah = {
      "Alpukat", "Apel", "Ceri", "Durian", "Jambu Air", "Manggis", "Strawberry"
    };
    int[] gambarBuah = {
            R.drawable.alpukat,
            R.drawable.apel,
            R.drawable.ceri,
            R.drawable.durian,
            R.drawable.jambuair,
            R.drawable.manggis,
            R.drawable.strawberry
    };

    int[] suaraBuah = {
            R.raw.alpukat,
            R.raw.apel,
            R.raw.ceri,
            R.raw.durian,
            R.raw.jambu_air,
            R.raw.manggis,
            R.raw.strawberry
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        getSupportActionBar().setTitle("Fruit");

        list = findViewById(R.id.list_view);
        BuahAdapter adapter = new BuahAdapter(FruitActivity.this, namaBuah, gambarBuah);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaPlayer.create(FruitActivity.this, suaraBuah[position]).start();
                Intent i = new Intent(FruitActivity.this, DetailFruitActivity.class);
                i.putExtra(ConstantFruit.DATANAMA, namaBuah[position]);
                i.putExtra(ConstantFruit.DATAGAMBAR, gambarBuah[position]);
                startActivity(i);
            }
        });

    }
}
