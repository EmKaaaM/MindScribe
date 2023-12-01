package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    //called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //called when calendar button is clicked
    public void onCalBtnClick(View v) {
        Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void onHomeBtnClick(View v) {
        //do nothing
    }

    public void onTodayBtnClick(View v) {
        Calendar currentDate = Calendar.getInstance();
        // Fetch the current date to pass to the Journal activity
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        // Package variables to send to Journal activity, and open it
        Intent intent = new Intent(HomeActivity.this, JournalActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("dayOfMonth", day);
        startActivity(intent);
    }
}