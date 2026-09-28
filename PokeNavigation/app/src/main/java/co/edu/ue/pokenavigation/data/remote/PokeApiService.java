package co.edu.ue.pokenavigation.data.remote;

import co.edu.ue.pokenavigation.data.model.PokemonResponse;
import co.edu.ue.pokenavigation.data.model.PokemonDetailResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface PokeApiService {

    @GET("pokemon")
    Call<PokemonResponse> getPokemon(
            @Query("limit") int limit,
            @Query("offset") int  offset
    );

    @GET("pokemon/{name}")
    Call<PokemonDetailResponse> getPokemonDetail(
            @Path("name") String name
    );
}
