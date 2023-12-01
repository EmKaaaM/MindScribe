package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JournalActivity extends AppCompatActivity {
    private int m_year, m_month, m_dayOfMonth; //variables to store date
    private JournalEntry m_journalEntry; //variable to store journal entry
    private final String apiUrl = "http://10.0.2.2:3001";
    private JournalEntry cachedJournalEntry = null;
    private int cachedYear = -1, cachedMonth = -1, cachedDay = -1;
    public static int[] MOOD_IDS = { R.string.moodHappy, R.string.moodAngry, R.string.moodFedUp, R.string.moodLove, R.string.moodTired, R.string.moodNervous,
                                    R.string.moodSad, R.string.moodNeutral};
    public ArrayList<String> moods = new ArrayList<>();
    public static final String[] KEYWORDS = { "Work", "School", "Home" };


    //called when activity is created
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
            String dateString = String.format("%02d/%02d/%d", m_dayOfMonth, m_month + 1, m_year);
            textView.setText(dateString);
        } else {
            // Handle the case where the Intent is null
        }

        Spinner moodDropdown = findViewById(R.id.moodSpinner);

        for (int moodId : MOOD_IDS) {
            moods.add(getString(moodId));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moods);
        moodDropdown.setAdapter(adapter);

        ArrayAdapter<String> keywordAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, KEYWORDS);

        MultiAutoCompleteTextView keywordsView = findViewById(R.id.KeywordsView);
        keywordsView.setAdapter(keywordAdapter);
        keywordsView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        displayJournalEntry(m_year, m_month, m_dayOfMonth);
    }

    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.mindscribe", MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // Default to -1 or any invalid value
    }
    //called when home button is clicked
    public void onBackBtnClick(View v) {
        finish();
    }

    //called when save button is clicked
    public void onSaveBtnClick(View v) {
        EditText editText = findViewById(R.id.EntryEditText);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.mindscribe", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);  // '-1' as a default value indicating 'not found'
        if (userId == -1) {
            // Handle error: User ID is not set or invalid
            return;
        }

        Spinner moodSpinner = findViewById(R.id.moodSpinner);
        String mood = moodSpinner.getSelectedItem().toString();

        MultiAutoCompleteTextView keywordsView = findViewById(R.id.KeywordsView);
        String keywords = keywordsView.getText().toString();

        m_journalEntry = new JournalEntry(m_year, m_month, m_dayOfMonth, editText.getText().toString(), userId, mood, keywords);

        // Rest of your code to send the entry
        JournalService journalService = new JournalService(this, apiUrl);
        new Thread(() -> {
            try {
                String response = journalService.createJournalEntry(m_journalEntry);
                // Handle the response or update UI accordingly
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the error
            }
        }).start();
        finish();
    }
    private void displayJournalEntry(int year, int month, int day) {
        // Check if the requested entry is already cached
        if (year == cachedYear && month == cachedMonth && day == cachedDay && cachedJournalEntry != null) {
            updateUIWithJournalEntry(cachedJournalEntry);
            return;
        }

        JournalService journalService = new JournalService(this, apiUrl);
        new Thread(() -> {
            try {
                int userId = getCurrentUserId();
                String response = journalService.fetchJournalEntry(userId, year, month, day);

                runOnUiThread(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String entryText = jsonResponse.getString("entry_text");
                        String mood = jsonResponse.getString("mood");
                        String keywords = jsonResponse.getString("keywords");
                        if (keywords.equals("null")) {
                            keywords = "";
                        }
                        // Cache the fetched entry
                        cachedJournalEntry = new JournalEntry(year, month, day, entryText, userId, mood, keywords);
                        cachedYear = year;
                        cachedMonth = month;
                        cachedDay = day;
                        updateUIWithJournalEntry(cachedJournalEntry);
                    } catch (JSONException e) {
                        // Handle JSON parsing error
                        showNoEntryMessage();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                // Handle network error
                runOnUiThread(this::showErrorMessage);
            }
        }).start();
    }
    private void updateUIWithJournalEntry(JournalEntry journalEntry) {
        EditText editText = findViewById(R.id.EntryEditText);
        editText.setText(journalEntry.getEntry());

        Spinner moodSpinner = findViewById(R.id.moodSpinner);
        moodSpinner.setSelection(moods.indexOf(journalEntry.getMood()));

        MultiAutoCompleteTextView keywordsView = findViewById(R.id.KeywordsView);
        keywordsView.setText(journalEntry.getKeywords());
    }
    private void showNoEntryMessage() {
        EditText editText = findViewById(R.id.EntryEditText);
        editText.setText(""); // Clear any existing text
        editText.setHint("Perhaps there is no entry yet for this date.");
    }

    private void showErrorMessage() {
        EditText editText = findViewById(R.id.EntryEditText);
        editText.setText(""); // Clear any existing text
        editText.setHint("Error occurred while fetching the entry.");
    }


}