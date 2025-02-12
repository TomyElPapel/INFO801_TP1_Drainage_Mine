package io.github.pspaces;


public class Main {
    public static void main(String[] args) throws InterruptedException {



        Thread t = new Thread(new Environnement(Config.NIVEAU_EAU_DEPART));

        t.start();
        InterfaceConsole.start();

        t.join();

    }
}