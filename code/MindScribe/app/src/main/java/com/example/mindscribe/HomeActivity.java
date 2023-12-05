package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private final String apiUrl = "http://10.0.2.2:3001";

    private TextView textView;
    private int userID;
    private int year;
    private int month;
    private int day;

    //called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.homePageEntry);
        // Fetch user ID to allow for the current entry to be shown
        userID = getCurrentUserId();
        Calendar currentDate = Calendar.getInstance();
        // Fetch the current date to pass to fetch today's journal and to navigate to today's journal
        year = currentDate.get(Calendar.YEAR);
        month = currentDate.get(Calendar.MONTH);
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        getCurrentEntry();
    }

    //Calls to fetch the current entry each time the home activity is shown
    @Override
    protected void onResume() {
        super.onResume();
        getCurrentEntry();
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
        // Package variables to send to Journal activity, and open it
        Intent intent = new Intent(HomeActivity.this, JournalActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("dayOfMonth", day);
        startActivity(intent);
    }
    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.mindscribe", MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // Default to -1 or any invalid value
    }
    private void getCurrentEntry() {
        JournalService journalService = new JournalService(this, apiUrl);
        // Attempt to set the current entry on the homepage, or set a placeholder if not
        new Thread(() -> {
            try {
                String entry = journalService.fetchJournalEntry(userID, year, month, day);
                textView.setText(entry);
            } catch (Exception E) {
                textView.setText("");
                textView.setHint("Perhaps there is no entry for today.");
            }
        }).start();
    }
}
