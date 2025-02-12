package io.github.pspaces;


public class Environnement implements Runnable {

    private float _niveauEau = 10;

    private boolean _pompeActive = false; 

    static private Environnement _singleton;

    public Environnement(float niveauEau) {
        this._niveauEau = niveauEau;
    }


    static public float getNiveauEau() {
        return _singleton._niveauEau;
    }

    @Override
    public void run() {
        _singleton = this;


        while (true) {

            try {
                Thread.sleep(1000);
                _niveauEau ++;
                if (_pompeActive) {
                    _niveauEau -= 4;
                }
                System.out.println("Niveau Eau " + _niveauEau);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void SetPompeActive(boolean v) {
        _singleton._pompeActive = v;
    }
}