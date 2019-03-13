package com.example.leeyo.naresha;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;
import java.util.Calendar;

public class Setting extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private int mYear, mMonth, mDate;
    static final int DATE_SET_BIRTH = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setCurrentDateOnView();
        addListenerOnButton();
    }

    public void CancelButton(View v) {
        finish();
    }

    public void SaveButton(View v) {
        Toast toast = Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT);
        toast.show();

        finish();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    public void settingSex(View v) {

        final CharSequence[] SexKind = {"남성", "여성"};

        AlertDialog.Builder set_sex = new AlertDialog.Builder(this);
        set_sex.setTitle("성별을 선택해주세요");
        set_sex.setSingleChoiceItems(SexKind, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), SexKind[item] + "을 선택하셨습니다", Toast.LENGTH_SHORT).show();
                Button sexButton = findViewById(R.id.sex_Button);
                sexButton.setText(SexKind[item]);
                dialog.cancel();
            }
        });

        AlertDialog sex_set = set_sex.create();
        sex_set.show();
    }

    public void settingHeight(View v) {
        NumberPickerFragment_SetHeight_Setting numberPickerFragment_setHeight_setting = new NumberPickerFragment_SetHeight_Setting();
        numberPickerFragment_setHeight_setting.setValueChangeListener(this);
        numberPickerFragment_setHeight_setting.show(getSupportFragmentManager(), "number picker");
    }

    public void settingWeight(View v) {
        NumberPickerFragment_SetWeight_Setting numberPickerFragment_setWeight_setting = new NumberPickerFragment_SetWeight_Setting();
        numberPickerFragment_setWeight_setting.setValueChangeListener(this);
        numberPickerFragment_setWeight_setting.show(getSupportFragmentManager(), "number picker");
    }

    public void settingBirth(View v) {
        // setCurrentDateOnView();
        // addListenerOnButton();
    }

    private void setCurrentDateOnView() {

        final Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDate = calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void addListenerOnButton() {
        Button setBirth = findViewById(R.id.Birth_Button);
        setBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_SET_BIRTH);

            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_SET_BIRTH:
                return new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDate);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // BirthInput DatePickerDialog 로 날짜 변경;
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDate = selectedDay;

            Button setBirth = findViewById(R.id.Birth_Button);
            setBirth.setText(new StringBuilder().append("  ").append(mYear).append(" / ").append(mMonth + 1).append(" / ").append(mDate));

        }
    };
}