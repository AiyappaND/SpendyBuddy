package com.example.spendybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.spendybuddy.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPageActivity extends AppCompatActivity {

    private FloatingActionButton AddExpense;
    private FloatingActionButton AddIncome;

    private FloatingActionButton FABLogout;
    private Button LogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);


        AddExpense = findViewById(R.id.add_Expense);
        AddIncome = findViewById(R.id.add_Income);

        FABLogout = findViewById(R.id.log_out);
        LogoutButton = findViewById(R.id.log_out_warning);


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

        FABLogout.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
               logoutWithWarning();
            }
        });

        LogoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                logoutWithWarning();
            }
        });


    }

    public void logoutWithWarning() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Logout")
                .setMessage("Do you really want to logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    Toast.makeText(LandingPageActivity.this,
                            "You have successfully logged out", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onBackPressed() {
       logoutWithWarning();
    }

}