package com.example.leeyo.naresha;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class medi extends AppCompatActivity {

    List<Alram_first> alramList;

    public void editButton(View v) {
        Toast toast = Toast.makeText(this, "잠시만기다려주세요...", Toast.LENGTH_SHORT);
        toast.show();

        Alram alram = new Alram();

        TextView medi_name = findViewById(R.id.alram_isFor);
        alram.setIsFor(medi_name.getText().toString());

        TextView medi_am_pm = findViewById(R.id.alram_am_pm);
        alram.setam_pm(medi_am_pm.getText().toString());

        TextView medi_hour = findViewById(R.id.alram_hour);
        alram.setHour(medi_hour.getText().toString());

        TextView medi_minute = findViewById(R.id.alram_minute);
        alram.setMinute(medi_minute.getText().toString());

        TextView medi_count = findViewById(R.id.medi_count);
        alram.setCount(medi_count.getText().toString());

        Bundle bundle = new Bundle();

        Intent information = new Intent(this, medi_edit.class);

        bundle.putSerializable("alram_edit", alram);

        information.putExtras(bundle);

        setResult(Activity.RESULT_OK, information);

        finish();
    }

    public void plusButton(View v) {
        Toast toast = Toast.makeText(this, "잠시만기다려주세요...", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, medi_plus.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {

                Alram_first alram_first = new Alram_first();

                alram_first = (Alram_first)data.getSerializableExtra("alram");

                ListView alramListView = findViewById(R.id.alram_list_view);

                alramList.add(new Alram_first(alram_first.getam_pm(), alram_first.getHour(), alram_first.getMinute(), alram_first.getIsFor(), alram_first.getCount() + "회분", true));

                Collections.reverse(alramList);

                AlramListViewAdapter_first alramListViewAdapter_first = new AlramListViewAdapter_first(this, alramList);

                alramListView.setAdapter(alramListViewAdapter_first);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi);

        alramList = new ArrayList<>();
    }
}
