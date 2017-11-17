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

public class SecondActivity extends AppCompatActivity {

    private TextView mTextMessage;

    TextView textView2;
    ListView listView2;
    ArrayAdapter mAdapter;
    List<String> item_info= new ArrayList<String>();
    List<String> total_price = new ArrayList<String>();
    String info;
    String categorie;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_ordered:
                        mTextMessage.setText(R.string.title_menu);
                        return true;
                    case R.id.navigation_goto_pay:
                        mTextMessage.setText(R.string.title_yourorder);
                        return true;
                }
                return false;
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        textView2 = findViewById(R.id.textView2);

        Bundle bundle = getIntent().getExtras();
        categorie = bundle.getString("menuItem");

        if(bundle!= null) {
            textView2.setText(categorie);
        }

        listView2 = findViewById(R.id.listView2);
        textView2 = findViewById(R.id.textView2);

        // instantiate ArrayAdapter of the categories
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item_info);

        // functie uitvoeren om de jsonarrayRequest uit te voeren en in  listview te zetten
        getQueue();

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("order_list", listView2.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });

    }


    public void getQueue() {

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item_info);
        listView2.setAdapter(mAdapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://resto.mprog.nl/menu";

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new SecondActivity.MyResponseListener(), new SecondActivity.MyErrorListener());

        requestQueue.add(jsonRequest);
    }

    private class MyResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            try {
                JSONArray items = response.getJSONArray("items");
                for (int i=0; i<items.length(); i++) {
                    String cat_item = items.getJSONObject(i).getString("category");
                    if (categorie.equalsIgnoreCase(cat_item)){
                        String name = items.getJSONObject(i).getString("name");
                        String price = items.getJSONObject(i).getString("price");
                        info = name + "     $" + price;
                        item_info.add(info);
                    }
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
            textView2.setText("Oops, that didn't work!");
        }
    }

}
