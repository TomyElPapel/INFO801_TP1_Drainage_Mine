package io.github.pspaces;

import org.jspace.SequentialSpace;
import org.jspace.Space;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Space space = new SequentialSpace();

        Thread env = new Thread(new Environnement(Config.NIVEAU_EAU_DEPART));
        Thread pompe = new Thread( new Pompe(space) );
        Thread capteurH2OHaut = new Thread( new CapteurH2OHaut(space) );
        Thread capteurH2OBas = new Thread( new CapteurH2OBas(space) );


        env.start();
        pompe.start();
        capteurH2OHaut.start();
        capteurH2OBas.start();



        // Signal stop pour arrêter tous les threads
        // space.put("STOP");


        
        InterfaceConsole.start();


        env.join();
        pompe.join();
        capteurH2OHaut.join();
        capteurH2OBas.join();
    }
}