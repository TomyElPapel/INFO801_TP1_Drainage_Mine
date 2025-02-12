package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class Pompe implements Runnable {
    private Space space;
    private boolean active = false;

    Pompe(Space space) {
        this.space = space;
    }

    boolean sendToEnv(boolean b) {
        if (b != active) {
            Environnement.SetPompeActive(b);
            active = b;
            return true;
        }
        return false;
    }

    void pompage() {
        if (sendToEnv(true)) {
            System.out.println("Pompe: Je commence à pomper");
        }
    }

    Object[] getActivationPompe() {
        try {
            Object[] b = space.queryp(new ActualField("activation_pompe"));
            return b;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        System.out.println("Pompe: Je suis en marche");
        try{
            while ((space.queryp(new ActualField("STOP")) == null)) {               // Attends un signal d'arrêt
                while (getActivationPompe() != null) {                                    // Tant que le signal d'activation est présent
                    pompage();                                                            // Pompe

                }
                if (sendToEnv(false)) {
                    System.out.println("Pompe: Je m'arrête");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
