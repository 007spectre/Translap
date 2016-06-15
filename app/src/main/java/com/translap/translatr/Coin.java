package com.translap.translatr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.translap.translatr.R;

import java.util.ArrayList;

public class Coin extends AppCompatActivity {
    Database database;
    ArrayList arrayList;
    ListView listView;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        database=new Database(this);
        database.getWritableDatabase();
        textView1=(TextView)findViewById(R.id.textView1);
        listView=(ListView)findViewById(R.id.listView);
        arrayList=database.fetchData();
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.activity_list_item,android.R.id.text1,arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coin, menu);
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
