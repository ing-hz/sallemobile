package com.example.occupationdessalles.beans;

import com.google.gson.annotations.SerializedName;

public class Salle {
    @SerializedName("_id")
    private String id;
    @SerializedName("nom")
    private String nom;
    @SerializedName("type")
    private String type;
    @SerializedName("bloc")
    private Bloc bloc;
    public Salle(){

    }
    public Bloc getBloc() {
        return bloc;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", bloc=" + bloc +
                '}';
    }
}
