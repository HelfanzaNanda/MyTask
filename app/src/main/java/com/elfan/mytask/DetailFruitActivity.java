package com.elfan.mytask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.elfan.mytask.constant.ConstantFruit;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailFruitActivity extends AppCompatActivity {

    TextView tvNamaBuah;
    CircleImageView ivGambarBauh;

    private static final String TAG = "DetailFruitActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fruit);

        tvNamaBuah = findViewById(R.id.tv_detail_buah);
        ivGambarBauh = findViewById(R.id.iv_detail_gambar);

        String namabuah = getIntent().getStringExtra(ConstantFruit.DATANAMA);
        int gambarbuah = getIntent().getIntExtra(ConstantFruit.DATAGAMBAR,0);

        Log.d(TAG, "Buah : "+namabuah);
        Log.d(TAG, "Gambar : "+gambarbuah);

        ivGambarBauh.setImageResource(gambarbuah);
        tvNamaBuah.setText(namabuah);

    }
}
