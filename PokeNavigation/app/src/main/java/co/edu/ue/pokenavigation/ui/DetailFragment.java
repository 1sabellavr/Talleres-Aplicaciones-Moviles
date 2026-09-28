package co.edu.ue.pokenavigation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import co.edu.ue.pokenavigation.R;
import co.edu.ue.pokenavigation.data.FavoritesManager;
import co.edu.ue.pokenavigation.data.model.PokemonDetailResponse;
import co.edu.ue.pokenavigation.data.model.PokemonTypeResponse;
import co.edu.ue.pokenavigation.data.repository.PokemonRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {

    private ImageView ivPokemonDetail;
    private TextView tvDetailName, tvDetailTypes, tvDetailHeight, tvDetailWeight, tvDetailExperience;
    private MaterialButton btnFavorite;
    private PokemonRepository repository;
    private String pokemonName;

    public DetailFragment() {
        super(R.layout.fragment_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivPokemonDetail = view.findViewById(R.id.ivPokemonDetail);
        tvDetailName = view.findViewById(R.id.tvDetailName);
        tvDetailTypes = view.findViewById(R.id.tvDetailTypes);
        tvDetailHeight = view.findViewById(R.id.tvDetailHeight);
        tvDetailWeight = view.findViewById(R.id.tvDetailWeight);
        tvDetailExperience = view.findViewById(R.id.tvDetailExperience);
        btnFavorite = view.findViewById(R.id.btnFavorite);

        repository = new PokemonRepository();

        // Obtenemos el nombre del Pokémon enviado desde el HomeFragment
        if (getArguments() != null) {
            pokemonName = getArguments().getString("pokemon_name");
        }

        if (pokemonName != null) {
            cargarDetalles(pokemonName);
        }

        btnFavorite.setOnClickListener(v -> {
            if (pokemonName != null) {
                if (!FavoritesManager.favoritos.contains(pokemonName)) {
                    FavoritesManager.favoritos.add(pokemonName);
                }
                Toast.makeText(requireContext(), "¡" + pokemonName + " guardado en Favoritos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDetalles(String name) {
        repository.obtenerDetallePokemon(name).enqueue(new Callback<PokemonDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonDetailResponse> call, @NonNull Response<PokemonDetailResponse> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    PokemonDetailResponse detail = response.body();

                    tvDetailName.setText(detail.getName());
                    tvDetailHeight.setText("Altura: " + detail.getHeight());
                    tvDetailWeight.setText("Peso: " + detail.getWeight());
                    tvDetailExperience.setText("Experiencia Base: " + detail.getBaseExperience());

                    // Concatenar tipos
                    StringBuilder typesBuilder = new StringBuilder("Tipos: ");
                    if (detail.getTypes() != null) {
                        for (PokemonTypeResponse tr : detail.getTypes()) {
                            typesBuilder.append(tr.getType().getName()).append(" ");
                        }
                    }
                    tvDetailTypes.setText(typesBuilder.toString());

                    // Cargar imagen oficial con Glide
                    String imageUrl = null;
                    if (detail.getSprites() != null && detail.getSprites().getOther() != null
                            && detail.getSprites().getOther().getArtwork() != null) {
                        imageUrl = detail.getSprites().getOther().getArtwork().getFrontDefault();
                    }

                    if (imageUrl != null) {
                        Glide.with(requireContext())
                                .load(imageUrl)
                                .into(ivPokemonDetail);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonDetailResponse> call, @NonNull Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(requireContext(), "Error al cargar los detalles", Toast.LENGTH_SHORT).show();
            }
        });
    }
}