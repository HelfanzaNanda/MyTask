package com.elfan.mytask;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.elfan.mytask.helper.RealmHelper;
import com.elfan.mytask.model.NoteModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailNoteActivity extends AppCompatActivity {

    public static final String KEY_ID = "key_id";
    RealmHelper realm;
    EditText edJudul, edJumlah, edTanggal;
    Button update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);

        final int dataID = getIntent().getIntExtra(KEY_ID, 0);
        realm = new RealmHelper(DetailNoteActivity.this);

        edJudul = findViewById(R.id.ed_judul);
        edJumlah = findViewById(R.id.ed_jumlah);
        edTanggal = findViewById(R.id.ed_tanggal);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);

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
                NoteModel noteModel = new NoteModel();
                noteModel.setId(dataID);
                noteModel.setJudul(edJudul.getText().toString().trim());
                noteModel.setJumlahhutang(edJumlah.getText().toString().trim());
                noteModel.setTanggal(edTanggal.getText().toString().trim());
                realm.updateData(noteModel);
                finish();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.deleteData(dataID);
                finish();
            }
        });

    }
}
