package co.edu.ue.pokenavigation.data.repository;

import co.edu.ue.pokenavigation.data.model.PokemonDetailResponse;
import co.edu.ue.pokenavigation.data.model.PokemonResponse;
import co.edu.ue.pokenavigation.data.remote.PokeApiService;
import co.edu.ue.pokenavigation.data.remote.RetrofitClient;
import retrofit2.Call;

public class PokemonRepository {
    private final PokeApiService service;

    public PokemonRepository() {
        service = RetrofitClient.getService();
    }

    public Call<PokemonResponse> obtenerPokemon(int limit, int offset) {
        return service.getPokemon(limit, offset);
    }

    //Metodo para obtener detalle por nombre (ACTIVIDAD)
    public Call<PokemonDetailResponse> obtenerDetallePokemon(String name) {
        return service.getPokemonDetail(name);
    }
}