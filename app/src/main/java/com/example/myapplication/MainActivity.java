package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                signIn(username, password);
            }
        });

        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                signUp(username, password);
            }
        });


    }

    private void signUp(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Bitte geben Sie Username und Passwort ein.", Toast.LENGTH_SHORT).show();
        } else {
            if (databaseHelper.checkUsernameExists(username)) {
                Toast.makeText(MainActivity.this, "Der Username ist bereits vorhanden. Bitte wählen Sie einen anderen aus.", Toast.LENGTH_SHORT).show();
            } else {
                long userId = databaseHelper.addUser(username, password);
                if (userId != -1) {
                    Toast.makeText(MainActivity.this, "Registrierung erfolgreich!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Fehlermeldung. Bitte versuchen Sie es erneut", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void signIn(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Bitte geben Sie Username und Passwort ein.", Toast.LENGTH_SHORT).show();
        } else {
            if (databaseHelper.checkUser(username, password)) {
                SharedPreferences preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("CURRENT_USER", username);
                editor.apply();

                Toast.makeText(MainActivity.this, "Login erfolgreich", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Ungüldige Eingabe. Versuchen Sie es erneut.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
