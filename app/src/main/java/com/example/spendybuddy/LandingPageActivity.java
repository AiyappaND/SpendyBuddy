package com.example.spendybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.spendybuddy.Transaction_be.OverviewTransactionActivity;
import com.example.spendybuddy.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPageActivity extends AppCompatActivity {

    private FloatingActionButton AddExpense;
    private FloatingActionButton AddIncome;

    private FloatingActionButton FABLogout;
    private Button LogoutButton;

    private Button AddExpenseButton;
    private Button AddIncomeButton;
    private Button TransactionList;
    private Button OverviewButton;
    String username;

    private Button UserProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        username = getIntent().getExtras().getString("username");

        AddExpense = findViewById(R.id.add_Expense);
        AddExpenseButton = findViewById(R.id.Expensebutton);
        AddIncome = findViewById(R.id.add_Income);
        AddIncomeButton =findViewById(R.id.Incomebutton);

        FABLogout = findViewById(R.id.log_out);
        LogoutButton = findViewById(R.id.log_out_warning);

        UserProfileButton = findViewById(R.id.toUserProfile);

        TransactionList = findViewById(R.id.toTransactionPage);

        OverviewButton = findViewById(R.id.overview_button);

        AddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, TransactionActivity.class);
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

        AddIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });

        AddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, TransactionActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        OverviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, GraphOverview.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        TransactionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, OverviewTransactionActivity.class);
                intent.putExtra("username", username);
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

        UserProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                navigateToUserProfile();
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

    public void navigateToUserProfile() {
        Intent profileIntent = new Intent(getApplicationContext(),
                UserProfile.class);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        profileIntent.putExtra("username", username);
        startActivity(profileIntent);
    }

}