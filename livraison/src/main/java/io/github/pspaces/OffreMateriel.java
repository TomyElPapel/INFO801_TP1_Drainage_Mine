package io.github.pspaces;

import java.io.Serializable;
import java.util.Date;

public class OffreMateriel implements Serializable {
    private DemandeMateriels demande;
    private Date dateProposition;
    private float prixPropose;

    public OffreMateriel(DemandeMateriels demande, Date dateProposition, float prixPropose) {
        this.demande = demande;
        this.dateProposition = dateProposition;
        this.prixPropose = prixPropose;
    }

    // Getters
    public DemandeMateriels getDemande() { return demande; }
    public Date getDateProposition() { return dateProposition; }
    public float getPrixPropose() { return prixPropose; }
}