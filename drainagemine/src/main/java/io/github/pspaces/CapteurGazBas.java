package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class CapteurGazBas  implements Runnable {
    private Space space;

    CapteurGazBas(Space space) {
        this.space = space;
    }

    boolean is_gaz_below() {
        return (Environnement.getNiveauMonoxydeCarbone() <= Config.SEUIL_MONOXYDE_CARBONE_BAS) && (Environnement.getNiveauMethane() <= Config.SEUIL_METHANE_BAS);
    }

    @Override
    public void run() {
        System.out.println("CapteurGazBas: Je suis en marche");
        try {
            while ((space.queryp(new ActualField("STOP")) == null)) {
                if (is_gaz_below() && (space.queryp(new ActualField("activation_ventilateur")) != null)) {
                    space.get(new ActualField("activation_ventilateur"));
                    System.out.println("CapteurGazBas: J'ai détecté un niveau de gaz Bas");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}