package com.example.sicovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView tvRegion, tvLastUpdate, tvTotalConfirm, tvTotalConfirmDiff, tvTotalRecovered, tvTotalRecoveredDiff, tvTotalActive,
            tvTotalActiveDiff, tvTotalDeath, tvTotalDeathDiff, tvTitleGlobal, tvGlobalDesc;
    CardView cvGlobalData, cvProvinceData, cvHospitalData, cvAbout;

    String date;
    String lastUpdate;
    String confirm;
    String confirm_diff;
    String recovered;
    String recovered_diff;
    String active;
    String active_diff;
    String death;
    String death_diff;
    String loading;
    int id;
    double confir, confir_diff, recover, recover_diff, activ, activ_diff, deat, deat_diff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        tvRegion = findViewById(R.id.tv_region);
        tvLastUpdate = findViewById(R.id.tv_date_update);
        tvTotalConfirm = findViewById(R.id.tv_total_confirm);
        tvTotalConfirmDiff = findViewById(R.id.tv_total_confirm_diff);
        tvTotalRecovered = findViewById(R.id.tv_total_recovered);
        tvTotalRecoveredDiff = findViewById(R.id.tv_total_recovered_diff);
        tvTotalActive = findViewById(R.id.tv_total_active);
        tvTotalActiveDiff = findViewById(R.id.tv_total_active_diff);
        tvTotalDeath = findViewById(R.id.tv_total_deaths);
        tvTotalDeathDiff = findViewById(R.id.tv_total_deaths_diff);
        tvTitleGlobal = findViewById(R.id.tv_title_global);
        tvGlobalDesc = findViewById(R.id.tv_global_desc);
        cvGlobalData = findViewById(R.id.cv_global_data);
        cvProvinceData = findViewById(R.id.cv_province_data);
        cvHospitalData = findViewById(R.id.cv_hospital_data);
        cvAbout = findViewById(R.id.cv_about);

        loading = getString(R.string.loading);

        cvGlobalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id==0){
                    getDataGlobal();
                    tvTitleGlobal.setText(R.string.indonesia_data);
                    tvGlobalDesc.setText(R.string.indonesia_description);
                    tvRegion.setText(R.string.global);
                } else {
                    getDataIndonesia();
                    tvTitleGlobal.setText(R.string.global_data);
                    tvGlobalDesc.setText(R.string.global_description);
                    tvRegion.setText(R.string.region);
                }
            }
        });

        cvProvinceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProvinceDataActivity.class);
                startActivity(intent);
            }
        });

        cvHospitalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HospitalDataActivity.class);
                startActivity(intent);
            }
        });

        cvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        getDataIndonesia();

    }

    private void getDataIndonesia() {
        loadingText();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.URL_DATA_INDONESIA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("data");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject objData = jsonArray.getJSONObject(i);
                                    date = objData.getString("date");
                                    lastUpdate = objData.getString("last_update");
                                    confirm = objData.getString("confirmed");
                                    confirm_diff = objData.getString("confirmed_diff");
                                    recovered = objData.getString("recovered");
                                    recovered_diff = objData.getString("recovered_diff");
                                    active = objData.getString("active");
                                    active_diff = objData.getString("active_diff");
                                    death = objData.getString("deaths");
                                    death_diff = objData.getString("deaths_diff");
                                }
                            }

                            formatString(confirm, confirm_diff, recovered, recovered_diff, active, active_diff, death, death_diff);

                            tvRegion.setText(R.string.region);
                            tvLastUpdate.setText(lastUpdate);
                            tvTotalConfirm.setText(DecimalFormat.getInstance().format(confir));
                            tvTotalConfirmDiff.setText(DecimalFormat.getInstance().format(confir_diff));
                            tvTotalRecovered.setText(DecimalFormat.getInstance().format(recover));
                            tvTotalRecoveredDiff.setText(DecimalFormat.getInstance().format(recover_diff));
                            tvTotalActive.setText(DecimalFormat.getInstance().format(activ));
                            tvTotalActiveDiff.setText(DecimalFormat.getInstance().format(activ_diff));
                            tvTotalDeath.setText(DecimalFormat.getInstance().format(deat));
                            tvTotalDeathDiff.setText(DecimalFormat.getInstance().format(deat_diff));

                            if (date!=null){
                                id = 0;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Data Tidak Berhasil Dimuat", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDataGlobal() {
        loadingText();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.URL_DATA_GLOBAL + date,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objDataGlobal = new JSONObject(response);
                            JSONObject objData = objDataGlobal.getJSONObject("data");

                            lastUpdate = objData.getString("last_update");
                            confirm = objData.getString("confirmed");
                            confirm_diff = objData.getString("confirmed_diff");
                            recovered = objData.getString("recovered");
                            recovered_diff = objData.getString("recovered_diff");
                            active = objData.getString("active");
                            active_diff = objData.getString("active_diff");
                            death = objData.getString("deaths");
                            death_diff = objData.getString("deaths_diff");

                            formatString(confirm, confirm_diff, recovered, recovered_diff, active, active_diff, death, death_diff);

                            tvLastUpdate.setText(lastUpdate);
                            tvTotalConfirm.setText(DecimalFormat.getInstance().format(confir));
                            tvTotalConfirmDiff.setText(DecimalFormat.getInstance().format(confir_diff));
                            tvTotalRecovered.setText(DecimalFormat.getInstance().format(recover));
                            tvTotalRecoveredDiff.setText(DecimalFormat.getInstance().format(recover_diff));
                            tvTotalActive.setText(DecimalFormat.getInstance().format(activ));
                            tvTotalActiveDiff.setText(DecimalFormat.getInstance().format(activ_diff));
                            tvTotalDeath.setText(DecimalFormat.getInstance().format(deat));
                            tvTotalDeathDiff.setText(DecimalFormat.getInstance().format(deat_diff));

                            id = 1;

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void formatString (String confirm, String confirm_diff, String recovered, String recovered_diff,
                               String active,String active_diff,String death,String death_diff){
        confirm = confirm.replaceAll("[^\\d]", "");
        confirm_diff = confirm_diff.replaceAll("[^\\d]", "");
        recovered = recovered.replaceAll("[^\\d]", "");
        recovered_diff = recovered_diff.replaceAll("[^\\d]", "");
        active = active.replaceAll("[^\\d]", "");
        active_diff = active_diff.replaceAll("[^\\d]", "");
        death = death.replaceAll("[^\\d]", "");
        death_diff = death_diff.replaceAll("[^\\d]", "");
        confir = Double.parseDouble(confirm);
        confir_diff = Double.parseDouble(confirm_diff);
        recover = Double.parseDouble(recovered);
        recover_diff = Double.parseDouble(recovered_diff);
        activ = Double.parseDouble(active);
        activ_diff = Double.parseDouble(active_diff);
        deat = Double.parseDouble(death);
        deat_diff = Double.parseDouble(death_diff);
    }

    private void loadingText(){

        tvRegion.setText(loading);
        tvLastUpdate.setText(loading);
        tvTotalConfirm.setText(loading);
        tvTotalConfirmDiff.setText(loading);
        tvTotalRecovered.setText(loading);
        tvTotalRecoveredDiff.setText(loading);
        tvTotalActive.setText(loading);
        tvTotalActiveDiff.setText(loading);
        tvTotalDeath.setText(loading);
        tvTotalDeathDiff.setText(loading);

    }

}