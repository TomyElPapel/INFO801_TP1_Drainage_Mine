package io.github.pspaces;

import org.jspace.SequentialSpace;
import org.jspace.Space;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Space space = new SequentialSpace();

        Thread env = new Thread(new Environnement(Config.NIVEAU_EAU_DEPART));
        Thread pompe = new Thread( new Pompe(space) );


        env.start();
        env.start();



        // Signal stop pour arrêter tous les threads
        // space.put("STOP");


        
        InterfaceConsole.start();


        env.join();
        pompe.join();

    }
}