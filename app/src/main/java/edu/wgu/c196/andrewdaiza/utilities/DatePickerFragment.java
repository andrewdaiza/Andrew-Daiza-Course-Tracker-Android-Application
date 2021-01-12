package edu.wgu.c196.andrewdaiza.utilities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Date dateValue;
    private TextView outputText;
    private CheckDate outputDate;

    public DatePickerFragment() {
    }

    public DatePickerFragment(TextView outputText, Date dateValue) {
        this.dateValue = dateValue;
        this.outputText = outputText;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        if (dateValue != null) {
            cal.setTime(dateValue);
        }
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if( outputText != null) {
            String strDate = Converters.getDateFormatting(month, dayOfMonth, year);
            outputText.setText(strDate);
        } else if (outputDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            outputDate.setFormatDate(cal.getTime());
        }
    }

}
