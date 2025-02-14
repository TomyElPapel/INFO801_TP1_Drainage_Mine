package io.github.pspaces;


public class Environnement implements Runnable {

    private float _niveauEau = 10;
    private float _niveauMonoxydeCarbone = 10;
    private float _niveauMethane = 10;

    private boolean _pompeActive = false;
    private boolean _ventilateurActive = false;

    static private Environnement _singleton;

    public Environnement(float niveauEau) {
        this._niveauEau = niveauEau;
    }


    static public float getNiveauEau() {
        return _singleton._niveauEau;
    }

    static public float getNiveauMonoxydeCarbone() {
        return _singleton._niveauMonoxydeCarbone;
    }


    static public float getNiveauMethane() {
        return _singleton._niveauMethane;
    }

    @Override
    public void run() {
        _singleton = this;


        while (true) {

            try {
                Thread.sleep(1000);
                _niveauEau ++;
                _niveauMethane ++;
                _niveauMonoxydeCarbone += 2;
                if (_pompeActive) {
                    _niveauEau -= 3;
                }

                if (_ventilateurActive) {
                    _niveauMethane -= 5;
                    _niveauMonoxydeCarbone -= 3;
                }

                if (_niveauEau < 0) {
                    _niveauEau = 0;
                }

                if (_niveauMethane < 0) {
                    _niveauMethane = 0;
                }

                if (_niveauMonoxydeCarbone < 0) {
                    _niveauMonoxydeCarbone = 0;
                }

                System.out.println("Niveau Eau " + _niveauEau + " | " + "Monoxyde de Carbone " + _niveauMonoxydeCarbone + " | " + "Methane " + _niveauMethane);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void SetPompeActive(boolean v) {
        _singleton._pompeActive = v;
    }

    public static void SetVentilateurActive(boolean v) {
        _singleton._ventilateurActive = v;
    }
}