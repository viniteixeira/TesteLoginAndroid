package support.api.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by MarioGuerra on 16/01/17.
 */

public class RgMask {


    private static final String maskRG = "##.###.###";


    public static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }


    public static TextWatcher insert(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String str = RgMask.unmask(s.toString());
                String mask;
                mask = maskRG;






                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                        mascara += m;
                        continue;
                    }

                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(mascara);
                editText.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {


            }
        };
    }

    private static String getDefaultMask(String str) {
        String defaultMask = maskRG;
        return defaultMask;
    }
}