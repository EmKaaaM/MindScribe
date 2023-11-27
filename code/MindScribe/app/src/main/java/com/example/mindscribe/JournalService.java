package com.example.mindscribe;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class JournalService {

    private OkHttpClient client = new OkHttpClient();
    private final Context context;
    private String apiUrl = "http://10.0.2.2:3001";


    public JournalService(Context context, String apiUrl) {
        this.context = context;
        this.apiUrl = apiUrl;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS); // Increase connect timeout
        builder.readTimeout(30, TimeUnit.SECONDS);    // Increase read timeout
        this.client = builder.build();
    }


    private String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.mindscribe", Context.MODE_PRIVATE);
        return sharedPreferences.getString("jwt_token", null);
    }

    public String createJournalEntry(JournalEntry entry) throws Exception {
        String token = getToken();
        if (token == null) {
            throw new IllegalStateException("Token not found. User might not be logged in.");
        }

        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        JSONObject entryJson = new JSONObject();
        entryJson.put("userId", entry.getUserID());
        entryJson.put("entryText", entry.getEntry());
        // Add year, month, and day to the JSON object
        entryJson.put("year", entry.getYear());
        entryJson.put("month", entry.getMonth());
        entryJson.put("day", entry.getDayOfMonth());
        // implement keywords and mood later on.
        // entryJson.put("keywords", entry.getKeywords());
        // entryJson.put("mood", entry.getMood());

        RequestBody body = RequestBody.create(entryJson.toString(), JSON);
        Request request = new Request.Builder()
                .url(apiUrl + "/createJournal")
                .addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    // Example method to get journal entries for a user
    public String fetchJournalEntry(int userId, int year, int month, int day) throws Exception {
        String token = getToken();
        if (token == null) {
            throw new IllegalStateException("Token not found. User might not be logged in.");
        }

        String fetchUrl = apiUrl + "/getJournalEntry?userId=" + userId + "&year=" + year + "&month=" + month + "&day=" + day;

        Request request = new Request.Builder()
                .url(fetchUrl)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
