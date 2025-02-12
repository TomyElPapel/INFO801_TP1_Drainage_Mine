package io.github.pspaces;

import org.jspace.SequentialSpace;
import org.jspace.Space;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException throws InterruptedException {



        Thread t = new Thread(new Environnement(Config.NIVEAU_EAU_DEPART));

        t.start();
        InterfaceConsole.start();

        t.join();

        Space space = new SequentialSpace();

        Thread t1 = new Thread( new Pompe(space) );
        t1.start();

        // Signal stop pour arrêter tous les threads
        space.put("STOP");

        t1.join();
    }
}