package adam.myweeklydiet;

import android.content.Intent;
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

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public ListView listView;
    public FloatingActionButton fab;
    public SQLiteDatabase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS weeks ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "week TEXT, "+
                "data TEXT )";
        DB = getApplicationContext().openOrCreateDatabase("weeks", MODE_PRIVATE, null);

        DB.execSQL(CREATE_TABLE);


        ArrayList<String> weekList = new ArrayList<String>();

        weekList.add("week one");


        listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.week_list, weekList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), myWeekActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}