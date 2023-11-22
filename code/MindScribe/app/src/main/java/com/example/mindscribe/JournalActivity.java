package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JournalActivity extends AppCompatActivity {
    private int m_year, m_month, m_dayOfMonth; //variables to store date

    //called when activity is created
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        // Retrieve data from the Intent's extras
        Intent intent = getIntent();
        if (intent != null) {
            m_year = intent.getIntExtra("year", -1); // -1 is the default value if the key is not found
            m_month = intent.getIntExtra("month", -1);
            m_dayOfMonth = intent.getIntExtra("dayOfMonth", -1);
            TextView textView = findViewById(R.id.dateTextView);
            textView.setText(m_month + "/" + m_dayOfMonth + "/" + m_year);
        } else {
            // Handle the case where the Intent is null
        }
    }

    //called when home button is clicked
    public void onBackBtnClick(View v){
        finish();
    }
}