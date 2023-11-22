package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JournalActivity extends AppCompatActivity {

    //called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
    }

    //called when calendar button is clicked
    public void calBtnOnClick(View v){
        Intent intent = new Intent (JournalActivity.this, CalendarActivity.class);
        startActivity(intent);
    }
}