package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void calBtnOnClick(View v){
        Intent intent = new Intent (LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}