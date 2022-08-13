package com.example.spendybuddy.utils;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.spendybuddy.data.model.Transaction;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RTDB {

    public HashMap<String, Transaction> values;
    public DatabaseReference db;
    public String username;
    public RTDB(String username) {
        username = username;
        values = new HashMap<>();
        db = FirebaseDatabase.getInstance().getReference("transactions");
    }

    public void addTransaction(Transaction transaction){
        db.setValue(transaction);
    }

    public void fetchValues() {
        Query query = db.orderByChild("account_id").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Transaction t = snap.getValue(Transaction.class);
                    values.put(t.getId(), t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Message canceled");
            }
        });
    }

    public void deleteTransaction(String transactionId){
        DatabaseReference ref = db.child(transactionId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Deleted transaction " + transactionId);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: Failed to delete transaction");
            }
        });
    }

    public void updateTransaction(String transactionId, Transaction transaction){
        DatabaseReference ref = db.child(transactionId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().setValue(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Updated transaction " + transactionId);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: Failed to update transaction");
            }
        });
    }

    public HashMap<String, Transaction> getValues() {
        return values;
    }

    public void setValues(HashMap<String, Transaction> values) {
        this.values = values;
    }

    public DatabaseReference getDb() {
        return db;
    }

    public void setDb(DatabaseReference db) {
        this.db = db;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
