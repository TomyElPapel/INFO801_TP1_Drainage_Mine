package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class Ventilateur  implements Runnable {
    private Space space;
    private boolean active = false;

    Ventilateur(Space space) {
        this.space = space;
    }

    boolean sendToEnv(boolean b) {
        if (b != active) {
            Environnement.SetVentilateurActive(b);
            active = b;
            return true;
        }
        return false;
    }

    void ventilation() {
        if (sendToEnv(true)) {
            System.out.println("Ventilateur: Je commence à ventiler");
        }
    }

    Object[] getActivationVentilateur() {
        try {
            Object[] b = space.queryp(new ActualField("activation_ventilateur"));
            return b;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void run() {
        System.out.println("Ventilateur: Je suis en marche");
        try{
            while ((space.queryp(new ActualField("STOP")) == null)) {               // Attends un signal d'arrêt
                while (getActivationVentilateur() != null) {                              // Tant que le signal d'activation est présent
                    ventilation();                                                        // Ventile
                }
                if (sendToEnv(false)) {
                    System.out.println("Ventilateur: Je m'arrête");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
