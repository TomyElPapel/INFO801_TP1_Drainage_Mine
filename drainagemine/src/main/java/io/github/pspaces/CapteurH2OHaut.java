package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class CapteurH2OHaut  implements Runnable {
    private Space space;

    CapteurH2OHaut(Space space) {
        this.space = space;
    }

    @Override
    public void run() {
        System.out.println("CapteurH2OHaut: Je suis en marche");
        try {
            while ((space.queryp(new ActualField("STOP")) == null)) {
                if ((Environnement.getNiveauEau() >= Config.SEUIL_EAU_HAUT) && (space.queryp(new ActualField("activation_pompe")) == null)) {
                    space.put("activation_pompe");
                    System.out.println("CapteurH2OHaut: J'ai détecté un niveau d'eau haut");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}