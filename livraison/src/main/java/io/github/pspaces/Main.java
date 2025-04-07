package io.github.pspaces;

import org.jspace.SequentialSpace;
import org.jspace.Space;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Space clientSpace = new SequentialSpace();
        Space fabricantSpace = new SequentialSpace();

        MaitreOeuvre maitreOeuvre = new MaitreOeuvre(clientSpace, fabricantSpace);
        Thread maitreOeuvreThread = new Thread(maitreOeuvre);
        maitreOeuvreThread.start();

        // Create fabricants
        Thread[] fabricantThreads = new Thread[3];
        for (int i = 0; i < fabricantThreads.length; i++) {
            Fabricant fabricant = new Fabricant(fabricantSpace, "F" + i);
            fabricantThreads[i] = new Thread(fabricant);
            fabricantThreads[i].start();
        }

        Client client = new Client(clientSpace);
        Thread clientThread = new Thread(client);
        clientThread.start();

        // Wait for client thread to finish
        clientThread.join();
    }
}