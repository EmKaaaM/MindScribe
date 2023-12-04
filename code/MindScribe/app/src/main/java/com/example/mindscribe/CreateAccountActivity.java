package com.example.mindscribe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }


    public void onCreateBtnClick(View v) throws JSONException {
        EditText usernameView = findViewById(R.id.usernameText);
        EditText passwordView = findViewById(R.id.createpasswordText);
        EditText confirmPasswordView = findViewById(R.id.confirmPasswordText);

        String password = String.valueOf(passwordView.getText());
        String confirm = String.valueOf(confirmPasswordView.getText());

        if (password.equals(confirm) && password.length() > 0 ) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://10.0.2.2:3001/createAccount";

            HashMap<String,String> params = new HashMap<>();
            params.put("username", usernameView.getText().toString());
            params.put("password", password);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                    response -> {
                        // Show a success message
                        Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_LONG).show();
                        finish();
                    },
                    error -> {
                        try {
                            // parse error message
                            String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            Gson g = new Gson();
                            APIResponse response = g.fromJson(responseBody, APIResponse.class);

                            usernameView.setText("");
                            passwordView.setText("");
                            confirmPasswordView.setText("");

                            // determine what caused the error and display to user
                            if (response.getBody().equals("Username already exists")) {
                                usernameView.setError("Username Taken");
                            }
                            else {
                                usernameView.setError("Something went wrong! Try again.");
                            }
                        }
                        catch (Exception e) {
                            // if parsing goes wrong, just clear all fields
                            usernameView.setText("");
                            passwordView.setText("");
                            confirmPasswordView.setText("");
                            usernameView.setError("Something went wrong! Try again.");
                        }
                    }
            );

            requestQueue.add(request);
            finish();
        }
        else {
            //Error, the password confirmation isn't correct
            confirmPasswordView.setText("");
            confirmPasswordView.setError("Passwords don't match");
        }
    }

}