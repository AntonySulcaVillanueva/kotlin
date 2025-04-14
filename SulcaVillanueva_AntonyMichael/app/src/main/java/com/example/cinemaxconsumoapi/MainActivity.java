package com.example.cinemaxconsumoapi;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaxconsumoapi.Adaptador.AdaptadorPelicula;
import com.example.cinemaxconsumoapi.Interfaz.InterfazPelicula;
import com.example.cinemaxconsumoapi.Modelo.CResultado;
import com.example.cinemaxconsumoapi.Modelo.CPelicula;
import com.example.cinemaxconsumoapi.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "7be72508776961f3948639fbd796bccd";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    ActivityMainBinding p;
    AdaptadorPelicula af;
    boolean cargar = false;
    private Retrofit retrofit;
    private InterfazPelicula interfazPelicula;
    private int currentPage = 1;
    private ArrayList<CPelicula> todasLasPeliculas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        p = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(p.getRoot());

        af = new AdaptadorPelicula(this);
        p.listado.setAdapter(af);

        GridLayoutManager grid = new GridLayoutManager(this, 3);
        p.listado.setLayoutManager(grid);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        interfazPelicula = retrofit.create(InterfazPelicula.class);

        obtenerPeliculas();

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrarPeliculas(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrarPeliculas(newText);
                return true;
            }
        });

        p.listado.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int vi = grid.getChildCount();
                    int total = grid.getItemCount();
                    int items = grid.findFirstVisibleItemPosition();
                    if (cargar && (vi + items) >= total) {
                        cargar = false;
                        currentPage++;
                        obtenerPeliculas();
                    }
                }
            }
        });
    }

    private void filtrarPeliculas(String query) {
        ArrayList<CPelicula> peliculasFiltradas = new ArrayList<>();
        for (CPelicula pelicula : todasLasPeliculas) {
            if (pelicula.getOriginal_title() != null &&
                    pelicula.getOriginal_title().toLowerCase().contains(query.toLowerCase())) {
                peliculasFiltradas.add(pelicula);
            }
        }

        if (query.isEmpty()) {
            af.filtrarPeliculas(todasLasPeliculas);
        } else {
            af.filtrarPeliculas(peliculasFiltradas);
        }
    }

    private void obtenerPeliculas() {
        Call<CResultado> call = interfazPelicula.obtenerPeliculas(API_KEY, currentPage);
        call.enqueue(new Callback<CResultado>() {
            @Override
            public void onResponse(Call<CResultado> call, Response<CResultado> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                    todasLasPeliculas.addAll(response.body().getResults());
                    af.adicionarRegistro(response.body().getResults());
                    cargar = true;
                }
            }

            @Override
            public void onFailure(Call<CResultado> call, Throwable t) {
            }
        });
    }
}