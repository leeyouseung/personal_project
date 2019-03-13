package com.example.leeyo.naresha;              // Login_height;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NumberPickerFragment_height_Login extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(120);
        numberPicker.setMaxValue(190);
        numberPicker.setWrapSelectorWheel(false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("신장 (cm)");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action for 'OK' Button;
                valueChangeListener.onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());  // 코드분석;
                Toast.makeText(getContext(), numberPicker.getValue() + "cm 입니다.", Toast.LENGTH_SHORT).show();

                // Ok button clicked change count_medi text;
                Login myActivity = (Login) getContext();
                assert myActivity != null;

                EditText heightButton = myActivity.findViewById(R.id.height_Button);

                Integer temp = numberPicker.getValue();

                heightButton.setText(temp.toString() + " cm");
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
