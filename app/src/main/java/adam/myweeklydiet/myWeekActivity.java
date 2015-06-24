package adam.myweeklydiet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
    public Button save;
    public SQLiteDatabase DB;
    public TextView textDialog;
    float cals;
    float fat;
    float protein ;
    float carbs;
    public String recordName;


    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_myweek);
        SharedPreferences shared;
        shared = getSharedPreferences("shared", Context.MODE_PRIVATE);
        DB = getApplicationContext().openOrCreateDatabase("weeks", MODE_PRIVATE, null);

        Intent i = getIntent();
        recordName = i.getStringExtra("record");
        Log.d("record", recordName);
        if(recordName.equals("Current")) {
            cals = shared.getFloat("cals", 0);
            fat = shared.getFloat("fat", 0);
            protein = shared.getFloat("protein", 0);
            carbs = shared.getFloat("carbs", 0);
        }else{
            Cursor c = DB.rawQuery("SELECT * FROM `weeks` WHERE `name` ='"+recordName+"'", null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        cals =  Float.parseFloat(c.getString(c.getColumnIndex("cals")));
                        fat = Float.parseFloat(c.getString(c.getColumnIndex("fat")));
                        protein = Float.parseFloat(c.getString(c.getColumnIndex("protein")));
                        carbs = Float.parseFloat(c.getString(c.getColumnIndex("carbs")));

                    }while (c.moveToNext());
                    //Move to next row
                }
            }

        }



        caloriestext = (TextView) findViewById(R.id.calories);
        save = (Button) findViewById(R.id.add);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomDialog(textDialog);

            }
        });
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
        chart.setCenterText("Total Cals " + cals);

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


    protected void showCustomDialog(final TextView textDialog) {
        final Dialog dialog = new Dialog(myWeekActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.name_week);


        final EditText name = (EditText)dialog.findViewById(R.id.name);
        Button button = (Button)dialog.findViewById(R.id.save);



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                SharedPreferences shared;
                shared = getSharedPreferences("shared", Context.MODE_PRIVATE);

                SharedPreferences.Editor prefsEditor = shared.edit();

                prefsEditor.putFloat("cals", 0);
                prefsEditor.putFloat("fat", 0);
                prefsEditor.putFloat("carbs", 0);
                prefsEditor.putFloat("protein", 0);

                String nametext = name.getText().toString();

                prefsEditor.commit();

                DB.execSQL("INSERT INTO `weeks` (`name`, `fat`, `protein`, `carbs`, `cals`) VALUES ('" + nametext + "','" + fat + "','" + protein + "','" + carbs + "','" + cals + "')");

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
