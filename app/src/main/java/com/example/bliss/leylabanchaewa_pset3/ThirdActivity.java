package com.example.bliss.leylabanchaewa_pset3;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private TextView mTextMessage;
    ArrayList<String> order;
    ListView listView3;
    ArrayAdapter mAdapter;

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
//
        Bundle bundle = getIntent().getExtras();
        order = bundle.getStringArrayList("order_list");

        listView3 = findViewById(R.id.listView3);

        mAdapter = new ArrayAdapter<String>(ThirdActivity.this, android.R.layout.simple_list_item_1, order);
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
    }


}
