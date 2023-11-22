package com.example.mindscribe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView m_calendarView; //calendarView object
    private int m_year, m_month, m_dayOfMonth; //variables to store date

    //called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        m_calendarView = (CalendarView) findViewById(R.id.calendarView); //initialize calendarView object
        m_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) { //when date is selected
                if (m_year == year && m_month == month && m_dayOfMonth == dayOfMonth) { //if same date is selected go to journal activity
                    Intent intent = new Intent(CalendarActivity.this, JournalActivity.class);
                    intent.putExtra("year", m_year);
                    intent.putExtra("month", m_month);
                    intent.putExtra("dayOfMonth", m_dayOfMonth);
                    startActivity(intent);
                } else { //if different date is selected
                    m_year = year;
                    m_month = month;
                    m_dayOfMonth = dayOfMonth;
                }
            }
        });
    }
}