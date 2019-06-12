package com.elfan.mytask;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.elfan.mytask.adapter.FilmAdapter;
import com.elfan.mytask.model.ResponseFilm;
import com.elfan.mytask.model.ResultsItem;
import com.elfan.mytask.refrofit.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmActivity extends AppCompatActivity {

    List<ResultsItem> dataFilm = new ArrayList<>();
    RecyclerView recycler;
    private int condition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        recycler = findViewById(R.id.rv_film);
        getDataOnline();
        recycler.setAdapter(new FilmAdapter(FilmActivity.this, dataFilm));
        recycler.setLayoutManager(new LinearLayoutManager(FilmActivity.this));

    }

    private void getDataOnline() {
        final ProgressDialog progressDialog = new ProgressDialog(FilmActivity.this);
        progressDialog.setMessage("Waiting...");
        progressDialog.show();
        Call<ResponseFilm> request = RetrofitConfig.getApiService().getDataFIlm("9f8808cfea557f655e533ea784664924");
        request.enqueue(new Callback<ResponseFilm>() {
            @Override
            public void onResponse(Call<ResponseFilm> call, Response<ResponseFilm> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    dataFilm = response.body().getResults();
                    recycler.setAdapter(new FilmAdapter(FilmActivity.this, dataFilm));
                } else {
                    Toast.makeText(FilmActivity.this, "Request Not Success", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseFilm> call, Throwable t) {
                Toast.makeText(FilmActivity.this, "Request Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeViewMode() {
        if (condition > 2) {
            condition = 0;
        }
        condition+=1;
        switch (condition) {
            case 0:
                recycler.setLayoutManager(new LinearLayoutManager(FilmActivity.this));
                break;
            case 1:
                recycler.setLayoutManager(new GridLayoutManager(FilmActivity.this, 2));
                break;
            case 2:
                recycler.setLayoutManager(new GridLayoutManager(FilmActivity.this, 3));
                break;
            default:
                recycler.setLayoutManager(new LinearLayoutManager(FilmActivity.this));
                condition = 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_film, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view:
                changeViewMode();
                //Toast.makeText(this, "Sudah Di Klik Kok", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
        //return super.onOptionsItemSelected(item);
    }

}
