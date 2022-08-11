package com.example.spendybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

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
        values = new HashMap<>();
        values.put("Clothes", 0);
        values.put("Eating Out", 0);
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

        username = getIntent().getExtras().getString("username");

        db = FirebaseDatabase.getInstance().getReference("transactions");

        pieChart = findViewById(R.id.piechart);

        populateGraph();
    }


    public void populateGraph(){


        String color_one= "#ef3c42";
        String color_two= "#f25e40";
        String color_three= "#f2823a";
        String color_four= "#f69537";
        String color_five= "#f4aa2f";
        String color_six= "#f6c137";
        String color_seven= "#fad435";
        String color_eight= "#fdf32f";
        String color_nine= "#ffff2d";
        String color_ten= "#dff429";
        String color_eleven= "#a7d52a";
        String color_twelve= "#79c725";
        String color_thirteen= "#53c025";
        String color_fourteen= "#52c67f";
        String color_fifteen= "#4daecf";
        String color_sixteen= "#4592ca";
        String color_seventeen= "#3f77c4";

        Query query = db.orderByChild("account_id").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                record.clear();
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    Message newmessage = snap.getValue(Message.class);
//                    record.add(newmessage);
//                }
//                stickerCount.setText(String.valueOf(record.size()));
//                Collections.sort(record, (b, a) -> a.getTime().compareTo(b.getTime()));
//                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Message canceled");
            }
        });



        pieChart.addPieSlice(
                new PieModel(
                        "Clothes",
                        Integer.parseInt(values.get("Clothes").toString()),
                        Color.parseColor(color_one)));
        pieChart.addPieSlice(
                new PieModel(
                        "Eating out",
                        Integer.parseInt(values.get("Eating Out").toString()),
                        Color.parseColor(color_two)));
        pieChart.addPieSlice(
                new PieModel(
                        "Entertainment",
                        Integer.parseInt(values.get("Entertainment").toString()),
                        Color.parseColor(color_three)));
        pieChart.addPieSlice(
                new PieModel(
                        "Gas",
                        Integer.parseInt(values.get("Gas").toString()),
                        Color.parseColor(color_four)));
        pieChart.addPieSlice(
                new PieModel(
                        "Groceries",
                        Integer.parseInt(values.get("Groceries").toString()),
                        Color.parseColor(color_five)));
        pieChart.addPieSlice(
                new PieModel(
                        "Holidays",
                        Integer.parseInt(values.get("Holidays").toString()),
                        Color.parseColor(color_six)));
        pieChart.addPieSlice(
                new PieModel(
                        "Investments",
                        Integer.parseInt(values.get("Investments").toString()),
                        Color.parseColor(color_seven)));
        pieChart.addPieSlice(
                new PieModel(
                        "Kids",
                        Integer.parseInt(values.get("Kids").toString()),
                        Color.parseColor(color_eight)));
        pieChart.addPieSlice(
                new PieModel(
                        "Shopping",
                        Integer.parseInt(values.get("Shopping").toString()),
                        Color.parseColor(color_nine)));
        pieChart.addPieSlice(
                new PieModel(
                        "Sports",
                        Integer.parseInt(values.get("Sports").toString()),
                        Color.parseColor(color_ten)));
        pieChart.addPieSlice(
                new PieModel(
                        "Travel",
                        Integer.parseInt(values.get("Travel").toString()),
                        Color.parseColor(color_eleven)));
        pieChart.addPieSlice(
                new PieModel(
                        "Other",
                        Integer.parseInt(values.get("Other").toString()),
                        Color.parseColor(color_twelve)));

    }
}