package com.example.spendybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPageActivity extends AppCompatActivity {

    private FloatingActionButton AddExpense;
    private FloatingActionButton AddIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);


        AddExpense = findViewById(R.id.add_Expense);
        AddIncome = findViewById(R.id.add_Income);


        AddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, Transaction.class);
                startActivity(intent);
            }
        });

        AddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });


    }
}