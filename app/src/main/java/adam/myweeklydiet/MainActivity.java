package adam.myweeklydiet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public ListView listView;
    public FloatingActionButton fab;
    public SQLiteDatabase DB;
    public  ArrayList<String> weekList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("My Calorie Counter");
        setTitle("My Calorie Counter");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        listView = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);
        weekList.add("Current");
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `weeks` ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "fat TEXT, "+
                "protein TEXT, "+
                "carbs TEXT, "+
                "cals TEXT )";
        DB = getApplicationContext().openOrCreateDatabase("weeks", MODE_PRIVATE, null);

        DB.execSQL(CREATE_TABLE);

        Cursor c = DB.rawQuery("SELECT * FROM " + "weeks", null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {


                     weekList.add(c.getString(c.getColumnIndex("name")));

                  }while (c.moveToNext());
                //Move to next row
            }
        }

        listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.week_list, weekList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), myWeekActivity.class);
                i.putExtra("record", listView.getItemAtPosition(position).toString());

                startActivity(i);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), addWeekActivity.class);
                startActivity(i);


            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
