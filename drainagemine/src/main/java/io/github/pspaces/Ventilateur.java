package io.github.pspaces;

import org.jspace.Space;

public class Ventilateur  implements Runnable {
    private Space space;

    Ventilateur(Space space) {
        this.space = space;
    }

    @Override
    public void run() {

    }
}
