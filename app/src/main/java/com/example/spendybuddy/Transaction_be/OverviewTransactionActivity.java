package com.example.spendybuddy.Transaction_be;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.spendybuddy.LandingPageActivity;
import com.example.spendybuddy.R;
import com.example.spendybuddy.TransactionActivity;
import com.example.spendybuddy.data.model.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OverviewTransactionActivity extends AppCompatActivity {
    String user_id;
    DatabaseReference db;
    RecyclerView transactionListRecycler;
    List<Transaction> record;
    TransactionListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_transaction);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user_id = extras.getString("username");
        }
        transactionListRecycler = findViewById(R.id.transaction_list_recycler_view);
        record = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("transactions");
        adapter = new TransactionListAdapter(this,record, user_id);
        transactionListRecycler.setLayoutManager(new LinearLayoutManager(this));
        transactionListRecycler.setAdapter(adapter);
        transactionListRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        Query query = db.orderByChild("account_id").equalTo(user_id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                record.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    Transaction newtranscation = snap.getValue(Transaction.class);
                    record.add(newtranscation);
                }
                Collections.sort(record,(a,b)->b.getDate().compareTo(a.getDate()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FloatingActionButton home = findViewById(R.id.home3_button);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(OverviewTransactionActivity.this, LandingPageActivity.class);
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                user_id = bundle.getString("username");

            }
            bundle.putString("username",user_id);

            intent.putExtras(bundle);
            startActivity(intent);

        });





    }
}