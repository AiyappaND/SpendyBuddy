package com.example.spendybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;

public class GraphOverview extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4;
    PieChart pieChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_overview);

        pieChart = findViewById(R.id.piechart);
        tv1 = findViewById(R.id.graph_tv1);
        tv2 = findViewById(R.id.graph_tv2);
        tv3 = findViewById(R.id.graph_tv3);
        tv4 = findViewById(R.id.graph_tv4);

        // todo
        // fetch all amounts, and then calculate amounts
        // then
        // tv.setText(Integer.toString(amount))
        // pieChart.addPieSlice(
        //    new PieModel(
        //        "1",
        //        Integer.parseInt(tvR.getText().toString()),
        //        Color.parseColor("#FFA726")));
        //pieChart.addPieSlice(
        //    new PieModel(
        //        "2",
        //        Integer.parseInt(tvPython.getText().toString()),
        //        Color.parseColor("#66BB6A")));
        //pieChart.addPieSlice(
        //    new PieModel(
        //        "3",
        //        Integer.parseInt(tvCPP.getText().toString()),
        //        Color.parseColor("#EF5350")));
        //pieChart.addPieSlice(
        //    new PieModel(
        //        "4",
        //        Integer.parseInt(tvJava.getText().toString()),
        //        Color.parseColor("#29B6F6")));

    }
}