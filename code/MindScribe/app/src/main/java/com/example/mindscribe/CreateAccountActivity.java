package com.example.mindscribe;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mindscribe.databinding.ActivityCreateAccountBinding;

import org.json.JSONException;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }


    public void onCreateBtnClick(View v) throws JSONException {


        EditText usernameView = findViewById(R.id.usernameText);
        EditText passwordView = findViewById(R.id.passwordText);
        EditText ConfirmPasswordView = findViewById(R.id.confirmPassText);

        if (passwordView.getText()==ConfirmPasswordView.getText()){
            //Create the new account
        }
        else {
            //Error, the password confirmation isn't correct
            ConfirmPasswordView.setText("");
            ConfirmPasswordView.setHint("Does not match");
        }


    }

}