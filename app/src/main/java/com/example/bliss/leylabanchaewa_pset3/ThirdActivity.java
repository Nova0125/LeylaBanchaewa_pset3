package com.example.bliss.leylabanchaewa_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private TextView mTextMessage;
    String order;
    ListView listView3;
    ArrayAdapter mAdapter;
    TextView textView3;
    Button button;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu:
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
        setContentView(R.layout.third_activity);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences settings = getSharedPreferences("prefs", MODE_PRIVATE);
        String items_stored = settings.getString("items", null);
//
        Log.d(" items", items_stored);
        String replace = items_stored.replace("[", "");
        String replace1 = replace.replace("]", "");
        List<String> order_prefs = new ArrayList<>(Arrays.asList(replace1.split(",")));

        listView3 = findViewById(R.id.listView3);
        textView3 = findViewById(R.id.textView3);
        button = findViewById(R.id.button_order);

        mAdapter = new ArrayAdapter<String>(ThirdActivity.this, android.R.layout.simple_list_item_1, order_prefs);
        listView3.setAdapter(mAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu:
                        Intent intent1 = new Intent(ThirdActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_goto_pay:
                        break;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            public  void onClick (View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(ThirdActivity.this);

                String url = "https://resto.mprog.nl/order";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String time_order = response.toString();
                        Toast toast = Toast.makeText(ThirdActivity.this, time_order, Toast.LENGTH_LONG);
                        toast.show();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView3.setText("Oops, that didn't work!");
                    }
                }
                );
                requestQueue.add(postRequest);
            }
        });

    }


}

