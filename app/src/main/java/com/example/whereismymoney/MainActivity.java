package com.example.whereismymoney;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    public static Database DB = new Database();
    public float[] spendingProportions;
    FloatingActionButton makeCategoryOrSpendingBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeCategoryOrSpendingBTN = (FloatingActionButton) findViewById(R.id.makeCategoryOrSpendingBTN);
        makeCategoryOrSpendingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openMakeCategoryOrSpending();
            }
        });
//        spendingProportions = DB.getSpendingProportions();
        setupPieChart();
    }

    private void setupPieChart() {
        //populating a list of pie entries
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < DB.Categories.size(); i++){
            Category category = DB.Categories.get(i);
            pieEntries.add(new PieEntry(DB.SumOfSpendingsInCategory(category), category.name));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Spendings in Categories");
        dataSet.setColors(DB.getCategoryColors());
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(25);
        PieData data = new PieData(dataSet);


        // Get the chart
        PieChart chart = (PieChart) findViewById(R.id.pieChart);
        chart.setData(data);
        chart.animateY(1000);
        //TODO: Figure out if we want percentage values or not
        //chart.setUsePercentValues(true);
        chart.setHoleRadius(0f);
        chart.setTransparentCircleRadius(0f);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.invalidate();
    }

    public void openMakeCategoryOrSpending(){
        Intent intent = new Intent(this, MakeCategoryOrSpending.class);
        startActivity(intent);
    }
}
