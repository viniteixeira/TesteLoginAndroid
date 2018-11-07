package support.api.http;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;

import support.api.SupportBase;
import support.api.utils.LogTrace;

public class HTTP {

    private Context mContext;
    String urlString;
    private URL mURL;
    private String mRetorno = "";
    private String mParams = "";
    private HttpURLConnection mConnection;
    private InputStream mInputStream;
    private HashMap<String, String> mHeader;
    private Metodo mMetodo = Metodo.GET;
    private int mTimeout = 60000 * 5;

    public HTTP(Context context, String url) throws Exception {
        mContext = context;
        urlString = url;
        mURL = new URL(urlString);
        mHeader = new HashMap<>();
    }

    /**
     * Connecta na url sem mandar parametro
     * @throws Exception
     * @param map
     */

    /**
     * Connecta na url mandando um json
     *
     * @param jsonObject
     * @throws Exception
     */
    public void connect(JSONObject jsonObject) throws Exception {
        this.connect(jsonObject.toString());
    }

    /**
     * Connecta na url mandando um HasMap
     *
     * @param params
     * @throws Exception
     */
    public void connect(HashMap<String, Object> params) throws Exception {
        StringBuilder sbParam;
        int count = 0;
        String key;
        String value;

        sbParam = new StringBuilder();
        Iterator<String> interetor = params.keySet().iterator();
        while (interetor.hasNext()) {

            key = interetor.next();
            value = String.valueOf(params.get(key));

            count++;

            if (count == 1) {
                if (mMetodo.equals(Metodo.GET))
                    sbParam.append("?");
            } else
                sbParam.append("&");

            sbParam.append(key);
            sbParam.append("=");
            sbParam.append(value);

        }

        this.connect(sbParam.toString());
    }

    /**
     * Connecta na url manda uma string
     *
     * @param params
     * @throws Exception
     */
    public void connect(String params) throws Exception {
        Iterator<String> interetor;

        Log.i(this.getClass().getSimpleName(), params);

        try {
            //Verifica conexão
            if (!HTTP.isOnline(mContext))
                throw new NetworkErrorException("No momento não foi possível processar uma requisição troca de dados. Verifique sua conexão e tente novamente.");

            //Guarda params
            mParams = params;

            //Valida se tem parametro e se o metodo é GET
            if (!urlString.isEmpty() && mMetodo.equals(Metodo.GET))
                urlString += params;

            //Inicia URL
            mURL = new URL(urlString);

            Log.w("URL CONNECT",urlString);

            //Inicializa connection
            mConnection = (HttpURLConnection) mURL.openConnection();
            mConnection.setRequestMethod(mMetodo.toString());
            mConnection.setReadTimeout(mTimeout);
            mConnection.setConnectTimeout(mTimeout);


            //Monta header
            interetor = mHeader.keySet().iterator();
            while (interetor.hasNext()) {
                String key = interetor.next();
                String value = mHeader.get(key);


                mConnection.setRequestProperty(key, value);
            }

            //Valida se tem parametro e se o metodo é xxx
            if (!params.isEmpty() && mMetodo.equals(Metodo.POST))
                SupportBase.writeOutputStream(mConnection.getOutputStream(), params);

            //Conecta
            mConnection.connect();

            //Obtem retorno
            mInputStream = mConnection.getInputStream();
            mRetorno = SupportBase.readInputStream(mInputStream);
        } finally {
            if (mInputStream != null)
                mInputStream.close();
            if (mConnection != null)
                mConnection.disconnect();
        }
    }

    /**
     * Obtem uma imagemΩΩ
     *
     * @throws Exception
     */
    public Bitmap obtemImagem() throws Exception {
        return BitmapFactory.decodeStream(mURL.openStream());
    }

    /**
     * Obtem dados da url e grava em um arquivo
     */
    public void obtemFile(File file) throws Exception {
        InputStream input = null;
        FileOutputStream output = null;
        byte data[] = new byte[1024];
        int count;

        try {
            if (file == null)
                throw new InvalidParameterException("Parametro file não pode ser nulo");

            input = new BufferedInputStream(mURL.openStream());
            output = new FileOutputStream(file);

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
        } finally {
            if (input != null)
                input.close();

            if (output != null) {
                output.flush();
                output.close();
            }
        }
    }

    /**
     * Verifica conexão
     *
     * @param context
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public Metodo getMetodo() {
        return mMetodo;
    }

    public void setMetodo(Metodo metodo) {
        this.mMetodo = metodo;
    }

    /**
     * Adiciona header
     *
     * @param key
     * @param value
     * @return
     */
    public HTTP addHeader(String key, String value) {
        this.mHeader.put(key, value);
        return this;
    }

    /**
     * Seta Timeout
     *
     * @param mTimeout
     * @return
     */
    public HTTP setTimeout(int mTimeout) {
        this.mTimeout = mTimeout;
        return this;
    }

    public String getRetorno() {
        return mRetorno;
    }

    public String getParametros() {
        return mParams;
    }

    public HashMap<String, String> getHeader() {
        return mHeader;
    }

    public int getTimeout() {
        return mTimeout;
    }
}
