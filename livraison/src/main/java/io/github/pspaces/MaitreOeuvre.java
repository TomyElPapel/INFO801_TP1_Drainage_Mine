package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;
import org.jspace.SequentialSpace;

import java.util.Date;

public class MaitreOeuvre implements Runnable {
    private Space clientSpace;
    private Space fabricantSpace;

    public MaitreOeuvre(Space clientSpace, Space fabricantSpace) {
        this.clientSpace = clientSpace;
        this.fabricantSpace = fabricantSpace;
    }

    @Override
    public void run() {
        try {
            System.out.println("MaitreOeuvre: Waiting for appel d'offre...");
            // Get the appel d'offre data from space
            Object[] objects = clientSpace.get(new ActualField("appeloffre"), new FormalField(AppelOffre.class));

            AppelOffre appelOffre = (AppelOffre) objects[1];

            String nom = appelOffre.getNom();
            String[] requirements = appelOffre.getRequirements();
            float cout = appelOffre.getCout();
            Date date = appelOffre.getDate();
            int quantitee = appelOffre.getQuantitee();

            System.out.println("MaitreOeuvre: Appel d'offre reçu");
            System.out.println("Nom: " + nom);
            System.out.println("Requirements: " + String.join(", ", requirements));
            System.out.println("Coût: " + cout);
            System.out.println("Date: " + date);
            System.out.println("Quantité: " + quantitee);

            // Publier l'appel d'offre pour les fabricants
            System.out.println("MaitreOeuvre: Publishing appel d'offre to fabricants");
            fabricantSpace.put("appeloffre", appelOffre);

            // Attendre les négociations et les transmettre au client
            while (true) {
                Object[] negociationObjects = fabricantSpace.get(new ActualField("negociation"), new FormalField(Negotiation.class), new FormalField(String.class));
                Negotiation negociation = (Negotiation) negociationObjects[1];
                String fabricantId = (String) negociationObjects[2];

                System.out.println("MaitreOeuvre: Received negotiation from Fabricant " + fabricantId);

                // Transférer la négociation au client
                clientSpace.put("negociation", negociation, fabricantId);

                // Attendre la réponse du client
                Object[] responseObjects = clientSpace.get(new ActualField("reponse"), new ActualField(fabricantId), new FormalField(Boolean.class));
                boolean approved = (Boolean) responseObjects[2];

                // Informer le fabricant de la décision
                fabricantSpace.put("decision", fabricantId, approved);

                if (approved) {
                    System.out.println("MaitreOeuvre: Client approved negotiation from Fabricant " + fabricantId);
                    break; // Une négociation a été approuvée, on arrête le processus
                } else {
                    System.out.println("MaitreOeuvre: Client rejected negotiation from Fabricant " + fabricantId);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}