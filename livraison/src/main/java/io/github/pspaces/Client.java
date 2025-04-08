package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

import java.util.Date;

public class Client implements Runnable {
    private Space space;

    public Client(Space space) {
        this.space = space;
    }

    @Override
    public void run() {
        try {
            String[] requirements = {"Resistance à la chaleur", "Matériaux durables"};
            Date deadlineDate = new Date(); // Current date
            AppelOffre appelOffre = new AppelOffre("Projet X", requirements, 10000.0f, deadlineDate, 100);

            space.put("appeloffre", appelOffre);
            System.out.println("Client: Appel d'offre envoye");

            // Wait for negotiations
            while (true) {
                Object[] negociationObjects = space.get(new ActualField("negociation"), new FormalField(Negotiation.class), new FormalField(String.class));
                Negotiation negociation = (Negotiation) negociationObjects[1];
                String fabricantId = (String) negociationObjects[2];

                float proposedPrice = negociation.getPrix();
                System.out.println("Client: Recu negociation de " + fabricantId + " avec prix de " + proposedPrice);

                // Simple decision rule: accept if price is less than original budget
                float priceThreshold = appelOffre.getCout() * 0.85f;
                boolean approved = proposedPrice <= priceThreshold;

                space.put("reponse", fabricantId, approved);

                if (approved) {
                    System.out.println("Client: Accepte la negotiation de Fabricant " + fabricantId);
                    break; // We found an acceptable offer
                } else {
                    System.out.println("Client: Refuse la negotiation de Fabricant " + fabricantId + " (prix trop eleve)");
                    System.out.println("---------------------------------------");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}