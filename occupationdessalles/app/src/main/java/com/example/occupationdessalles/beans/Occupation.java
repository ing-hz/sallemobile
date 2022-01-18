package com.example.occupationdessalles.beans;

public class Occupation {
    private String id;
    private String salle;
    private String creneau;

    public Occupation() {
    }

    public Occupation(String salle, String creneau) {
        this.salle = salle;
        this.creneau = creneau;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getCreneau() {
        return creneau;
    }

    public void setCreneau(String creneau) {
        this.creneau = creneau;
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "id='" + id + '\'' +
                ", salle='" + salle + '\'' +
                ", creneau='" + creneau + '\'' +
                '}';
    }
}
