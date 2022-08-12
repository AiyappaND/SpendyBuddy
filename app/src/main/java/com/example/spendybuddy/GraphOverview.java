package com.example.spendybuddy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.spendybuddy.data.model.Transaction;
import com.example.spendybuddy.data.model.TransactionType;
import com.example.spendybuddy.databinding.SignupBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Collections;
import java.util.HashMap;


public class GraphOverview extends AppCompatActivity {

//    TextView tv1, tv2, tv3, tv4;
    public PieChart pieChart;
    public DatabaseReference db;
    public String username;

    public HashMap<String, Integer> values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_overview);
        // Populate initial fields and percentages.


        username = getIntent().getExtras().getString("username");

        db = FirebaseDatabase.getInstance().getReference("transactions");

        pieChart = findViewById(R.id.piechart);
        resetValues();
        updateGraph();
        populateGraph();
    }


    public void populateGraph() {


        Query query = db.orderByChild("account_id").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // reset graph totals
                updateGraph();
                Integer total = 1;
                resetValues();
                for (DataSnapshot snap : snapshot.getChildren()) {

                    System.out.println("data" + snap.toString());
                    // CHANGE TRANSACTION ACTIVITY PAGE NAME. BAD DESIGN.
                    Transaction t = snap.getValue(Transaction.class);
                    System.out.println(t.getTransactionType().toString());
                    String type = t.getTransactionType().toString();
                    Integer value =  Math.toIntExact((long) t.getAmount());
                    values.put(type, value + Math.round(values.get(type)));
                }

                values.forEach((key, value) -> {
                    // decrease value by 10%
                    if( value >0 ){
                        values.put(key, values.get(key) / total);
                    }
                    System.out.println(key);
                    System.out.println(value);
                    System.out.println(total);
                    System.out.println("\n");
                    });

                updateGraph();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Message canceled");
            }
        });
    }

    public void resetValues() {
        values = new HashMap<>();
        values.put("Clothes", 0);
        values.put("EatingOut", 0);
        values.put("Entertainment", 0);
        values.put("Gas", 0);
        values.put("Groceries", 0);
        values.put("Holidays", 0);
        values.put("Investments", 0);
        values.put("Kids", 0);
        values.put("Shopping", 0);
        values.put("Sports", 0);
        values.put("Travel", 0);
        values.put("Other", 0);
    }


      public void updateGraph(){




          String color_one = "#ef3c42";
          String color_two = "#f25e40";
          String color_three = "#f2823a";
          String color_four = "#f69537";
          String color_five = "#f4aa2f";
          String color_six = "#f6c137";
          String color_seven = "#fad435";
          String color_eight = "#fdf32f";
          String color_nine = "#ffff2d";
          String color_ten = "#dff429";
          String color_eleven = "#a7d52a";
          String color_twelve = "#79c725";
          String color_thirteen = "#53c025";
          String color_fourteen = "#52c67f";
          String color_fifteen = "#4daecf";
          String color_sixteen = "#4592ca";
          String color_seventeen = "#3f77c4";
            pieChart.addPieSlice(
                    new PieModel(
                            "Clothes",
                            Integer.parseInt(String.valueOf(values.get("Clothes"))),
                            Color.parseColor(color_one)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Eating out",
                            Integer.parseInt(String.valueOf(values.get("EatingOut"))),
                            Color.parseColor(color_two)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Entertainment",
                            Integer.parseInt(String.valueOf(values.get("Entertainment"))),
                            Color.parseColor(color_three)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Gas",
                            Integer.parseInt(String.valueOf(values.get("Gas"))),
                            Color.parseColor(color_four)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Groceries",
                            Integer.parseInt(String.valueOf(values.get("Groceries"))),
                            Color.parseColor(color_five)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Holidays",
                            Integer.parseInt(String.valueOf(values.get("Holidays"))),
                            Color.parseColor(color_six)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Investments",
                            Integer.parseInt(String.valueOf(values.get("Investments"))),
                            Color.parseColor(color_seven)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Kids",
                            Integer.parseInt(String.valueOf(values.get("Kids"))),
                            Color.parseColor(color_eight)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Shopping",
                            Integer.parseInt(String.valueOf(values.get("Shopping"))),
                            Color.parseColor(color_nine)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Sports",
                            Integer.parseInt(String.valueOf(values.get("Sports"))),
                            Color.parseColor(color_ten)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Travel",
                            Integer.parseInt(String.valueOf(values.get("Travel"))),
                            Color.parseColor(color_eleven)));
            pieChart.addPieSlice(
                    new PieModel(
                            "Other",
                            Integer.parseInt(String.valueOf(values.get("Other"))),
                            Color.parseColor(color_twelve)));

        }
}