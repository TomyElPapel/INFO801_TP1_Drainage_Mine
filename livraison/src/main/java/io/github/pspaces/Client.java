package io.github.pspaces;

import org.jspace.ActualField;
import org.jspace.Space;

public class Client implements Runnable{
    private Space space;

    public Client(Space space) {
        this.space = space;
    }

    @Override
    public void run() {

    }
}