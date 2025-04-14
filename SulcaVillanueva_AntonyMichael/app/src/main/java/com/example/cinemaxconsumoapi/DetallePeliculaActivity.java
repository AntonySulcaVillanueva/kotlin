package com.example.cinemaxconsumoapi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetallePeliculaActivity extends AppCompatActivity {

    private ImageView imagenPelicula;
    private TextView txtTitulo, txtFechaLanzamiento, txtId, txtDescripcion;

    private Button btnVolver;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);

        // Inicializar vistas
        imagenPelicula = findViewById(R.id.imagen);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtFechaLanzamiento = findViewById(R.id.txtFechaLanzamiento);
        txtId = findViewById(R.id.txtId);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnVolver = findViewById(R.id.btnVolver);

        // Obtener datos del Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titulo = extras.getString("original_title");
            String posterPath = extras.getString("poster_path");
            String fechaLanzamiento = extras.getString("release_date");
            String descripcion = extras.getString("overview");
            long id = extras.getLong("id");

            // Asignar datos a las vistas
            txtTitulo.setText(titulo);
            txtFechaLanzamiento.setText(fechaLanzamiento);
            txtId.setText(String.valueOf(id));
            txtDescripcion.setText(descripcion);

            // Cargar imagen
            String imagenUrl = "https://image.tmdb.org/t/p/original" + posterPath;
            Glide.with(this)
                    .load(imagenUrl)
                    .into(imagenPelicula);
        }
        btnVolver.setOnClickListener(v -> finish());
    }
}