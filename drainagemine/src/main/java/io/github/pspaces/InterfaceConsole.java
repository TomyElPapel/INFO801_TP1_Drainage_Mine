package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;
import java.sql.Time;

public class InterfaceConsole {
    private Space space;
    InterfaceConsole(Space space) {
        this.space = space;
    }

    private String printLoadingBar(float actualLevel, float floorVal, float cellingVal) {
        return printLoadingBar(actualLevel, floorVal, cellingVal, 20, 100);
    }

    private String printLoadingBar(float actualLevel, float floorVal, float cellingVal, int barLength, int barMax) {

        int actualBarLength = (int) (actualLevel * barLength / barMax);
        String res = "";
        if(actualLevel < floorVal) {
            res += ConsoleColor.TEXT_GREEN;
        } else if(actualLevel > cellingVal) {
            res += ConsoleColor.TEXT_RED;
        } else {
            res += ConsoleColor.TEXT_GREEN;
        }
        res += "[";
        for (int i = 0; i < barLength; i++) {
            if(i == (int) (barLength * (floorVal / barMax)) || i == (int) (barLength * (cellingVal / barMax))) {
                res += "|";
            }
            else if (i < actualBarLength) {
                res += "=";
            } else {
                res += " ";
            }
        }
        res += "]" + ConsoleColor.TEXT_RESET;
        return res;
    }

    public void printMineStatus() throws InterruptedException {

        System.out.println("\n\n\n+----------------------------------------------+");
        System.out.println("| Niveau CO:  " + printLoadingBar(Environnement.getNiveauMonoxydeCarbone(), Config.SEUIL_MONOXYDE_CARBONE_BAS, Config.SEUIL_MONOXYDE_CARBONE_HAUT, 30, 62) + " | Niveau: "+Environnement.getNiveauMethane()+", SBas: "+Config.SEUIL_METHANE_BAS+", SHaut: "+Config.SEUIL_METHANE_HAUT);
        System.out.println("| Niveau CH4: " + printLoadingBar(Environnement.getNiveauMethane(), Config.SEUIL_METHANE_BAS, Config.SEUIL_METHANE_HAUT, 30, 75) + " | Niveau: "+Environnement.getNiveauMonoxydeCarbone()+", SBas: "+Config.SEUIL_MONOXYDE_CARBONE_BAS+", SHaut: "+Config.SEUIL_MONOXYDE_CARBONE_HAUT);
        System.out.println("| Niveau H20: " + printLoadingBar(Environnement.getNiveauEau(), Config.SEUIL_EAU_BAS, Config.SEUIL_EAU_HAUT, 30, 25) + " | Niveau: "+Environnement.getNiveauEau()+", SBas: "+Config.SEUIL_EAU_BAS+", SHaut: "+Config.SEUIL_EAU_HAUT);
        System.out.println("+----------------------+-----------------------+");
        System.out.println("| Pompe:       " + ((space.queryp(new ActualField("activation_pompe")) != null) && (space.queryp(new ActualField("activation_ventilateur")) == null) ? ConsoleColor.TEXT_GREEN + "Actif  " : ConsoleColor.TEXT_RED + "Inactif") + ConsoleColor.TEXT_RESET + " |");
        System.out.println("| Ventilateur: " + ((space.queryp(new ActualField("activation_ventilateur")) != null) ? ConsoleColor.TEXT_GREEN + "Actif  " : ConsoleColor.TEXT_RED + "Inactif") + ConsoleColor.TEXT_RESET + " |");
        System.out.println("+----------------------+");
    }

    public void start() throws InterruptedException {
        // try (Scanner in = new Scanner(System.in)) {
        //     while (true) {
        //         int v = in.nextInt();

        //         if (v == 1) {
        //             Environnement.SetNiveauEauBaisse(!Environnement.GetNiveauEauBaisse());
        //         }


        //         if (v == 0) {
        //             Environnement.SetNiveauEauMonte(!Environnement.GetNiveauEauMonte());
        //         }
        //     }
        // }
        Time lastTime = new Time(System.currentTimeMillis());
        while (true)
        {
            Time currentTime = new Time(System.currentTimeMillis());
            long deltaTime = currentTime.getTime() - lastTime.getTime();

            if (deltaTime >= 1000) {
                // System.out.print("\033[H\033[2J");

                printMineStatus();

                lastTime = currentTime;
            }
        }
    }
}
