package io.github.pspaces;


public class Environnement implements Runnable {

    private float _niveauEau = 10;

    private boolean _niveauEauMonte = false;
    private boolean _niveauEauBaisse = false;

    
 

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
                if (_niveauEauMonte) {
                    _niveauEau ++;
                }

                if (_niveauEauBaisse) {
                    _niveauEau --;
                }


                System.out.println("Niveau Eau " + _niveauEau);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void SetNiveauEauMonte(boolean v) {
        _singleton._niveauEauMonte = v;
    }

    public static void SetNiveauEauBaisse(boolean v) {
        _singleton._niveauEauBaisse = v;
    }

    public static boolean GetNiveauEauMonte() {
        return _singleton._niveauEauMonte;
    }

    public static boolean GetNiveauEauBaisse() {
        return _singleton._niveauEauBaisse ;
    }
}