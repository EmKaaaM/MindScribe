package com.example.mindscribe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView m_calendarView; //calendarView object
    private int m_year, m_month, m_dayOfMonth; //variables to store date

    //called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        m_calendarView = findViewById(R.id.calendarView);
        m_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Get the current date
                Calendar currentDate = Calendar.getInstance();
                int currentYear = currentDate.get(Calendar.YEAR);
                int currentMonth = currentDate.get(Calendar.MONTH);
                int currentDayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH);

                // Check if the selected date is not in the future
                if ((year < currentYear) || (year == currentYear && month < currentMonth) ||
                        (year == currentYear && month == currentMonth && dayOfMonth <= currentDayOfMonth)) {
                    // Date is not in the future, proceed to JournalActivity
                    Intent intent = new Intent(CalendarActivity.this, JournalActivity.class);
                    intent.putExtra("year", year);
                    intent.putExtra("month", month);
                    intent.putExtra("dayOfMonth", dayOfMonth);
                    startActivity(intent);
                } else {
                    // Date is in the future, notify the user or take appropriate action
                    Toast.makeText(CalendarActivity.this, "Cannot select a future date", Toast.LENGTH_SHORT).show();
                    // Optionally, you can reset the CalendarView to the current date or take other actions.
                }
            }
        });
    }

    //called when save button is clicked
    public void onCalBtnClick(View v) {
        //do nothing
    }

    //called when home button is clicked
    public void onHomeBtnClick(View v){
        finish();
    }
}