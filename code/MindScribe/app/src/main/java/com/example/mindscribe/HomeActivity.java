package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private final String apiUrl = "http://10.0.2.2:3001";
    private JournalEntry cachedEntry = null;
    private int cachedYear = -1, cachedMonth = -1, cachedDay = -1;

    private TextView entryText, moodText, keywordText;
    private int userID;
    private int year;
    private int month;
    private int day;

    //called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entryText = findViewById(R.id.homePageEntry);
        moodText = findViewById(R.id.homePageMood);
        keywordText = findViewById(R.id.homePageKeyword);
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
                runOnUiThread(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(entry);
                        String entryText = jsonResponse.getString("entry_text");
                        String mood = jsonResponse.getString("mood");
                        String keywords = jsonResponse.getString("keywords");
                        // Cache and update the entry
                        cachedEntry = new JournalEntry(year, month, day, entryText, userID, mood, keywords);
                        cachedYear = year;
                        cachedMonth = month;
                        cachedDay = day;
                        updateCurrentEntry(cachedEntry);
                    } catch (JSONException e) {
                        // Handle JSON parsing error
                        entryText.setText("");
                        entryText.setHint("Perhaps there is no entry for today.");
                        moodText.setText("");
                        moodText.setHint("There is no mood set for today.");
                        keywordText.setText("");
                        keywordText.setHint("There is no keywords set for today.");
                    }
                });
            } catch (Exception E) {
                // Handle network error
                entryText.setText("");
                entryText.setHint("Perhaps there is no entry for today.");
                moodText.setText("");
                moodText.setHint("There is no mood set for today.");
                keywordText.setText("");
                keywordText.setHint("There is no keywords set for today.");
            }
        }).start();
    }
    private void updateCurrentEntry(JournalEntry entry) {
        moodText.setText(entry.getMood());
        keywordText.setText(entry.getKeywords());
        entryText.setText(entry.getEntry());
    }
}
