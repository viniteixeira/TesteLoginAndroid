package support.api.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.HashMap;

//import support.api.R;
import support.api.R;
import support.api.SupportBase;

public class Loading extends AppCompatActivity {

    public interface Listener {
        void onStart();
        void onResume();
        void onPause();
        void onFinish();
    }

    private static Loading loading;
    private static ProgressWheel progress;
    private static TextView lblMensagem;
    private static ProgressBar progressBar;
    private static Listener listener;

    public static void show(Context context) {
        Loading.show(context, null);
    }

    public static void show(Context context, Listener listener) {
        Loading.listener = listener;

        //Roda listener
        if (Loading.listener != null)
            Loading.listener.onStart();

        context.startActivity(new Intent(context, Loading.class));
    }

    public static void hide() {
        if (loading != null) {

            //Finaliza loading
            loading.finish();

            //Roda listener
            if (listener != null)
                listener.onFinish();

            loading = null;
            lblMensagem = null;
            progressBar = null;
            listener = null;

        }
    }

    public static void setText(String texto) {
        if (lblMensagem != null) {
            lblMensagem.setVisibility(View.VISIBLE);
            lblMensagem.setText(texto);
        }
    }

    public static void setProgressMax(int value) {
         if (progressBar != null) {
             progressBar.setVisibility(View.VISIBLE);
             progressBar.setProgress(0);
             progressBar.setMax(value);
             if (value == -1)
                 progressBar.setVisibility(View.GONE);
             else
                 progressBar.setVisibility(View.VISIBLE);
         }
    }

    public static void setProgressValue(int value) {
        if (progressBar != null) {
            if (value >= progressBar.getMax())
                progressBar.setProgress(0);
            else
                progressBar.setProgress(value);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.loading);
        progress = (ProgressWheel) this.findViewById(R.id.progress);
        lblMensagem = (TextView) this.findViewById(R.id.lblMensagem);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        lblMensagem.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        loading = this;

        //Obtem tamanho da tela e ajusta content da tela
        HashMap<String, Integer> hashMap = SupportBase.getXYActivity(this);
        this.getWindow().setLayout(hashMap.get("width"), hashMap.get("height"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listener != null)
            listener.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (listener != null)
            listener.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
