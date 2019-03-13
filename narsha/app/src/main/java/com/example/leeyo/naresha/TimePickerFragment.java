package com.example.leeyo.naresha;              // medi_plus;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    final List<medi_plus_Alram> plusList = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();
    int mHour = calendar.get(Calendar.HOUR_OF_DAY);
    int mMinute = calendar.get(Calendar.MINUTE);

    private Handler mHandle;

    public void setmHandle(Handler mHandle) {
        this.mHandle = mHandle;

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Integer iHour = view.getCurrentHour();
        Integer iMinute = view.getCurrentMinute();
        String viewAmPm = "AM";

        if(iHour > 12) {
            iHour -= 12;
            viewAmPm = "PM";
        } else if(iHour == 12) {
            iHour = 12;
            viewAmPm = "AM";
        }

        Toast toast = Toast.makeText(getContext(),  viewAmPm + " " + iHour + "시" + iMinute + "분 을 선택하셨습니다", Toast.LENGTH_SHORT);
        toast.show();

        plusList.add(new medi_plus_Alram(viewAmPm, iHour.toString() + "시", iMinute.toString() + "분"));
        mHandle.obtainMessage(2000,1,2,plusList.get(0)).sendToTarget();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), this, mHour, mMinute, DateFormat.is24HourFormat(getContext()));

        return timePickerDialog;
    }
}
