package com.example.mindscribe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

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

        if (password == confirm){
            //Check if the username is already taken
            Boolean nameTaken = false;




            if (!nameTaken){
                //Create the new account


            }
            else {
                //Reset values
                usernameView.setText("");
                usernameView.setHint("Username Taken");
                passwordView.setText("");
                confirmPasswordView.setText("");
            }

        }
        else {
            //Error, the password confirmation isn't correct
            confirmPasswordView.setText("");
            confirmPasswordView.setHint("Does not match");
        }


    }

}