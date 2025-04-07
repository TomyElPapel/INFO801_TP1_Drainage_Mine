package io.github.pspaces;

import java.util.Date;

public class AppelOffre {
    private String nom;
    private String[] requirements;
    private float cout;
    private Date date;
    private int quantitee;

    // Constructor
    public AppelOffre(String nom, String[] requirements, float cout, Date date, int quantitee) {
        this.nom = nom;
        this.requirements = requirements;
        this.cout = cout;
        this.date = date;
        this.quantitee = quantitee;
    }

    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public void setRequirements(String[] requirements) {
        this.requirements = requirements;
    }

    public float getCout() {
        return cout;
    }

    public void setCout(float cout) {
        this.cout = cout;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantitee() {
        return quantitee;
    }

    public void setQuantitee(int quantitee) {
        this.quantitee = quantitee;
    }
}
