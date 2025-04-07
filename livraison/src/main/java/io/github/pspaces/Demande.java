package io.github.pspaces;

import java.util.Date;

public class Demande {
    private String requirements;
    private double cost;
    private int time;
    private int quantity;
    private Date date;

    public Demande(String requirements, double cost, int time, int quantity) {
        this.requirements = requirements;
        this.cost = cost;
        this.time = time;
        this.quantity = quantity;
    }

    public String getRequirements() {
        return requirements;
    }

    public double getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }

    public int getQuantity() {
        return quantity;
    }
}