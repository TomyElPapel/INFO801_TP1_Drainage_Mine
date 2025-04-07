package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;
import java.util.Date;

import java.util.Date;
import java.util.Random;

public class Fabricant implements Runnable {
    private Space space;
    private String id;
    private Random random;

    public Fabricant(Space space, String id) {
        this.space = space;
        this.id = id;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            System.out.println("Fabricant " + id + ": Waiting for appel d'offre...");
            // Get the appel d'offre data from space
            Object[] objects = space.get(new ActualField("appeloffre"), new FormalField(AppelOffre.class));
            AppelOffre appelOffre = (AppelOffre) objects[1];

            System.out.println("Fabricant " + id + ": Received appel d'offre - " + appelOffre.getNom());

            // Create negotiation response (with some randomized values)
            float proposedPrice = appelOffre.getCout() * (0.8f + random.nextFloat() * 0.4f); // 80% to 120% of original price
            Negotiation negotiation = new Negotiation(appelOffre, new Date(), proposedPrice);

            System.out.println("Fabricant " + id + ": Proposing price of " + proposedPrice);
            space.put("negociation", negotiation, id);

            // Wait for client decision
            Object[] decisionObjects = space.get(new ActualField("decision"), new ActualField(id), new FormalField(Boolean.class));
            boolean approved = (Boolean) decisionObjects[2];

            if (approved) {
                System.out.println("Fabricant " + id + ": Negotiation approved! Starting production.");
            } else {
                System.out.println("Fabricant " + id + ": Negotiation rejected.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}