package com.example.spendybuddy;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spendybuddy.data.model.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.HashMap;


public class GraphOverview extends AppCompatActivity {

    public PieChart pieChart;
    public DatabaseReference db;
    public String username;
    public HashMap<String, Integer> values;

    TextView tv_clothes,
            tv_eating,
            tv_entertainment,
            tv_gas, tv_groceries,
            tv_holidays,
            tv_investments,
            tv_kids,
            tv_shopping,
            tv_sports,
            tv_travel,
            tv_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_overview);
        // Populate initial fields and percentages.
        username = getIntent().getExtras().getString("username");

        db = FirebaseDatabase.getInstance().getReference("transactions");

        tv_clothes =  findViewById(R.id.graph_clothes);
        tv_eating =  findViewById(R.id.graph_eating_out);
        tv_entertainment =  findViewById(R.id.graph_entertainment);
        tv_gas =  findViewById(R.id.graph_gas);
        tv_groceries =  findViewById(R.id.graph_groceries);
        tv_holidays =  findViewById(R.id.graph_holidays);
        tv_investments =  findViewById(R.id.graph_investments);
        tv_kids =  findViewById(R.id.graph_kids);
        tv_shopping =  findViewById(R.id.graph_shopping);
        tv_sports =  findViewById(R.id.graph_sports);
        tv_travel =  findViewById(R.id.graph_travel);
        tv_other =  findViewById(R.id.graph_other);

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
                    Integer value = Math.toIntExact((long) t.getAmount());
                    values.put(type, value + Math.round(values.get(type)));
                }

                values.forEach((key, value) -> {
                    if (value > 0) {
                        values.put(key, values.get(key) / total);
                    }
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


    public void updateGraph() {


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


        tv_clothes.setText(values.get("Clothes").toString());
        tv_eating.setText(values.get("EatingOut").toString());
        tv_entertainment.setText(values.get("Entertainment").toString());
        tv_gas.setText(values.get("Gas").toString());
        tv_groceries.setText(values.get("Groceries").toString());
        tv_holidays.setText(values.get("Holidays").toString());
        tv_investments.setText(values.get("Investments").toString());
        tv_kids.setText(values.get("Kids").toString());
        tv_shopping.setText(values.get("Shopping").toString());
        tv_sports.setText(values.get("Sports").toString());
        tv_travel.setText(values.get("Travel").toString());
        tv_other.setText(values.get("Other").toString());

    }


}