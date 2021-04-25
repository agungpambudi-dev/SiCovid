package com.example.sicovid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    TextView tvVersion;

    String version = BuildConfig.VERSION_NAME;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.about);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        tvVersion = findViewById(R.id.tv_version);

        tvVersion.setText("Versi : "+version+" - Alpha");

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}