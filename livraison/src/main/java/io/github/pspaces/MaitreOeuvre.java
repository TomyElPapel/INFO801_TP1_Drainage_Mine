package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

import java.util.Date;

public class MaitreOeuvre implements Runnable {
    private Space clientSpace;
    private Space fabricantSpace;
    private static final int MAX_ITERATIONS = 10;

    public MaitreOeuvre(Space clientSpace, Space fabricantSpace) {
        this.clientSpace = clientSpace;
        this.fabricantSpace = fabricantSpace;
    }

    @Override
    public void run() {
        try {
            System.out.println("MaitreOeuvre: Waiting for appel d'offre...");
            // Get the appel d'offre data from client
            Object[] objects = clientSpace.get(new ActualField("appeloffre"), new FormalField(AppelOffre.class));
            AppelOffre appelOffre = (AppelOffre) objects[1];

            System.out.println("MaitreOeuvre: Appel d'offre reçu");
            System.out.println("Nom: " + appelOffre.getNom());
            System.out.println("Requirements: " + String.join(", ", appelOffre.getRequirements()));
            System.out.println("Coût: " + appelOffre.getCout());
            System.out.println("Date: " + appelOffre.getDate());
            System.out.println("Quantité: " + appelOffre.getQuantitee());

            boolean negotiationComplete = false;
            int iterations = 0;

            while (!negotiationComplete && iterations < MAX_ITERATIONS) {
                iterations++;

                // Publish appel d'offre to fabricants
                System.out.println("MaitreOeuvre: Publication de l'appel d'offre pour tous les fabricants");
                fabricantSpace.put("appeloffre", appelOffre);

                // Wait for a negotiation from any fabricant
                Object[] negociationObjects = fabricantSpace.get(
                        new ActualField("negociation"),
                        new FormalField(Negotiation.class),
                        new FormalField(String.class)
                );

                Negotiation negotiation = (Negotiation) negociationObjects[1];
                String fabricantId = (String) negociationObjects[2];

                System.out.println("MaitreOeuvre: Recu negociation de Fabricant " + fabricantId);

                // Pass negotiation to client
                clientSpace.put("negociation", negotiation, fabricantId);

                // Wait for client response
                Object[] responseObjects = clientSpace.get(
                        new ActualField("reponse"),
                        new ActualField(fabricantId),
                        new FormalField(Boolean.class)
                );

                boolean approved = (Boolean) responseObjects[2];

                // Inform fabricant of decision
                fabricantSpace.put("decision", fabricantId, approved);

                if (approved) {
                    System.out.println("MaitreOeuvre: Client accepte l'appel d'offre de Fabricant " + fabricantId);
                    negotiationComplete = true; // End the process
                } else {
                    System.out.println("MaitreOeuvre: Client refuse l'appel d'offre de Fabricant " + fabricantId);
                    // Continue to next iteration where we'll post the appel d'offre again
                }
            }

            if (!negotiationComplete) {
                System.out.println("MaitreOeuvre: Maximum tentative atteint, arret de la negotiation");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}