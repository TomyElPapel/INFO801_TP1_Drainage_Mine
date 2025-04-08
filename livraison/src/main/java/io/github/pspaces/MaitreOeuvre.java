package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

import java.util.Date;

public class MaitreOeuvre implements Runnable {
    private Space clientSpace;
    private Space fabricantSpace;
    private Space fournisseurSpace;
    private static final int MAX_ITERATIONS = 10;

    public MaitreOeuvre(Space clientSpace, Space fabricantSpace, Space fournisseurSpace) {
        this.clientSpace = clientSpace;
        this.fabricantSpace = fabricantSpace;
        this.fournisseurSpace = fournisseurSpace;
    }

    @Override
    public void run() {
        try {
            System.out.println("MaitreOeuvre: Waiting for appel d'offre...");
            Object[] objects = clientSpace.get(new ActualField("appeloffre"), new FormalField(AppelOffre.class));
            AppelOffre appelOffre = (AppelOffre) objects[1];

            System.out.println("MaitreOeuvre: Appel d'offre reçu");
            System.out.println("Nom: " + appelOffre.getNom());
            System.out.println("Requirements: " + String.join(", ", appelOffre.getRequirements()));
            System.out.println("Coût: " + appelOffre.getCout());
            System.out.println("Date: " + appelOffre.getDate());
            System.out.println("Quantité: " + appelOffre.getQuantitee());

            // Phase 1: Negotiation with Fabricants
            boolean fabricantNegotiationComplete = false;
            int iterations = 0;
            String successfulFabricant = null;
            float fabricantPrice = 0;

            while (!fabricantNegotiationComplete && iterations < MAX_ITERATIONS) {
                iterations++;

                System.out.println("MaitreOeuvre: Publication de l'appel d'offre pour tous les fabricants");
                fabricantSpace.put("appeloffre", appelOffre);

                Object[] negociationObjects = fabricantSpace.get(
                        new ActualField("negociation"),
                        new FormalField(Negotiation.class),
                        new FormalField(String.class)
                );

                Negotiation negotiation = (Negotiation) negociationObjects[1];
                String fabricantId = (String) negociationObjects[2];
                fabricantPrice = negotiation.getPrix();

                System.out.println("MaitreOeuvre: Recu negociation de Fabricant " + fabricantId);
                clientSpace.put("negociation", negotiation, fabricantId);

                Object[] responseObjects = clientSpace.get(
                        new ActualField("reponse"),
                        new ActualField(fabricantId),
                        new FormalField(Boolean.class)
                );

                boolean approved = (Boolean) responseObjects[2];
                fabricantSpace.put("decision", fabricantId, approved);

                if (approved) {
                    System.out.println("MaitreOeuvre: Client accepte l'appel d'offre de Fabricant " + fabricantId);
                    fabricantNegotiationComplete = true;
                    successfulFabricant = fabricantId;
                } else {
                    System.out.println("MaitreOeuvre: Client refuse l'appel d'offre de Fabricant " + fabricantId);
                }
            }

            if (!fabricantNegotiationComplete) {
                System.out.println("MaitreOeuvre: Maximum tentative atteint, arret de la negotiation");
                return;
            }

            // Phase 2: Negotiation with Material Suppliers
            System.out.println("\n==========================================");
            System.out.println("MaitreOeuvre: Démarrage négociation matériaux avec le Fabricant " + successfulFabricant);

            String[] materialsNeeded = {"Acier", "Aluminium", "Plastique"};
            float materialBudget = fabricantPrice * 0.7f; // 70% of fabricant price
            Date materialDeadline = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000); // One week later
            DemandeMateriels demandeMateriel = new DemandeMateriels(
                    appelOffre.getNom(),
                    materialsNeeded,
                    appelOffre.getQuantitee(),
                    materialBudget,
                    materialDeadline
            );

            boolean materialNegotiationComplete = false;
            iterations = 0;

            while (!materialNegotiationComplete && iterations < MAX_ITERATIONS) {
                iterations++;

                System.out.println("MaitreOeuvre: Publication de la demande de materiel");
                System.out.println("Budget materiel: " + materialBudget);
                fournisseurSpace.put("demandeMateriel", demandeMateriel);

                Object[] offerObjects = fournisseurSpace.get(
                        new ActualField("offreMateriel"),
                        new FormalField(OffreMateriel.class),
                        new FormalField(String.class)
                );

                OffreMateriel offreMateriel = (OffreMateriel) offerObjects[1];
                String fournisseurId = (String) offerObjects[2];
                float materialPrice = offreMateriel.getPrixPropose();

                System.out.println("MaitreOeuvre: Recu offre de " + fournisseurId + " au prix de " + materialPrice);

                // Accept if price is below 90% of budget
                float materialThreshold = materialBudget * 0.9f;
                boolean materialApproved = materialPrice <= materialThreshold;

                fournisseurSpace.put("decisionMateriel", fournisseurId, materialApproved);

                if (materialApproved) {
                    System.out.println("MaitreOeuvre: Accepte l'offre de " + fournisseurId);
                    materialNegotiationComplete = true;
                } else {
                    System.out.println("MaitreOeuvre: Refuse l'offre de " + fournisseurId);
                    System.out.println("---------------------------------------");
                }
            }

            if (!materialNegotiationComplete) {
                System.out.println("MaitreOeuvre: Maximum tentative atteint pour les materiaux");
            } else {
                System.out.println("MaitreOeuvre: Projet complet - fabrication et materiaux acquis");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}