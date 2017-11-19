package com.example.bliss.leylabanchaewa_pset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private TextView mTextMessage;
    ListView listView;
    TextView textView;
    ArrayAdapter mAdapter;

    List<String> categoriesGroup = new ArrayList<String>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu:
                        return true;
                    case R.id.navigation_goto_pay:
                        return true;
                }
                return false;
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);

        // instantiate ArrayAdapter of the categories
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesGroup);

        // functie uitvoeren om de jsonarrayRequest uit te voeren en in  listview te zetten
        getQueue();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("menuItem", listView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu:
                        break;
                    case R.id.navigation_goto_pay:
                        Intent intent2 = new Intent(MainActivity.this, ThirdActivity.class);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });
    }

    public void getQueue() {

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesGroup);
        listView.setAdapter(mAdapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://resto.mprog.nl/categories";

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new MyResponseListener(), new MyErrorListener());

        requestQueue.add(jsonRequest);

    }

    private class MyResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            try {
                JSONArray categories = response.getJSONArray("categories");
                for (int i=0; i<categories.length(); i++) {
                    categoriesGroup.add(categories.getString(i));
                }

                mAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("MYAPP", "unexpected JSON exception", e);
            }
        }
    }

    private class MyErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            // error message
            textView.setText("Oops, that didn't work!");
        }
    }


}
