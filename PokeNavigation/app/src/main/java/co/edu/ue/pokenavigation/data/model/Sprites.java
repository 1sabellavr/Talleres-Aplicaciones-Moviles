package co.edu.ue.pokenavigation.data.model;

import com.google.gson.annotations.SerializedName;

public class Sprites {
    @SerializedName("front_default")
    private String frontDefault;

    private Other other;

    public String getFrontDefault() {
        return frontDefault;
    }

    public Other getOther() {
        return other;
    }
}