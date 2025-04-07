package io.github.pspaces;

import org.jspace.SequentialSpace;
import org.jspace.Space;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Space space = new SequentialSpace();

        MaitreOeuvre maitreOeuvre = new MaitreOeuvre(space);
        Thread maitreOeuvreThread = new Thread(maitreOeuvre);
        maitreOeuvreThread.start();

        Client client = new Client(space);
        Thread clientThread = new Thread(client);
        clientThread.start();

        // Wait for both threads to finish
        maitreOeuvreThread.join();
        clientThread.join();
    }
}