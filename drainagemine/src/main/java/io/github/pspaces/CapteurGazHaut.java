package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class CapteurGazHaut  implements Runnable {
    private Space space;

    CapteurGazHaut(Space space) {
        this.space = space;
    }

    boolean is_gaz_above() {
        return (Environnement.getNiveauMonoxydeCarbone() >= Config.SEUIL_MONOXYDE_CARBONE_HAUT) || (Environnement.getNiveauMethane() >= Config.SEUIL_METHANE_HAUT);
    }

    @Override
    public void run() {
        System.out.println("CapteurGazHaut: Je suis en marche");
        try {
            while ((space.queryp(new ActualField("STOP")) == null)) {
                if (is_gaz_above() && (space.queryp(new ActualField("activation_ventilateur")) == null)) {
                    space.put("activation_ventilateur");
                    System.out.println("CapteurGazHaut: J'ai détecté un niveau de gaz haut");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}