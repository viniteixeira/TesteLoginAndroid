package support.api.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
                            implements DatePickerDialog.OnDateSetListener {

    private Listener mListener;
    private Calendar mCalendar;

    public interface Listener {
        void onDateSet(DatePicker view, Calendar calendar);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (mListener != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            mListener.onDateSet(view, calendar);
        }
    }

    public void setCalendar(Calendar calendar) {
        this.mCalendar = calendar == null ? Calendar.getInstance() : calendar;
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public static DatePickerFragment start(FragmentManager fragmentManager,  Listener listener) {

        return DatePickerFragment.start(fragmentManager, null, listener);
    }

    public static DatePickerFragment start(FragmentManager fragmentManager, Calendar calendar, Listener listener) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(listener);
        datePickerFragment.setCalendar(calendar);
        datePickerFragment.show(fragmentManager, DatePickerFragment.class.getSimpleName());
        return datePickerFragment;
    }
}