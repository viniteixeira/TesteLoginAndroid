package support.api.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by MarioGuerra on 03/05/16.
 */
public class KeyBoardEditText extends android.support.v7.widget.AppCompatEditText {

    private KeyImeChange keyImeChangeListener;

    public KeyBoardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setKeyImeChangeListener(KeyImeChange listener){
        keyImeChangeListener = listener;
    }

    public interface KeyImeChange {
        void onKeyIme(int keyCode, KeyEvent event);
    }

    public boolean onKeyPreIme (int keyCode, KeyEvent event){
        if(keyImeChangeListener != null){
            keyImeChangeListener.onKeyIme( keyCode, event);
        }
        return false;
    }
}