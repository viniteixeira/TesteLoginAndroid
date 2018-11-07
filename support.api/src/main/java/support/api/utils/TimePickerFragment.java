package support.api.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
                            implements TimePickerDialog.OnTimeSetListener {

    private Listener mListener;

    public interface Listener {
        void onTimeSet(TimePicker view, int hourOfDay, int minute);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (mListener != null)
            mListener.onTimeSet(view, hourOfDay, minute);
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public static TimePickerFragment start(FragmentManager fragmentManager, Listener listener) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setListener(listener);
        timePickerFragment.show(fragmentManager, TimePickerFragment.class.getSimpleName());
        return timePickerFragment;
    }
}