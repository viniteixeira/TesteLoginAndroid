package support.api.utils;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by MarioGuerra on 02/05/16.
 */
public class EditTextEditorActionListener  implements EditText.OnEditorActionListener{

    private ScrollView scrollView;
    private Activity mActivity;
    public EditTextEditorActionListener(ScrollView scrollView , Activity mActivity) {
        this.scrollView = scrollView;
        this.mActivity = mActivity;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int result = actionId & EditorInfo.IME_MASK_ACTION;
        switch(result) {
            // user taped on keyboard DONE button
            case EditorInfo.IME_ACTION_DONE:
                // put the scroll view back to its original position
                scrollView.setPadding(0, 0, 0, 0);
                // hide keyboard
                ((InputMethodManager) mActivity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);

                // remove focus from any edit text
                LinearLayout scrollViewLL = (LinearLayout) scrollView.getChildAt(0);
                scrollViewLL.requestFocus();
                break;
        }
        return false;
    }
}
