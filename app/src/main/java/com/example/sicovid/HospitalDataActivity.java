package com.example.sicovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sicovid.Adapter.AdapterHospital;
import com.example.sicovid.Model.Hospital;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HospitalDataActivity extends AppCompatActivity {

    RecyclerView rvHospitalData;
    TextView tvLoading;
    private SearchView searchView;
    List<Hospital> listData = new ArrayList<>();
    AdapterHospital adapterHospital;
    Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_data);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.hospital_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        rvHospitalData = findViewById(R.id.rv_hospital_data);
        tvLoading = findViewById(R.id.tv_loading);

        adapterHospital = new AdapterHospital(this,listData);

        rvHospitalData.setLayoutManager(new LinearLayoutManager(HospitalDataActivity.this));
        rvHospitalData.setAdapter(adapterHospital);

        hospitalData();

    }

    private void hospitalData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.URL_DATA_HOSPITAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("features");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject objData = jsonArray.getJSONObject(i);
                                    JSONObject result = objData.getJSONObject("attributes");
                                    hospital = new Hospital(
                                            result.getString("nama"),
                                            result.getString("wilayah"),
                                            result.getString("alamat"),
                                            result.getString("telepon"),
                                            result.getDouble("lat"),
                                            result.getDouble("lon")
                                    );
                                    listData.add(hospital);

                                }
                            }

                            tvLoading.setVisibility(View.GONE);
                            adapterHospital.notifyDataSetChanged();
                            adapterHospital.setOnItemClickCallback(new AdapterHospital.OnItemClickCallback() {
                                @Override
                                public void onItemClicked(Hospital data) {

                                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HospitalDataActivity.this);
                                    builder.setMessage("Apakah Anda Ingin Melihat Rute Lokasi " + data.getName()).setCancelable(false);

                                    final double latitude = data.getLat();
                                    final double longitude = data.getLon();

                                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f", latitude, longitude);
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                            intent.setPackage("com.google.android.apps.maps");
                                            try {
                                                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                                startActivity(unrestrictedIntent);
                                            } catch (ActivityNotFoundException e) {
                                                Toast.makeText(HospitalDataActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    final AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                /*if (query.isEmpty()){
                    hospitalData();
                }*/
                searchView.clearFocus();
                adapterHospital.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapterHospital.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}