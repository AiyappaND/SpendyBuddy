package com.example.spendybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.spendybuddy.databinding.SignupBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class GraphOverview extends AppCompatActivity {

//    TextView tv1, tv2, tv3, tv4;
    PieChart pieChart;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_overview);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        pieChart = findViewById(R.id.piechart);

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



        pieChart.addPieSlice(
                new PieModel(
                        "Clothes",
                        Integer.parseInt("8"),
                        Color.parseColor(color_one)));
        pieChart.addPieSlice(
                new PieModel(
                        "Eating out",
                        Integer.parseInt("8"),
                        Color.parseColor(color_two)));
        pieChart.addPieSlice(
                new PieModel(
                        "Entertainment",
                        Integer.parseInt("8"),
                        Color.parseColor(color_three)));
        pieChart.addPieSlice(
                new PieModel(
                        "Gas",
                        Integer.parseInt("8"),
                        Color.parseColor(color_four)));
        pieChart.addPieSlice(
                new PieModel(
                        "Groceries",
                        Integer.parseInt("8"),
                        Color.parseColor(color_five)));
        pieChart.addPieSlice(
                new PieModel(
                        "Holidays",
                        Integer.parseInt("8"),
                        Color.parseColor(color_six)));
        pieChart.addPieSlice(
                new PieModel(
                        "Investments",
                        Integer.parseInt("8"),
                        Color.parseColor(color_seven)));
        pieChart.addPieSlice(
                new PieModel(
                        "Kids",
                        Integer.parseInt("8"),
                        Color.parseColor(color_eight)));
        pieChart.addPieSlice(
                new PieModel(
                        "Shopping",
                        Integer.parseInt("8"),
                        Color.parseColor(color_nine)));
        pieChart.addPieSlice(
                new PieModel(
                        "Sports",
                        Integer.parseInt("8"),
                        Color.parseColor(color_ten)));
        pieChart.addPieSlice(
                new PieModel(
                        "Travel",
                        Integer.parseInt("8"),
                        Color.parseColor(color_eleven)));
        pieChart.addPieSlice(
                new PieModel(
                        "Other",
                        Integer.parseInt("8"),
                        Color.parseColor(color_twelve)));

    }
}