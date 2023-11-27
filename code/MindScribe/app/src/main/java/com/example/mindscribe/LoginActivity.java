package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginBtnClick(View v) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:3001/login";

        EditText usernameTextView = findViewById(R.id.usernameText);
        EditText passwordView = findViewById(R.id.passwordText);

        HashMap<String,String> params = new HashMap<>();
        params.put("username", usernameTextView.getText().toString());
        params.put("password", passwordView.getText().toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }, error -> {
                    passwordView.setText("");
                    usernameTextView.setText("");
                    usernameTextView.setHint("Incorrect");
                }
        );

        requestQueue.add(request);
    }

    public void onCreateAccCLick (View v) {

        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

}