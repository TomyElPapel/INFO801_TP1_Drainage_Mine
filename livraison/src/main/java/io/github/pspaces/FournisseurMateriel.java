package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

import java.util.Date;
import java.util.Random;

public class FournisseurMateriel implements Runnable {
    private Space space;
    private String id;
    private Random random = new Random();

    public FournisseurMateriel(Space space, String id) {
        this.space = space;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("FournisseurMateriel " + id + ": En attente d'une demande materiel...");

                Object[] objects = space.get(new ActualField("demandeMateriel"), new FormalField(DemandeMateriels.class));
                DemandeMateriels demande = (DemandeMateriels) objects[1];

                System.out.println("FournisseurMateriel " + id + ": Demande materiel recue pour " + demande.getNomProjet());

                // Different pricing strategy per supplier
                float budget = demande.getBudgetMax();
                float basePrice;
                if (id.equals("M0")) {
                    basePrice = budget * 0.90f;
                } else if (id.equals("M1")) {
                    basePrice = budget * 0.85f;
                } else {
                    basePrice = budget * 0.95f;
                }

                // Add small random variation
                float proposedPrice = basePrice * (0.98f + random.nextFloat() * 0.04f);

                OffreMateriel offre = new OffreMateriel(demande, new Date(), proposedPrice);

                System.out.println("FournisseurMateriel " + id + ": Proposition à " + proposedPrice);
                space.put("offreMateriel", offre, id);

                Object[] decisionObjects = space.get(new ActualField("decisionMateriel"), new ActualField(id),
                        new FormalField(Boolean.class));
                boolean approved = (Boolean) decisionObjects[2];

                if (approved) {
                    System.out.println("FournisseurMateriel " + id + ": Offre acceptée à " + proposedPrice);
                } else {
                    System.out.println("FournisseurMateriel " + id + ": Offre refusée");
                    Thread.sleep(random.nextInt(50));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}