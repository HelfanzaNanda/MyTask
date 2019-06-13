package com.elfan.mytask;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.elfan.mytask.helper.RealmHelper;
import com.elfan.mytask.model.NoteModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailNoteActivity extends AppCompatActivity {

    public static final String KEY_ID = "key_id";
    RealmHelper realm;
    private EditText edJudul, edJumlah, edTanggal;
    FloatingActionButton update;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);

        init();
        
        final int dataID = getIntent().getIntExtra(KEY_ID, 0);
        
        NoteModel noteModel = realm.showOneData(dataID);
        edJudul.setText(noteModel.getJudul());
        edJumlah.setText(noteModel.getJumlahhutang());
        edTanggal.setText(noteModel.getTanggal());

        edTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int nowYear = calendar.get(Calendar.YEAR);
                int nowMonth = calendar.get(Calendar.MONTH);
                int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DetailNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        edTanggal.setText(dateFormat.format(cal.getTime()));
                    }
                }, nowYear, nowMonth, nowDay);
                dialog.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edJudul.getText().toString().trim())){
                    edJudul.setError("Judul Tidak Boleh Kosong");
                }else if (TextUtils.isEmpty(edJumlah.getText().toString().trim())){
                    edJumlah.setError("Jumlah Hutang Tidak Boleh Kosong");
                }else if (TextUtils.isEmpty(edTanggal.getText().toString().trim())){
                    edTanggal.setError("Tanggal Tidak Boleh Kosong");
                }else {
                    NoteModel noteModel = new NoteModel();
                    noteModel.setId(dataID);
                    noteModel.setJudul(edJudul.getText().toString().trim());
                    noteModel.setJumlahhutang(edJumlah.getText().toString().trim());
                    noteModel.setTanggal(edTanggal.getText().toString().trim());
                    realm.updateData(noteModel);
                    finish();
                    Toast.makeText(DetailNoteActivity.this, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        realm = new RealmHelper(DetailNoteActivity.this);
        edJudul = findViewById(R.id.ed_judul);
        edJumlah = findViewById(R.id.ed_jumlah);
        edTanggal = findViewById(R.id.ed_tanggal);
        update = findViewById(R.id.fab_update);
    }

    private void DialogDelete(){
        final int dataID = getIntent().getIntExtra(KEY_ID, 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("apakah data ini akan di hapus???");
        builder
                .setMessage("klik YA untuk hapus")
                .setIcon(R.drawable.ic_delete_color)
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.deleteData(dataID);
                        finish();
                        Toast.makeText(DetailNoteActivity.this, "Data Berhasil diHapus", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                DialogDelete();
                return true;
        }
        return false;
    }
}
