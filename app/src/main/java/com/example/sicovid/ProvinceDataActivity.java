package com.example.sicovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sicovid.Adapter.AdapterProvince;
import com.example.sicovid.Model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProvinceDataActivity extends AppCompatActivity {

    private List<Province> listData = new ArrayList<>();
    RecyclerView rvProvinceData;
    TextView tvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_data);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.province_data);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        rvProvinceData = findViewById(R.id.rv_province_data);
        tvLoading = findViewById(R.id.tv_loading);

        provinceData();

    }

    private void provinceData(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.URL_DATA_PROVINCE,
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
                                    Province province = new Province(
                                            result.getString("FID"),
                                            result.getString("Provinsi"),
                                            result.getString("Kasus_Posi"),
                                            result.getString("Kasus_Semb"),
                                            result.getString("Kasus_Meni")
                                    );
                                    listData.add(province);
                                }
                            }

                            tvLoading.setVisibility(View.GONE);

                            rvProvinceData.setLayoutManager(new LinearLayoutManager(ProvinceDataActivity.this));
                            AdapterProvince adapter = new AdapterProvince(ProvinceDataActivity.this, listData);
                            rvProvinceData.setAdapter(adapter);


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
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}