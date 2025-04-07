package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class MaitreOeuvre implements Runnable {
    private Space space;

    public MaitreOeuvre(Space space) {
        this.space = space;
    }

    @Override
    public void run() {
        try {
            System.out.println("MaitreOeuvre: Waiting for demande...");
            Object[] demandeData = space.get(new ActualField("demande"), new ActualField(String.class), new ActualField(Double.class), new ActualField(Integer.class), new ActualField(Integer.class));
            String requirements = (String) demandeData[1];
            double cost = (Double) demandeData[2];
            int time = (Integer) demandeData[3];
            int quantity = (Integer) demandeData[4];

            System.out.println("MaitreOeuvre: Demande reçue");
            System.out.println("Requirements: " + requirements);
            System.out.println("Cost: " + cost);
            System.out.println("Time: " + time);
            System.out.println("Quantity: " + quantity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}