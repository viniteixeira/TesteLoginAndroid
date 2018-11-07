package login.visaogrupo.login.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import login.visaogrupo.login.HTTPCallback;
import support.api.http.HTTP;
import support.api.utils.Criptho;


public class RequestTask extends AsyncTask<String, Void, Boolean> {

    public static final byte[] KEY_CRIPTY = "oSENHOR3oMeUp4sT0rN4DaMeF4Lt@ra!".getBytes();
    public static final byte[] IV_CRIPTY = "?S4LM0S_23:V3R_1".getBytes();

    private Context mContext;
    private String mUrl;
    private String mBody;
    private HTTPCallback mCallback;

    public RequestTask(Context context, String url, String body, HTTPCallback callback) {
        this.mContext = context;
        this.mUrl = url;
        this.mBody = body;
        this.mCallback = callback;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {

            HTTP http = new HTTP(mContext, mUrl);
            Criptho criptho = new Criptho(KEY_CRIPTY, IV_CRIPTY);
            String bodyEncripyted = criptho.encode(this.mBody, Criptho.BASE64_MODE);
            Log.i("URL: ", mUrl);
            Log.i("Body: ", mBody);
            Log.i("Body Encrypted:", bodyEncripyted);
            http.setTimeout(15000);
            http.connect(bodyEncripyted);
            String response = http.getRetorno();
            Log.i("Response:", response);
            mCallback.onSucess(response);

        } catch (Exception err) {
            err.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        //
    }
}

