package support.api.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by user on 29/03/17.
 */

public class MonetaryMask implements TextWatcher {

        private boolean mUpdating;
        private EditText mEditText;
        private NumberFormat mNF = NumberFormat.getCurrencyInstance();

        public MonetaryMask(EditText mEditText) {
            super();
            this.mEditText = mEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        public static Double unmask(String str){
            String unmasked =  str.replaceAll("[R$]", "").replaceAll("[.]", "").replaceAll("[,]", ".");
            Double resultado = Double.parseDouble(unmasked);

            return resultado;


        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            if (mUpdating) {
                mUpdating = false;
                return;
            }
            mUpdating = true;
            String str = s.toString();
            boolean hasMask = (str.indexOf("R$") >= 0 && str.indexOf(".") >= 0 && str.indexOf(",") >= 0) ||
                    (str.indexOf("R$") >= 0 && str.indexOf(",") >= 0);

            if (hasMask) {
                str = str.replaceAll("[R$]", "").replaceAll("[.]", "").replaceAll("[,]", "");
            }

            try {
                double value = (Double.parseDouble(str) / 100);
                str = mNF.format(value);
                mEditText.setText(str);
                mEditText.setSelection(str.length());
            } catch (Exception e) {
                e.printStackTrace();
                s = "";
            }


        }


        @Override
        public void afterTextChanged(Editable s) {

        }
}