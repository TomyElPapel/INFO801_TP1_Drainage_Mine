package io.github.pspaces;

import sun.jvm.hotspot.gc.shared.Space;

public class CapteurH2OBas  implements Runnable {
    private Space space;

    CapteurH2OBas(Space space) {
        this.space = space;
    }

    @Override
    public void run() {

    }
}