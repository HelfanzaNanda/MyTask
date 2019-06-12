package com.elfan.mytask;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.elfan.mytask.adapter.NoteAdapter;
import com.elfan.mytask.helper.RealmHelper;
import com.elfan.mytask.model.NoteModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    List<NoteModel> noteModels = new ArrayList<>();
    RecyclerView recycler;
    RealmHelper realm;
    LinearLayout bottomsheet;
    private EditText edJudul, edJumlah, edTanggal;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomsheet = findViewById(R.id.bottomsheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        //bottomSheetBehavior.setHideable(true);

        init();
        setTanggal();
        addData();
        showData();
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                //startActivity(new Intent(NoteActivity.this, AddNoteActivity.class));
            }
        });*/
        //realm = new RealmHelper(NoteActivity.this);
        //get data realm

    }

    private void showData(){
        noteModels = realm.showData();
        recycler = findViewById(R.id.rv_note);
        recycler.setAdapter(new NoteAdapter(NoteActivity.this, noteModels));
        recycler.setLayoutManager(new LinearLayoutManager(NoteActivity.this));
        recycler.setHasFixedSize(true);
        recycler.addItemDecoration(new DividerItemDecoration(NoteActivity.this, 1));
    }

    private void addData() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
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
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    noteModels = realm.showData();
                    recycler.setAdapter(new NoteAdapter(NoteActivity.this, noteModels));
                    Toast.makeText(NoteActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                }
                clear();
            }
        });
    }

    private void setTanggal() {
        edTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int nowYear = calendar.get(Calendar.YEAR);
                int nowMonth = calendar.get(Calendar.MONTH);
                int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(NoteActivity.this, new DatePickerDialog.OnDateSetListener() {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteModels = realm.showData();
        recycler.setAdapter(new NoteAdapter(NoteActivity.this, noteModels));
    }

    private void init(){
        edJudul = findViewById(R.id.ed_judul);
        edJumlah = findViewById(R.id.ed_jumlah);
        edTanggal = findViewById(R.id.ed_tanggal);
        add = findViewById(R.id.fab_add);
        realm = new RealmHelper(NoteActivity.this);
    }

    private void clear(){
        edJudul.getText().clear();
        edJumlah.getText().clear();
        edTanggal.getText().clear();
    }

}
