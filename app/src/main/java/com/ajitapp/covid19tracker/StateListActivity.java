package com.ajitapp.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ajitapp.covid19tracker.Adapter.StateListAdapter;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class StateListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private StateListAdapter stateListAdapter;
    private MaterialSearchView materialSearchView;
    private ArrayList<HashMap<String, String>> arrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar_state);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Select a State");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StateListActivity.this, MainActivity.class));

            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.state_wise_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getStateData();

    }

    private void getStateData() {

        String url = "https://api.covid19india.org/data.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");


                            for (int i =1; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String state_name = jsonObject.getString("state");
                                String confirm_cases = jsonObject.getString("confirmed");

                                HashMap<String, String> map = new HashMap<>();

                                map.put("state_name", state_name);
                                map.put("confirm_cases", confirm_cases);

                                arrayList.add(map);

                            }


                            stateListAdapter = new StateListAdapter(getApplicationContext(), arrayList);
                            recyclerView.setAdapter(stateListAdapter);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StateListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StateListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }
                }
        );

        int socketTime = 7000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(StateListActivity.this);
        requestQueue.add(jsonObjectRequest);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.state_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
