package com.example.occupationdessalles.beans;

public class Creneau {
    private String heure_debut;
    private String heure_fin;

    public Creneau() {
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
    }

    @Override
    public String toString() {
        return "Creneau{" +
                "heure_debut='" + heure_debut + '\'' +
                ", heure_fin='" + heure_fin + '\'' +
                '}';
    }
}
