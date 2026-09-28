package co.edu.ue.pokenavigation.data.model;

import com.google.gson.annotations.SerializedName;

public class Artwork {
    @SerializedName("front_default")
    private String frontDefault;

    public String getFrontDefault() {
        return frontDefault;
    }
}