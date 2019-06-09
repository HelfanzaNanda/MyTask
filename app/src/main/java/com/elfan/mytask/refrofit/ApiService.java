package com.elfan.mytask.refrofit;

import com.elfan.mytask.model.ResponseFilm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<ResponseFilm> getDataFIlm(
            @Query("api_key") String apikey
    );
}
