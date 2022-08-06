package com.example.spendybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IncomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);


        FloatingActionButton home = findViewById(R.id.home2_button);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(IncomeActivity.this, LandingPageActivity.class);
            startActivity(intent);

        });
    }
}