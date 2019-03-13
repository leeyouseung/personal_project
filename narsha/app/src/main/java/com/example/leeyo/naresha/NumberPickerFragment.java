package com.example.leeyo.naresha;              // medi_plus;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NumberPickerFragment extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);
        numberPicker.setWrapSelectorWheel(false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("회분");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action for 'OK' Button;
                valueChangeListener.onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());  // 코드분석;
                Toast.makeText(getContext(), numberPicker.getValue() + "회분 입니다.", Toast.LENGTH_SHORT).show();

                // Ok button clicked change count_medi text;
                medi_plus myActivity = (medi_plus) getContext();
                assert myActivity != null;

                Button mediCountButton = myActivity.findViewById(R.id.have_count_medi);

                Integer temp = numberPicker.getValue();

                mediCountButton.setText(temp.toString());

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action for 'Cancel' Button;

            }
        });

        builder.setView(numberPicker);

        return builder.create();

    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;

    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;

    }
}
