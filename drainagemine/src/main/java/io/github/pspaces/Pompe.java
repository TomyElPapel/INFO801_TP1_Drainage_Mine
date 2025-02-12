package io.github.pspaces;

import org.jspace.Space;

public class Pompe implements Runnable {
    private Space space;

    Pompe(Space space) {
        this.space = space;
    }

    @Override
    public void run() {

    }
}
