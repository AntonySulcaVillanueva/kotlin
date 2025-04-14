package com.example.cinemaxconsumoapi.Interfaz;

import com.example.cinemaxconsumoapi.Modelo.CResultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfazPelicula {
    @GET("popular")
    Call<CResultado> obtenerPeliculas(
            @Query("api_key") String apiKey,
            @Query("page") long page
    );
}
