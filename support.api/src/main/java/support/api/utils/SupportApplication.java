package support.api.utils;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import support.api.SupportBase;

public class SupportApplication extends MultiDexApplication {

    @Override
    public void onCreate() {

        //Instalação do multiDex
        this.installMultiDex();

        super.onCreate();

        SupportBase.Context = this;
    }

    private void installMultiDex() {
        try {
            MultiDex.install(this);
        }
        catch (Throwable err) {
            Log.e(SupportApplication.class.getSimpleName(), "Erro ao instalar multiDex");
            LogTrace.logCatch(this, this.getClass(), err, false);
        }
    }
}
