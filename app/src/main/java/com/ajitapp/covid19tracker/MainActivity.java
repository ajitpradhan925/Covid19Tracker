package com.ajitapp.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView daily_confirm_casesTv;
    private TextView total_confirm_casesTv;

    private TextView total_active_cases;

    private TextView daily_recover_cases;
    private TextView total_recover_cases;

    private TextView daily_death_cases;
    private TextView total_death_cases;

    private TextView updateDateAndTimeTv;

    private ImageView refresh_btn;

    private CardView card1, card2, card3, card4;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daily_confirm_casesTv = (TextView) findViewById(R.id.daily_confirm_cases);
        total_confirm_casesTv = (TextView) findViewById(R.id.total_confirm_cases);

        total_active_cases = (TextView) findViewById(R.id.total_active_cases);

        daily_recover_cases = (TextView) findViewById(R.id.daily_recover_cases);
        total_recover_cases = (TextView) findViewById(R.id.total_recover_cases);

        daily_death_cases = (TextView) findViewById(R.id.daily_death_cases);
        total_death_cases = (TextView) findViewById(R.id.total_death_cases);
        updateDateAndTimeTv = (TextView) findViewById(R.id.update);

        refresh_btn = (ImageView) findViewById(R.id.refresh_btn);

        card1 = (CardView) findViewById(R.id.card1);
        card2 = (CardView) findViewById(R.id.card2);
        card3 = (CardView) findViewById(R.id.card3);
        card4 = (CardView) findViewById(R.id.card4);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateListActivity();
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateListActivity();
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateListActivity();
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStateListActivity();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    private void getStateListActivity() {
        startActivity(new Intent(MainActivity.this, StateListActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
