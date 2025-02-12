package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class Pompe implements Runnable {
    private Space space;

    Pompe(Space space) {
        this.space = space;
    }

    void pompage() {
        System.out.println("Pompe: Je pompe");
    }

    @Override
    public void run() {
        System.out.println("Pompe: Je suis en marche");
        try{
            while ((space.queryp(new ActualField("STOP")) == null)) {               // Attends un signal d'arrêt
                while (space.query(new ActualField("activation_pompe")) != null) {  // Tant que le signal d'activation est présent
                    pompage();                                                            // Pompe
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
