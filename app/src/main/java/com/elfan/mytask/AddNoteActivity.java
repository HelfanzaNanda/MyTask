package com.elfan.mytask;

import android.app.DatePickerDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class AddNoteActivity extends AppCompatActivity {
    private EditText edJudul, edJumlah, edTanggal;
    RealmHelper realm;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        init();

        edTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int nowYear = calendar.get(Calendar.YEAR);
                int nowMonth = calendar.get(Calendar.MONTH);
                int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
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


        add.setOnClickListener(new View.OnClickListener() {
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
                    noteModel.setId((int) realm.getNextId());
                    noteModel.setJudul(edJudul.getText().toString().trim());
                    noteModel.setJumlahhutang(edJumlah.getText().toString().trim());
                    noteModel.setTanggal(edTanggal.getText().toString().trim());
                    realm.insertData(noteModel);
                    finish();
                    Toast.makeText(AddNoteActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void init(){
        edJudul = findViewById(R.id.ed_judul);
        edJumlah = findViewById(R.id.ed_jumlah);
        edTanggal = findViewById(R.id.ed_tanggal);
        add = findViewById(R.id.fab_add);
        realm = new RealmHelper(AddNoteActivity.this);
    }
}
