package co.edu.ue.pokenavigation.data.model;

import com.google.gson.annotations.SerializedName;

public class Other {
    @SerializedName("official-artwork")
    private Artwork artwork;

    public Artwork getArtwork() {
        return artwork;
    }
}