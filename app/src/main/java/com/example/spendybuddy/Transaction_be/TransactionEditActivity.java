package com.example.spendybuddy.Transaction_be;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.spendybuddy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TransactionEditActivity extends AppCompatActivity {
    String user_id = "Aiyappa";
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user_id = extras.getString("user_id");
        }
        db = FirebaseDatabase.getInstance().getReference("transactions");
    }
}