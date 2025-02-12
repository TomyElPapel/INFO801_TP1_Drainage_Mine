package io.github.pspaces;

import sun.jvm.hotspot.gc.shared.Space;

public class CapteurCH4  implements Runnable {
    private Space space;

    CapteurCH4(Space space) {
        this.space = space;
    }

    @Override
    public void run() {

    }
}