package com.example.leeyo.naresha;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class medi_edit extends AppCompatActivity {

    List<Alram> alramList;

    public void saveButton(View v) {
        Toast toast = Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT);
        toast.show();

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent intent = new Intent(this, medi.class);
        startActivityForResult(intent, 2);

        if(requestCode == 2) {
            if(resultCode == Activity.RESULT_OK) {

                Alram alram = new Alram();

                alram = (Alram)data.getSerializableExtra("alram_edit");

                ListView alramListView = findViewById(R.id.alram_list_view);

                alramList.add(new Alram(alram.getam_pm(), alram.getHour(), alram.getMinute(), alram.getIsFor(), alram.getCount() + "회분"));

                Collections.reverse(alramList);

                AlramListViewAdapter alramListViewAdapter = new AlramListViewAdapter(this, alramList);

                alramListView.setAdapter(alramListViewAdapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_edit);

        alramList = new ArrayList<>();
    }

    public void editButton(View v) {
        Toast toast = Toast.makeText(this, "edit Loding...", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, medi_plus.class);
        startActivity(intent);
    }
}