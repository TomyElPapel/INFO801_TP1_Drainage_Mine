package io.github.pspaces;

import org.jspace.Space;

public class Client implements Runnable {
    private Space space;

    public Client(Space space) {
        this.space = space;
    }

    @Override
    public void run() {
        try {
            Demande demande = new Demande("Technical specifications", 10000.0, 30, 100);
            // Explicitly use wrapper classes for primitive values
            space.put("demande", demande.getRequirements(),
                    Double.valueOf(demande.getCost()),
                    Integer.valueOf(demande.getTime()),
                    Integer.valueOf(demande.getQuantity()));
            System.out.println("Client: Demande envoyée");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}