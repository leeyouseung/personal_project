package com.example.leeyo.naresha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText idText = findViewById(R.id.Input_Id);
        EditText passwordText = findViewById(R.id.PasswordText);

        Button button = findViewById(R.id.create_button);



    }

}
