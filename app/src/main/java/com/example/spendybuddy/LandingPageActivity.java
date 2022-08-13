package com.example.spendybuddy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spendybuddy.Transaction_be.OverviewTransactionActivity;
import com.example.spendybuddy.data.model.Transaction;
import com.example.spendybuddy.ui.login.LoginActivity;
import com.example.spendybuddy.utils.RTDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LandingPageActivity extends AppCompatActivity {

    private FloatingActionButton AddExpense;


    private FloatingActionButton FABLogout;
    private Button LogoutButton;

    private Button AddExpenseButton;
    public DatabaseReference db;
    private Button TransactionList;
    private Button OverviewButton;
    public TextView TotalExpensesValue;
    String username;

    private Button UserProfileButton;
    RTDB rtdb;
    double totalExpenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        username = getIntent().getExtras().getString("username");
        db = FirebaseDatabase.getInstance().getReference("transactions");

        AddExpense = findViewById(R.id.add_Expense);
        AddExpenseButton = findViewById(R.id.Expensebutton);
        rtdb = new RTDB(username);
        FABLogout = findViewById(R.id.log_out);
        LogoutButton = findViewById(R.id.log_out_warning);
        UserProfileButton = findViewById(R.id.toUserProfile);
        TotalExpensesValue = findViewById(R.id.expense_amount_Monthly);
        TransactionList = findViewById(R.id.toTransactionPage);

        OverviewButton = findViewById(R.id.overview_button);
        getTotalMonthlyExpenses();

        AddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, TransactionActivity.class);
                intent.putExtra("username", username);
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

    public void getTotalMonthlyExpenses() {
        Query query = db.orderByChild("account_id").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalExpenses = 0.0;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Transaction t = snap.getValue(Transaction.class);
                    totalExpenses += t.getAmount();
                }
            TotalExpensesValue.setText("$"+String.valueOf(totalExpenses));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Message canceled");
            }
        });

    }

}