package io.github.pspaces;

import java.io.Serializable;
import java.util.Date;

public class DemandeMateriels implements Serializable {
    private String nomProjet;
    private String[] typesMateriels;
    private int quantite;
    private float budgetMax;
    private Date dateLivraison;

    public DemandeMateriels(String nomProjet, String[] typesMateriels, int quantite, float budgetMax, Date dateLivraison) {
        this.nomProjet = nomProjet;
        this.typesMateriels = typesMateriels;
        this.quantite = quantite;
        this.budgetMax = budgetMax;
        this.dateLivraison = dateLivraison;
    }

    // Getters
    public String getNomProjet() { return nomProjet; }
    public String[] getTypesMateriels() { return typesMateriels; }
    public int getQuantite() { return quantite; }
    public float getBudgetMax() { return budgetMax; }
    public Date getDateLivraison() { return dateLivraison; }
}