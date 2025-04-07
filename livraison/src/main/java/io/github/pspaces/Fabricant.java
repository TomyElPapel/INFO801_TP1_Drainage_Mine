package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

import java.util.Date;
import java.util.Random;

public class Fabricant implements Runnable {
    private Space space;
    private String id;
    private Random random = new Random();

    public Fabricant(Space space, String id) {
        this.space = space;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Fabricant " + id + ": Waiting for appel d'offre...");

                // Get the appel d'offre - only one fabricant will succeed
                Object[] objects = space.get(new ActualField("appeloffre"), new FormalField(AppelOffre.class));
                AppelOffre appelOffre = (AppelOffre) objects[1];

                System.out.println("Fabricant " + id + ": Successfully grabbed the appel d'offre");

                // Generate an offer price based on the budget
                float budget = appelOffre.getCout();

                // Different pricing strategy per fabricant
                float basePrice;
                if (id.equals("F0")) {
                    basePrice = budget * 0.90f;
                } else if (id.equals("F1")) {
                    basePrice = budget * 0.85f;
                } else {
                    basePrice = budget * 0.95f;
                }

                // Add small random variation
                float proposedPrice = basePrice * (0.98f + random.nextFloat() * 0.04f);

                // Create negotiation object
                Negotiation negotiation = new Negotiation(appelOffre, new Date(), proposedPrice);

                System.out.println("Fabricant " + id + ": Submitting offer of " + proposedPrice);
                space.put("negociation", negotiation, id);

                // Wait for decision
                Object[] decisionObjects = space.get(new ActualField("decision"), new ActualField(id),
                        new FormalField(Boolean.class));
                boolean approved = (Boolean) decisionObjects[2];

                if (approved) {
                    System.out.println("Fabricant " + id + ": Offer accepted! Contract won at " + proposedPrice);
                    // We could add contract execution logic here
                } else {
                    System.out.println("Fabricant " + id + ": Offer rejected. Waiting for next opportunity.");
                    // Small random delay to avoid one fabricant always winning
                    Thread.sleep(random.nextInt(50));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}