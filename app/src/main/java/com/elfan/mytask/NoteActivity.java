package com.elfan.mytask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
<<<<<<< HEAD
=======
import android.widget.ArrayAdapter;
>>>>>>> f6fe00fb48c184a3cc4f12770eff2fb0a72b675a
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
import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    List<NoteModel> noteModels = new ArrayList<>();
    RecyclerView recycler;
    RealmHelper realm;
    LinearLayout bottomsheet;
    private EditText edJudul, edJumlah, edTanggal;
    FloatingActionButton add;
    SwipeRefreshLayout refreshLayout;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

<<<<<<< HEAD
        getSupportActionBar().setTitle("Note");

=======
        bottomsheet = findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
>>>>>>> f6fe00fb48c184a3cc4f12770eff2fb0a72b675a
        init();
        setTanggal();
        addData();
        showData();
        refresh();
    }

    private void refresh(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void showData(){
        noteModels.clear();
        noteModels = realm.showData();
        recycler = findViewById(R.id.rv_note);
        recycler.setAdapter(new NoteAdapter(NoteActivity.this, noteModels));
        recycler.setLayoutManager(new LinearLayoutManager(NoteActivity.this));
<<<<<<< HEAD
        //recycler.setHasFixedSize(true);
=======
>>>>>>> f6fe00fb48c184a3cc4f12770eff2fb0a72b675a
        recycler.addItemDecoration(new DividerItemDecoration(NoteActivity.this, 1));
    }

    private void addData() {
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
                    noteModels = realm.showData();
                    recycler.setAdapter(new NoteAdapter(NoteActivity.this, noteModels));
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == bottomSheetBehavior.STATE_EXPANDED){
            hideKeyboard();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else {
            super.onBackPressed();
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager) NoteActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void init(){
        edJudul = findViewById(R.id.ed_judul);
        edJumlah = findViewById(R.id.ed_jumlah);
        edTanggal = findViewById(R.id.ed_tanggal);
        add = findViewById(R.id.fab_add);
        refreshLayout = findViewById(R.id.swipe_refresh);
        realm = new RealmHelper(NoteActivity.this);
        bottomsheet = findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
    }

    private void clear(){
        edJudul.getText().clear();
        edJumlah.getText().clear();
        edTanggal.getText().clear();
    }

    private void hideKeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager inputMethodManager = (InputMethodManager) NoteActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            hideKeyboard();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else{
            super.onBackPressed();
        }
    }
}