package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    String url = "http://10.0.2.2:3001/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginBtnClick(View v) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        EditText usernameTextView = findViewById(R.id.usernameText);
        EditText passwordView = findViewById(R.id.passwordText);

        HashMap<String, String> params = new HashMap<>();
        params.put("username", usernameTextView.getText().toString());
        params.put("password", passwordView.getText().toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    try {
                        String token = response.getString("token");
                        int userId = response.getInt("user_id"); // Retrieve the user ID from the response

                        storeTokenAndUserId(token, userId); // Store the token and user ID

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    passwordView.setError("Password or Username are incorrect");
                }
        );

        requestQueue.add(request);
    }

    private void storeTokenAndUserId(String token, int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.mindscribe", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt_token", token);
        editor.putInt("user_id", userId);
        editor.apply();
    }

    public void onCreateAccCLick (View v) {
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }
}
