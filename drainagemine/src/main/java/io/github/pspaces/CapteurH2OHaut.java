package io.github.pspaces;

import sun.jvm.hotspot.gc.shared.Space;

public class CapteurH2OHaut  implements Runnable {
    private Space space;

    CapteurH2OHaut(Space space) {
        this.space = space;
    }

    @Override
    public void run() {

    }
}