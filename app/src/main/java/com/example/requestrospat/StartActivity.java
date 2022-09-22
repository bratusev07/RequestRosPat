package com.example.requestrospat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EditText input = (EditText) findViewById(R.id.search_input);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (input.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Введите запрос", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.putExtra("input", input.getText().toString());
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_input = input.getText().toString();
                if (search_input.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Введите запрос", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("input", input.getText().toString());
                    startActivity(intent);
                }
            }
        });


    }
}