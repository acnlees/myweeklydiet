package adam.myweeklydiet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 23/06/2015.
 */
public class myWeekActivity extends MainActivity {

    public SharedPreferences shared;
    public TextView caloriestext;
    public TextView fattext;
    public TextView proteintext;
    public TextView carbstext;


    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_myweek);

        // DB = getApplicationContext().openOrCreateDatabase("db", MODE_PRIVATE, null);

        //DB.rawQuery("INSERT INTO `weeks` (`data`)

        SharedPreferences shared;
        shared = getSharedPreferences("shared", Context.MODE_PRIVATE);




        float cals = shared.getFloat("cals", 0);
        float fat = shared.getFloat("fat", 0);
        float protein = shared.getFloat("protein", 0);
        float carbs = shared.getFloat("carbs", 0);

        Log.d("cals", String.valueOf(cals));


        caloriestext = (TextView) findViewById(R.id.calories);

        caloriestext.setText(" Total Calories : " + String.valueOf(cals));


        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setDescription("");
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        yVals1.add(new Entry(fat, 0));
        yVals1.add(new Entry(protein, 1));
        yVals1.add(new Entry(carbs, 2));


        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Fat");
        xVals.add("Protein");
        xVals.add("Carbs");

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);

        PieData data = new PieData(xVals, dataSet);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        chart.setData(data);
    }

}
