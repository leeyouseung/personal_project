package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class medi_plus extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

//    private int mYear, mMonth, mDate;
//    private int mYear1, mMonth1, mDate1;
//    static final int DATE_START_DIALOG_ID = 998;
//    static final int DATE_END_DIALOG_ID = 999;

    List<medi_plus_Alram> plusList;
    private AlramListViewAdapter_plus alramListViewAdapter_plus;

    public void backButton(View v) {
        Toast toast = Toast.makeText(this, "잠시만 기다려주세요...", Toast.LENGTH_SHORT);
        toast.show();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_plus);

//        EditText mediStart = findViewById(R.id.medi_start);
//        //mediStart.setEnabled(false);          // 글자색 까지 흐리게 만듬;
//        mediStart.setFocusable(false);
//
//        EditText mediEnd = findViewById(R.id.medi_end);
//        //mediEnd.setEnabled(false);          // 글자색 까지 흐리게 만듬;
//        mediEnd.setFocusable(false);

        plusList = new ArrayList<>();

        ListView plusListView = findViewById(R.id.plus_list_view);

        alramListViewAdapter_plus = new AlramListViewAdapter_plus(this, plusList);

        plusListView.setEmptyView(findViewById(R.id.plus_list_view));
        plusListView.setAdapter(alramListViewAdapter_plus);

//        plusListView.getAdapter().getItem(0);

//        ImageButton medi_cal_start = findViewById(R.id.medi_cal_start);
//        medi_cal_start.setOnClickListener(this);
//
//        ImageButton medi_cal_end = findViewById(R.id.medi_cal_end);
//        medi_cal_end.setOnClickListener(this);

//        setCurrentDateOnView();
//        addListenerOnButton();
//
//        setCurrentDateOnView1();
//        addListenerOnButton1();

    }

    /*private void setCurrentDateOnView() {       // medi_start 첫번째 셋팅 현재 날짜;
        EditText start_medi = findViewById(R.id.medi_start);

        final Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDate = calendar.get(Calendar.DAY_OF_MONTH);

        start_medi.setText(new StringBuilder().append("  ").append(mYear).append("-").append(mMonth + 1).append("-").append(mDate).append(" "));

    }

    private void setCurrentDateOnView1() {      // medi_end 첫번째 셋팅 현재 날짜;
        EditText end_medi = findViewById(R.id.medi_end);

        final Calendar calendar1 = Calendar.getInstance();

        mYear1 = calendar1.get(Calendar.YEAR);
        mMonth1 = calendar1.get(Calendar.MONTH);
        mDate1 = calendar1.get(Calendar.DAY_OF_MONTH);

        end_medi.setText(new StringBuilder().append("  ").append(mYear1).append("-").append(mMonth1 + 1).append("-").append(mDate1).append(" "));

    }

    private void addListenerOnButton() {
        ImageButton cal_medi_start = findViewById(R.id.medi_cal_start);
        cal_medi_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_START_DIALOG_ID);

            }
        });

    }

    private void addListenerOnButton1() {
        ImageButton cal_medi_end = findViewById(R.id.medi_cal_end);
        cal_medi_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_END_DIALOG_ID);

            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_START_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDate);

            case DATE_END_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener1, mYear1, mMonth1, mDate1);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // medi_start DatePickerDialog 로 날짜 변경;
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDate = selectedDay;

            EditText start_medi = findViewById(R.id.medi_start);
            start_medi.setText(new StringBuilder().append("  ").append(mYear).append("-").append(mMonth + 1).append("-").append(mDate).append(" "));

        }

    };

    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        // medi_end DatePickerDialog 로 날짜 변경;
        public void onDateSet(DatePicker view1, int selectedYear1, int selectedMonth1, int selectedDay1) {
            mYear1 = selectedYear1;
            mMonth1 = selectedMonth1;
            mDate1 = selectedDay1;

            EditText end_medi = findViewById(R.id.medi_end);
            end_medi.setText(new StringBuilder().append("  ").append(mYear1).append("-").append(mMonth1 + 1).append("-").append(mDate1).append(" "));

        }
    };*/

    public void mediTimePlus(View v) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setmHandle(mHandle);
        timePickerFragment.show(getSupportFragmentManager(), String.valueOf(TimePickerFragment.STYLE_NO_FRAME));
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

//    public void countButton(View v) {
//        NumberPickerFragment numberPickerFragment = new NumberPickerFragment();
//        numberPickerFragment.setValueChangeListener(this);
//        numberPickerFragment.show(getSupportFragmentManager(), "time picker");
//
//    }

    public void cancelButton(View v) {
        finish();

    }

    public void saveButton(View v) {
        Toast toast = Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT);
        toast.show();

        Alram_first alram_first = new Alram_first();

        EditText medi_name = findViewById(R.id.medi_name_input);
        alram_first.setIsFor(medi_name.getText().toString());

        TextView medi_am_pm = findViewById(R.id.plus_am_pm);
        alram_first.setAm_pm(medi_am_pm.getText().toString());

        TextView medi_hour = findViewById(R.id.plus_hour);
        alram_first.setHour(medi_hour.getText().toString());

        TextView medi_minute = findViewById(R.id.plus_minute);
        alram_first.setMinute(medi_minute.getText().toString());

        Button medi_count = findViewById(R.id.have_count_medi);
        alram_first.setCount(medi_count.getText().toString());

        Bundle bundle = new Bundle();

        Intent information = new Intent(this, medi.class);

        bundle.putSerializable("alram", alram_first);

        information.putExtras(bundle);

        setResult(Activity.RESULT_OK, information);

        finish();
    }

    @Override
    public void onClick(View v) {

    }

    /*public DatePickerDialog.OnDateSetListener getDatePickerListener1() {
        return datePickerListener1;

    }

    public void setDatePickerListener1(DatePickerDialog.OnDateSetListener datePickerListener1) {
        this.datePickerListener1 = datePickerListener1;

    }*/

    @SuppressLint("HandlerLeak")
    Handler mHandle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2000:
                    if(msg.obj instanceof medi_plus_Alram) {
                        plusList.add((medi_plus_Alram)msg.obj);
                        alramListViewAdapter_plus.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
}