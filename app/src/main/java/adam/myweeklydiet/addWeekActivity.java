package adam.myweeklydiet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Adam on 23/06/2015.
 */
public class addWeekActivity extends MainActivity {

    public String searchTerm;
    public String searchUrl = "http://api.nal.usda.gov/ndb/search/?format=json&sort=n&max=25&offset=0&api_key=mAT76mqFbvDpft67Qv15V2Dyt8XOdV8u0prN2qIS&q=";

    public String dataUrl = "http://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=mAT76mqFbvDpft67Qv15V2Dyt8XOdV8u0prN2qIS&nutrients=203&nutrients=204&nutrients=208&nutrients=205&ndbno=";

    public EditText query;
    public Button search;
    public   ListView listView;
    public Map<String, String> foodlist;
    public  ArrayList<String> names = new ArrayList<String>();
    public  ArrayList<String> ids  = new ArrayList<String>();
    public String foodId;
    public String foodName;
    public TextView textdialog;
    public food newFood;
    public week myWeek;
    public ArrayList<food> weekly = new ArrayList<>();
    public SQLiteDatabase DB;
    public SharedPreferences shared;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_newweek);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        query = (EditText) findViewById(R.id.query);
        search = (Button) findViewById(R.id.search);
        listView  = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            foodName = listView.getItemAtPosition(position).toString();
            foodId =   foodlist.get(foodName);
            if(!foodId.equals("No Results")) {


                try {

                    getFoodData getFoodData = new getFoodData();
                    newFood = getFoodData.newFood(dataUrl + foodId);

                    showCustomDialog(textdialog);

                } catch (IOException e) {
                    //handle
                }


            }
            }

        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    searchTerm = query.getText().toString();
                    Log.d("search url", searchTerm);

                    searchFoods searchfoods = new searchFoods();
                    foodlist = searchfoods.run(searchUrl + searchTerm);

                    Log.d("here", foodlist.toString());
                        ids.clear();
                        names.clear();

                        Iterator<Map.Entry<String, String>> iterator = foodlist.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> pairs = (Map.Entry<String, String>) iterator.next();

                            ids.add(pairs.getValue());
                            names.add(pairs.getKey());
                        }


                        listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.food_list, names));


                    }catch(IOException e){
                        //handle
                    }


                }

        });


    }

    protected void showCustomDialog(final TextView textDialog) {
        final Dialog dialog = new Dialog(addWeekActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);

        final TextView calories = (TextView)dialog.findViewById(R.id.calories);
        final TextView fat = (TextView)dialog.findViewById(R.id.fat);
        final TextView protein = (TextView)dialog.findViewById(R.id.protein);
        final TextView carbs = (TextView)dialog.findViewById(R.id.carbs);
        final EditText amount = (EditText)dialog.findViewById(R.id.amount);
        final Button update = (Button)dialog.findViewById(R.id.update);


        calories.setText("cals:"+  newFood.getCalories());
        fat.setText("fat:"+ newFood.getFat());
        protein.setText("protein:"+ newFood.getProtein());
        carbs.setText("carbs: "+ newFood.getCarbs());

        Button button = (Button)dialog.findViewById(R.id.add);

        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                float d = Float.parseFloat(amount.getText().toString()) / 100;
                float cals = Float.parseFloat(newFood.getCalories())*d;
                float fats = Float.parseFloat(newFood.getFat())*d;
                float carb = Float.parseFloat(newFood.getCarbs())*d;
                float prots = Float.parseFloat(newFood.getProtein())*d;

                calories.setText("cals:"+  String.valueOf(cals));
                fat.setText("fat:"+  String.valueOf(fats));
                carbs.setText("carbs:"+  String.valueOf(carb));
                protein.setText("protein:"+  String.valueOf(prots));

            }


            });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                SharedPreferences shared;
                shared = getSharedPreferences("shared", Context.MODE_PRIVATE);

                float cals = shared.getFloat("cals", 0);
                float fat = shared.getFloat("fat", 0);
                float protein = shared.getFloat("protein", 0);
                float carbs = shared.getFloat("carbs", 0);
                Log.d("cals", String.valueOf(cals));

                SharedPreferences.Editor prefsEditor = shared.edit();

                cals = cals + Float.parseFloat(newFood.calories);
                fat = fat +  Float.parseFloat(newFood.fat);
                protein = protein +  Float.parseFloat(newFood.protein);
                carbs = carbs +  Float.parseFloat(newFood.carbs);

                Log.d("cals2", String.valueOf(cals));

                prefsEditor.putFloat("cals", cals);
                prefsEditor.putFloat("fat", fat);
                prefsEditor.putFloat("carbs", carbs);
                prefsEditor.putFloat("protein", protein);


                prefsEditor.commit();

                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
