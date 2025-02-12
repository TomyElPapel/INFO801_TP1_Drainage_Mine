package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class CapteurH2OBas  implements Runnable {
    private Space space;

    CapteurH2OBas(Space space) {
        this.space = space;
    }

    @Override
    public void run() {
        System.out.println("CapteurH2OBas: Je suis en marche");
        try {
            while ((space.queryp(new ActualField("STOP")) == null)) {
                if ((Environnement.getNiveauEau() <= Config.SEUIL_EAU_BAS) && (space.queryp(new ActualField("activation_pompe")) != null)) {
                    space.get(new ActualField("activation_pompe"));
                    System.out.println("CapteurH2OBas: J'ai détecté un niveau d'eau Bas");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}