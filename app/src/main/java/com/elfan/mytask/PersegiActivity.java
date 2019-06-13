package com.elfan.mytask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PersegiActivity extends AppCompatActivity {
    EditText edSisi;
    Button btnHitung;
    TextView tvHasil;
    Spinner spSpiner;
    String[] pilihan = {"Volume", "Keliling", "Luas Permukaan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persegi);
        getSupportActionBar().setTitle("Luas");

        edSisi = findViewById(R.id.ed_sisi);
        btnHitung = findViewById(R.id.btn_hitung);
        tvHasil = findViewById(R.id.tv_hasil);
        spSpiner = findViewById(R.id.sp_spinner);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spSpiner.getSelectedItem().toString().equals(pilihan[0])){
                    Double sisi = Double.valueOf(edSisi.getText().toString());
                    Double hasil = sisi * sisi * sisi;
                    tvHasil.setText(hasil.toString());
                }else if (spSpiner.getSelectedItem().toString().equals(pilihan[1])){
                    Double sisi = Double.valueOf(edSisi.getText().toString());
                    Double hasil = 6 * sisi * sisi;
                    tvHasil.setText(hasil.toString());
                }else if (spSpiner.getSelectedItem().toString().equals(pilihan[2])){
                    Double sisi = Double.valueOf(edSisi.getText().toString());
                    Double hasil = 12 * sisi;
                    tvHasil.setText(hasil.toString());
                }
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(PersegiActivity.this, android.R.layout.simple_spinner_dropdown_item, pilihan);
        spSpiner.setAdapter(adapter);
    }
}
