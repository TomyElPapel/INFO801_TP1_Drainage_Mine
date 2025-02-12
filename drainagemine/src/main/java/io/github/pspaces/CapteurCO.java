package io.github.pspaces;

import sun.jvm.hotspot.gc.shared.Space;

public class CapteurCO  implements Runnable {
    private Space space;

    CapteurCO(Space space) {
        this.space = space;
    }

    @Override
    public void run() {

    }
}