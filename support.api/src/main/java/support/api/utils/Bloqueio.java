package support.api.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import support.api.http.HTTP;

public class Bloqueio extends AsyncTask<Void, Void, Boolean> {

    private static final String PREFS = "PREFS_BLOQUEIO";
    private static final String PREFS_BLOQUEADO = Bloqueio.PREFS + "_BLOQUEADO";

    public static final String URL = "https://protected-lowlands-9644.herokuapp.com/listadecontatos/";

    private Context mContext;
    private String mAPPID;

    public Bloqueio(Context context, String appID) {
        mContext = context;
        mAPPID = appID;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        HTTP http;
        JSONObject retorno;
        int bloqueado = 0;
        int tentativas = 0;

        //Enquanto não for bloqueado
        while (bloqueado == 0) {
            try {

                //Espera de 5seg
                Thread.sleep(5000);

                //Verifica se esta online
                if (HTTP.isOnline(mContext)) {

                    //Conecta do service de bloqueio
                    http = new HTTP(mContext, Bloqueio.URL + mAPPID);
//                    http.connect();

                    //Verifica se esta vindo retorno
                    if (!http.getRetorno().isEmpty()) {

                        //Recebe retorno
                        retorno = new JSONObject(http.getRetorno());
                        bloqueado = SupportJson.getInt(retorno, "bloqueado", 0);

                        Log.i(this.getClass().getSimpleName(), retorno.toString());

                        //Salvar na prefs
                        this.bloquear(bloqueado == 1);
                    }
                }
            }
            catch (Exception err) {
                LogTrace.logCatch(mContext, this.getClass(), err);
            }

            //Verifica se esta bloquiado na prefs depois de 5 tentativas
            if (tentativas >= 5 && this.isBloqueado())
                bloqueado = 1;

            tentativas++;
        }

        return bloqueado == 1;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        Intent intent;

        try {

            if (aBoolean) {

//                //Bloqueia
//                this.bloquear(true);

                //Inicia tela de bloqueio
                intent = new Intent(mContext, TelaBloqueio.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        }
        catch (Exception err) {
            LogTrace.logCatch(mContext, this.getClass(), err, true);
        }
    }

    /**
     * Salva bloqueio
     */
    private void bloquear(boolean bloqueado) {
        SharedPreferences preferences = mContext.getSharedPreferences(Bloqueio.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(Bloqueio.PREFS_BLOQUEADO, bloqueado);

        editor.apply();
    }

    /**
     * Salva bloqueio
     */
    private boolean isBloqueado() {
        SharedPreferences preferences = mContext.getSharedPreferences(Bloqueio.PREFS, Context.MODE_PRIVATE);

        return preferences.getBoolean(Bloqueio.PREFS_BLOQUEADO, false);
    }
}
