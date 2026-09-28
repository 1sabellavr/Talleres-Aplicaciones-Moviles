package co.edu.ue.pokenavigation.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonDetailResponse {
    private String name;
    private int height;
    private int weight;

    @SerializedName("base_experience")
    private int baseExperience;

    private Sprites sprites;
    private List<PokemonTypeResponse> types;

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<PokemonTypeResponse> getTypes() {
        return types;
    }
}