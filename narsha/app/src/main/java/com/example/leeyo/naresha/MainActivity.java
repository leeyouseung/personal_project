package com.example.leeyo.naresha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void chonButton(View v) {
        Toast toast = Toast.makeText(this, "잠시만 기다려주세요...", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, chon.class);
        startActivity(intent);

    }

    public void hulButton(View v) {
        Toast toast = Toast.makeText(this, "잠시만 기다려주세요...", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, hul.class);
        startActivity(intent);

    }

    public void mediButton(View v) {
        Toast toast = Toast.makeText(this, "잠시만 기다려주세요...", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, medi.class);
        startActivity(intent);

    }

    public void settingButton(View v) {
        Toast toast = Toast.makeText(this, "잠시만 기다려주세요...", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}