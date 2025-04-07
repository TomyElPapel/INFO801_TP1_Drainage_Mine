package io.github.pspaces;

import org.jspace.SequentialSpace;
import org.jspace.Space;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Space space = new SequentialSpace();

        // Create and start the server thread
        Server server = new Server(space);
        Thread serverThread = new Thread(server);
        serverThread.start();

        // Create and start the client thread
        Client client = new Client(space);
        Thread clientThread = new Thread(client);
        clientThread.start();

        // Wait for both threads to finish
        serverThread.join();
        clientThread.join();

    }
}