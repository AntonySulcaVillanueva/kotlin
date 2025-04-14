package com.example.cinemaxconsumoapi.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemaxconsumoapi.DetallePeliculaActivity;
import com.example.cinemaxconsumoapi.Modelo.CPelicula;
import com.example.cinemaxconsumoapi.R;

import java.util.ArrayList;

public class AdaptadorPelicula extends RecyclerView.Adapter<AdaptadorPelicula.ViewHolder> {

    private ArrayList<CPelicula> data;
    private Context c;

    public AdaptadorPelicula(Context c) {
        this.data = new ArrayList<>();
        this.c = c;
    }

    public void adicionarRegistro(ArrayList<CPelicula> peliculas) {
        if (peliculas != null) {
            data.addAll(peliculas);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPelicula.ViewHolder holder, int position) {
        CPelicula pelicula = data.get(position);

        holder.txtindice.setText(String.valueOf(pelicula.getId()));
        holder.txtnombre.setText(pelicula.getOriginal_title());
        holder.txtpos.setText(String.valueOf(position));

        String imagenUrl = "https://image.tmdb.org/t/p/original" + pelicula.getPoster_path();
        Glide.with(c)
                .load(imagenUrl)
                .into(holder.imagen);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(c, DetallePeliculaActivity.class);
            intent.putExtra("id", pelicula.getId());
            intent.putExtra("original_title", pelicula.getOriginal_title());
            intent.putExtra("poster_path", pelicula.getPoster_path());
            intent.putExtra("release_date", pelicula.getRelease_date());
            intent.putExtra("overview", pelicula.getOverview());
            c.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView txtindice, txtnombre, txtpos;

        public ViewHolder(@NonNull View velemento) {
            super(velemento);
            imagen = velemento.findViewById(R.id.imagen);
            txtindice = velemento.findViewById(R.id.txtindice);
            txtnombre = velemento.findViewById(R.id.txtnombre);
            txtpos = velemento.findViewById(R.id.txtpos);
        }
    }

    public void filtrarPeliculas(ArrayList<CPelicula> peliculasFiltradas) {
        data.clear();
        if (peliculasFiltradas != null) {
            data.addAll(peliculasFiltradas);
        }
        notifyDataSetChanged();
    }
}