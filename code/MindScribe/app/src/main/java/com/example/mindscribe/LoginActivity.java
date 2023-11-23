package com.example.mindscribe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(params),
                response -> {
                    Log.d("COMP32", response.toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }, error -> {
                    Log.d("COMP32", error.toString());
                    passwordView.setText("");
                    usernameTextView.setText("incorrect!");
                }){
        };

        requestQueue.add(request);
    }
}