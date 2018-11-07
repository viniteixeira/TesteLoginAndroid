package support.api.utils;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;

public class SupportVoz {

    public static final int RESULT_VOZ = 11;

    public void init(Fragment fragment) throws Exception {
        this.initPadrao(fragment);
    }

    public void init(Activity activity) throws Exception {
        this.initPadrao(activity);
    }

    private void initPadrao(Object object) throws Exception {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "pt-BR");


        if (object instanceof Fragment)
            ((Fragment)object).startActivityForResult(intent, RESULT_VOZ);
        else if (object instanceof Activity)
            ((Activity)object).startActivityForResult(intent, RESULT_VOZ);
    }

    public String getResult(Intent data) {
        return data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
    }
}
