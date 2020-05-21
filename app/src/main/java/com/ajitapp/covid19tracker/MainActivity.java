package com.ajitapp.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView daily_confirm_casesTv;
    private TextView total_confirm_casesTv;

    private TextView total_active_casesTv;

    private TextView daily_recover_casesTv;
    private TextView total_recover_casesTv;

    private TextView daily_death_casesTv;
    private TextView total_death_casesTv;

    private TextView updateDateAndTimeTv;

    private ImageView refresh_btn;

    private CardView card1, card2, card3, card4;

    private Toolbar toolbar;

    private View shimmer_homepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daily_confirm_casesTv = (TextView) findViewById(R.id.daily_confirm_cases);
        total_confirm_casesTv = (TextView) findViewById(R.id.total_confirm_cases);

        total_active_casesTv = (TextView) findViewById(R.id.total_active_cases);

        daily_recover_casesTv = (TextView) findViewById(R.id.daily_recover_cases);
        total_recover_casesTv = (TextView) findViewById(R.id.total_recover_cases);

        daily_death_casesTv = (TextView) findViewById(R.id.daily_death_cases);
        total_death_casesTv = (TextView) findViewById(R.id.total_death_cases);
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

        refresh_btn = (ImageView) findViewById(R.id.refresh_btn);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shimmer_homepage = (View) findViewById(R.id.shimmer_loading);
                shimmer_homepage.setVisibility(View.VISIBLE);
                getData();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);
            }
        });

    getData();
    }

    private void getData() {
        String url = "https://api.covid19india.org/data.json";

        shimmer_homepage = (View) findViewById(R.id.shimmer_loading);
        shimmer_homepage.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");

                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            // Confirm Cases
                            String total_confirm_cases = jsonObject.getString("confirmed");
                            String daily_confirm_cases = jsonObject.getString("deltaconfirmed");
                            total_confirm_casesTv.setText(total_confirm_cases);
                            daily_confirm_casesTv.setText("[+" +daily_confirm_cases+ "]");

                            // Active Cases
                            String total_active_cases = jsonObject.getString("active");
                            total_active_casesTv.setText(total_active_cases);

                            // Recover Cases
                            String total_recover_cases = jsonObject.getString("recovered");
                            String daily_recover_cases = jsonObject.getString("deltarecovered");
                            total_recover_casesTv.setText(total_recover_cases);
                            daily_recover_casesTv.setText("[+"+daily_recover_cases+ "]");


                            // Death Cases
                            String total_death_cases = jsonObject.getString("deaths");
                            String daily_death_cases = jsonObject.getString("deltadeaths");
                            total_death_casesTv.setText(total_death_cases);
                            daily_death_casesTv.setText("[+"+daily_death_cases+"]");

                            String updatetime = jsonObject.getString("lastupdatedtime");
                            updateDateAndTimeTv.setText(updatetime);


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    shimmer_homepage.setVisibility(View.INVISIBLE);
                                }
                            }, 3000);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            shimmer_homepage.setVisibility(View.INVISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        shimmer_homepage.setVisibility(View.INVISIBLE);

                    }
                }
        );

        int socketTime = 70000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);

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
