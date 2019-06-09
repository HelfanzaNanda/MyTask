package com.elfan.mytask;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.elfan.mytask.adapter.FilmAdapter;
import com.elfan.mytask.model.FilmModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        recycler = findViewById(R.id.rv_film);

        /*FilmModel filmModel = new FilmModel();
        filmModel.setJudulFilm("Judul Film");
        filmModel.setPosterFilm("https://image.tmdb.org/t/p/w185_and_h278_bestv2/3ZEqU9Ykmn8zGDUwWnmTfHaaWRB.jpg");

        for (int i = 0; i <20 ; i++) {
            dataFilm.add(filmModel);
        }*/
        
        getDataOnline();

        recycler.setAdapter(new FilmAdapter(FilmActivity.this, dataFilm));
        recycler.setLayoutManager(new GridLayoutManager(FilmActivity.this, 2));


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
                if (response.isSuccessful()){
                    dataFilm = response.body().getResults();
                    recycler.setAdapter(new FilmAdapter(FilmActivity.this, dataFilm));
                }else {
                    Toast.makeText(FilmActivity.this, "Request Not Success", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseFilm> call, Throwable t) {
                Toast.makeText(FilmActivity.this, "Request Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
