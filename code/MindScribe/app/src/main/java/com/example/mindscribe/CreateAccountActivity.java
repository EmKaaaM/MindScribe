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
        EditText confirmPasswordView = findViewById(R.id.confirmPassText);


        if (passwordView.getText()==confirmPasswordView.getText()){
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