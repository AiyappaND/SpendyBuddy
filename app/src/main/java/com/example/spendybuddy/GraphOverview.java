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
        tv1 = findViewById(R.id.)
    }
}