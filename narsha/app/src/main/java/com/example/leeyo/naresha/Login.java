package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;

public class Login extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private int mYear, mMonth, mDate;
    static final int DATE_SET_BIRTH = 998;

    public void NextButton(View v) {
        Toast toast = Toast.makeText(this, "잠시만 기다려주세요...", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setCurrentDateOnView();
        addListenerOnButton();
    }

    public void settingSex(View v) {

        final CharSequence[] SexKind= {"남성", "여성"};

        AlertDialog.Builder set_sex = new AlertDialog.Builder(this);
        set_sex.setTitle("성별을 선택해주세요");
        set_sex.setSingleChoiceItems(SexKind, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), SexKind[item] + "을 선택하셨습니다", Toast.LENGTH_SHORT).show();
                EditText sexButton = findViewById(R.id.sex_Button);
                sexButton.setText(SexKind[item]);
                dialog.cancel();
            }
        });

        AlertDialog sex_set = set_sex.create();
        sex_set.show();
    }

    public void settingHeight(View v) {
        NumberPickerFragment_height_Login numberPickerFragment_height_login = new NumberPickerFragment_height_Login();
        numberPickerFragment_height_login.setValueChangeListener(this);
        numberPickerFragment_height_login.show(getSupportFragmentManager(), "number picker");
    }

    public void settingWeight(View v) {
        NumberPickerFragment_weight_Login numberPickerFragment_weight_login = new NumberPickerFragment_weight_Login();
        numberPickerFragment_weight_login.setValueChangeListener(this);
        numberPickerFragment_weight_login.show(getSupportFragmentManager(), "number picker");
    }

    private void setCurrentDateOnView() {

        final Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDate = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void addListenerOnButton() {
        EditText BirthInput = findViewById(R.id.Birth_Button);
        BirthInput.setOnClickListener(new View.OnClickListener() {
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

            EditText BirthInput = findViewById(R.id.Birth_Button);
            BirthInput.setText(new StringBuilder().append("  ").append(mYear).append(" 년 ").append(mMonth + 1).append(" 월 ").append(mDate).append(" 일"));

        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}