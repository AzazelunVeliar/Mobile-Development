package ru.mirea.khudyakovma.dialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;
import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyDateDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (DatePicker view, int year1, int month1, int dayOfMonth) -> {
            Toast.makeText(getActivity(), "Дата: " + dayOfMonth + "." + (month1 + 1) + "." + year1, Toast.LENGTH_LONG).show();
        }, year, month, day);
    }
}
