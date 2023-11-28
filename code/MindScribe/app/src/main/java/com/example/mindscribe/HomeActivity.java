package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    //called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //called when calendar button is clicked
    public void onCalBtnClick(View v){
        Intent intent = new Intent (HomeActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void onHomeBtnClick(View v){
        //do nothing
    }
}